package org.shemtov.ninja.validation;

import org.junit.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.shemtov.ninja.intersection.NinjaIntersection;
import org.shemtov.ninja.intersection.NinjaIntersectionFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmptyInputValidationTest {

    private Geometry testGeometry;

    public EmptyInputValidationTest() throws ParseException {
        // Random Geometry on North America
        this.testGeometry = new WKTReader().read("POLYGON((-123 48,-85 48,-85 3,-123 32,-123 48))");
    }

    @Test()
    public void testGeometryAIsEmptyGeometryBIsEmpty() {
        List<Geometry> geometriesA = new ArrayList<>();
        List<Geometry> geometriesB = new ArrayList<>();
        NinjaIntersection ninjaIntersectionService = NinjaIntersectionFactory.create(geometriesA, geometriesB);
        assertEquals(ninjaIntersectionService.getIntersections().size(), 0);
    }

    @Test()
    public void testGeometryAIsEmptyGeometryBNotEmpty() {
        List<Geometry> geometriesA = new ArrayList<>();
        List<Geometry> geometriesB = new ArrayList<>() {{
            add(testGeometry);
        }};
        NinjaIntersection ninjaIntersectionService = NinjaIntersectionFactory.create(geometriesA, geometriesB);
        assertEquals(ninjaIntersectionService.getIntersections().size(), 0);
    }

    @Test()
    public void testGeometryANotEmptyGeometryBIsEmpty() {
        List<Geometry> geometriesA = new ArrayList<>() {{
            add(testGeometry);
        }};
        List<Geometry> geometriesB = new ArrayList<>();
        NinjaIntersection ninjaIntersectionService = NinjaIntersectionFactory.create(geometriesA, geometriesB);
        assertEquals(ninjaIntersectionService.getIntersections().size(), 0);
    }


}
