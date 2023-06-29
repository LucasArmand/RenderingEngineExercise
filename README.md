# Raster and Raycasting Engine in Java
This was a side project that I worked on during my AP Computer Science class. I wanted to solidify my understanding
of the fundamental proccesses in 3D graphics pipelines. While any practical solution would use OpenGL, writing this in Java
forced me to create everything from scratch, providing a fun challenge. There's no better way to learn something than to build it :)

## Rasterizer
The first part of this project was building a triangle rasterizer. This takes 3D coordinates for the points of triangles, and 
applies a matrix multiplication to transform them from world space into camera space, and then onto 2D screen space. The triangles
are then textured by sampling from a texture image for each pixel within the triangle, warped by that same transformation. In the demo
that I built, the user could fly the camera around the scene, viewing it from any angle.

## Raycaster
The second part of the project was to build a raycasting rendering engine. This is different from the rastering engine, as it involves shooting out 
a ray from each pixel on the screen into the 3D world. The intersection of the world and the ray is then calculated, and the color at the intersection
is reported back to the original pixel, which is then colored appropriately. This works because light travels the same forwards as backwards, so tracing
light beams from the camera to the world is the same as tracing light beams from the world to the camera, which is how we see in the real world.
