
public class Tri {
	private Vector[] points;
	private Vector normal;
	private double distance;
	private Vector center;
	public Vector getNormal() {
		return normal;
	}
	public Vector getCenter() {
		return center;
	}
	public double getDistance() {
		return distance;
	}
	public Tri(Vector a, Vector b, Vector c) {
		points = new Vector[] {a,b,c};
		center = Vector.center(a, b, c);
		distance = center.getMagnitude();
		normal = a.sub(b).cross(b.sub(c));
		normal.normalize();
	}
}
