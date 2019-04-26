
public class Tri {
	private Vector[] points;
	private Vector normal;
	private double distance;
	private Vector center;
	
	public boolean insideTri(Vector p) {
		boolean noHit = false;
		Vector ABcrossAP = points[0].sub(points[1]).cross(points[0].sub(p));
		Vector ABcrossAC = points[0].sub(points[1]).cross(points[0].sub(points[2]));
		//System.out.println(ABcrossAP +" and " + ABcrossAC);
		if(ABcrossAP.dot(ABcrossAC) < 0) {
			noHit = true;
		}
		
		Vector BCcrossBP = points[1].sub(points[2]).cross(points[1].sub(p));
		Vector BCcrossBA = points[1].sub(points[2]).cross(points[1].sub(points[0]));
		//System.out.println(BCcrossBP +" and " + BCcrossBA);
		if(BCcrossBP.dot(BCcrossBA) < 0) {
			noHit = true;
		}
		
		Vector CAcrossCP = points[2].sub(points[0]).cross(points[2].sub(p));
		Vector CAcrossCB = points[2].sub(points[0]).cross(points[2].sub(points[1]));
		//System.out.println(CAcrossCP +" and " + CAcrossCB);
		if(CAcrossCP.dot(CAcrossCB) < 0) {
			noHit = true;
		}
		return !noHit;
	}
	
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
