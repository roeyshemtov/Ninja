package org.shemtov.ninja.intersection;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.index.SpatialIndex;
import org.shemtov.ninja.model.IntersectionModel;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class NinjaIntersection {

    private final SpatialIndex index;
    private final double minContainmentPercentage;
    private final List<Geometry> geometriesToSearch;
    private List<IntersectionModel> intersections;

    NinjaIntersection(SpatialIndex index,
                      List<Geometry> geometriesA,
                      List<Geometry> geometriesB,
                      double minContainmentPercentage) {
        requireNonNull(geometriesA, "The list of geometries should not be null");
        requireNonNull(geometriesB, "The list of geometries should not be null");

        this.index = index;
        this.minContainmentPercentage = checkAndGetPercentageInRange(minContainmentPercentage);
        this.geometriesToSearch = geometriesA.size() > geometriesB.size() ? geometriesB : geometriesA;

        List<Geometry> geometriesToIndex = geometriesA.size() > geometriesB.size() ? geometriesA : geometriesB;
        geometriesToIndex
                .forEach(geometry -> this.index.insert(geometry.getEnvelopeInternal(), geometry));
    }

    @SuppressWarnings("unchecked")
    public List<IntersectionModel> getIntersections() {
        if (this.intersections != null) {
            return this.intersections;
        }
        this.intersections = new ArrayList<>();

        for (Geometry geometryToSearch : this.geometriesToSearch) {
            List<Geometry> intersectedGeometries = (List<Geometry>) this.index.query(geometryToSearch.getEnvelopeInternal());
            for (Geometry geometry : intersectedGeometries) {
                if (geometry.intersects(geometryToSearch)) {
                    Geometry intersection = geometry.intersection(geometryToSearch);
                    double containmentPercentage = this.calculateContainmentPercentage(intersection, geometry, geometryToSearch);
                    if (containmentPercentage > this.minContainmentPercentage) {
                        this.intersections.add(createIntersectionModel(geometry, geometryToSearch, intersection, containmentPercentage));
                    }
                }
            }
        }
        return this.intersections;
    }

    private double calculateContainmentPercentage(Geometry intersection, Geometry geometryA, Geometry geometryB) {
        double areaA = geometryA.getArea();
        double areaB = geometryB.getArea();
        double intersectionArea = intersection.getArea();
        return areaA < areaB ? intersectionArea / areaA : intersectionArea / areaB;
    }

    private static IntersectionModel createIntersectionModel(Geometry geometryA, Geometry geometryB, Geometry intersection, double containmentPercentage) {
        return new IntersectionModel(geometryA, geometryB, intersection, containmentPercentage);
    }

    private static double checkAndGetPercentageInRange(double percentage) {
        if (percentage >= 0 && percentage <= 1) {
            return percentage;
        } else {
            throw new IllegalArgumentException("minContainmentPercentage argument should be between 0 to 1");
        }
    }
}
