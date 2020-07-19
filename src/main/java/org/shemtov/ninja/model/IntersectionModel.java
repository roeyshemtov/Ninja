package org.shemtov.ninja.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Geometry;

@Getter
@AllArgsConstructor
public class IntersectionModel {
    private Geometry geometryA;
    private Geometry geometryB;
    private Geometry intersection;
    private double containmentPercentage;
}
