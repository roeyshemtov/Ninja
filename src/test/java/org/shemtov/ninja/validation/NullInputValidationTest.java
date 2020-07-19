package org.shemtov.ninja.validation;

import org.junit.Test;

import java.util.ArrayList;

import org.shemtov.ninja.intersection.NinjaIntersectionFactory;

import static org.junit.jupiter.api.Assertions.*;

public class NullInputValidationTest {

    private static String GEOMETRY_NULL_MESSAGE = "The list of geometries should not be null";
    private static String MCP_NOT_IN_RANGE_MESSAGE = "minContainmentPercentage argument should be between 0 to 1";

    @Test()
    public void testGeometryAIsNullGeometryBNotNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> NinjaIntersectionFactory.create(null, new ArrayList<>()));
        assertEquals(exception.getMessage(), GEOMETRY_NULL_MESSAGE);
    }

    @Test()
    public void testGeometryBIsNullGeometryANotNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> NinjaIntersectionFactory.create(new ArrayList<>(), null));
        assertEquals(exception.getMessage(), GEOMETRY_NULL_MESSAGE);
    }

    @Test
    public void testMinimumContainmentPercentageIsBelowTheRange() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> NinjaIntersectionFactory.create(new ArrayList<>(), new ArrayList<>(), -1));
        assertEquals(exception.getMessage(), MCP_NOT_IN_RANGE_MESSAGE);
    }

    @Test
    public void testMinimumContainmentPercentageIsAboveTheRange() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> NinjaIntersectionFactory.create(new ArrayList<>(), new ArrayList<>(), 2));
        assertEquals(exception.getMessage(), MCP_NOT_IN_RANGE_MESSAGE);
    }


}
