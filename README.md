# Ninja
Ninja is an intersection library build to the top of [locationtech jts](https://github.com/locationtech/jts) library.

Ninja give you the ability to intersect wisely two lists of geometries and find the
geometries that intersect each other with multiple conditions that you can choose.


<h4>How To Use?</h4>


```
List<Geometry> geometriesA = new ArrayList<>();
List<Geometry> geometriesB = new ArrayList<>();
NinjaIntersection intersectionService = NinjaIntersectionFactory.create(geometriesA,geometriesB);
List<IntersectionModel> intersections = intersectionService.getIntersections();
IntersectionModel model = intersections.get(0);
model.getGeometryA(); // Get the first Geometry.
model.getGeometryB(); // Get the second Geometry.
model.getIntersections(); // Get the intersection between first and second geometries.
model.getContainmentPercentage(); // Get the containmentPercentage between the intersection and the bigger geometry.
```

<h5>NinjaIntersectionFactory must and optional arguments</h5>
* List<Geometry> geometriesA (Must) - list of geometries to intersect.
* List<Geometry> geometriesB (Must) - list of geometries to intersect.
* double minContainmentPercentage (Optional, default 0) - The minimum containment percentage between intersection and the bigger geometry.
* SpatialIndex/String geospatialIndex (Optional, default STRTree) - The index that will be used for indexing the geometries.