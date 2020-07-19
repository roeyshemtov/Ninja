package org.shemtov.ninja.intersection;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.index.SpatialIndex;
import org.locationtech.jts.index.strtree.STRtree;
import org.locationtech.jts.index.quadtree.Quadtree;

import java.util.List;

public class NinjaIntersectionFactory {

    public static NinjaIntersection create(List<Geometry> geometriesA, List<Geometry> geometriesB) {
        return create(getDefaultSpatialIndex(), geometriesA, geometriesB, 0);
    }

    public static NinjaIntersection create(List<Geometry> geometriesA, List<Geometry> geometriesB, double minContainmentPercentage) {
        return create(getDefaultSpatialIndex(), geometriesA, geometriesB, minContainmentPercentage);
    }

    public static NinjaIntersection create(String geospatialIndex, List<Geometry> geometriesA, List<Geometry> geometriesB, double minContainmentPercentage) {
        return create(getSpatialIndexByName(geospatialIndex), geometriesA, geometriesB, minContainmentPercentage);
    }

    public static NinjaIntersection create(SpatialIndex geospatialIndex, List<Geometry> geometriesA, List<Geometry> geometriesB, double minContainmentPercentage) {
        return new NinjaIntersection(geospatialIndex, geometriesA, geometriesB, minContainmentPercentage);
    }

    private static SpatialIndex getSpatialIndexByName(String name) {
        if (name.equals("quadtree")) {
            return new Quadtree();
        } else if (name.equals("strtree")) {
            return new STRtree();
        } else {
            throw new IllegalArgumentException("Index %s is not supported.Supported geospatial index are: [quadtree,strtree]");
        }
    }

    private static SpatialIndex getDefaultSpatialIndex() {
        return new STRtree();
    }
}