
public class Tri {
	private Vector[] points;
	private Vector[] oPoints;
	private Vector normal;
	private double distance;
	private Vector center;
	public Vector[] getPoints() {
		return points;
	}
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
	public void rotateY (double t) {
		for(int i = 0; i < 3; i++) {
			//points[i] = center.sub(oPoints[i]);
			//System.out.println(t / Math.PI);
			//System.out.println(Math.sin(t) +", " + Math.cos(t));
			double x = Math.cos(t) * oPoints[i].getX() + Math.sin(t) * oPoints[i].getZ();
			double z = -Math.sin(t) * oPoints[i].getX() + Math.cos(t) * oPoints[i].getZ();
			//System.out.println("x:" + x +", z:"+ z);
			//System.out.println(points[i].getDistance(center));
			
			points[i] = new Vector(x,oPoints[i].getY(),z);
			//System.out.println(i +": " + points[i]);
			//System.out.println("real " + i + ": " + this.getPoints()[i]);
		}
		
	}
	public Tri(Vector a, Vector b, Vector c) {
		points = new Vector[] {a,b,c};
		oPoints = new Vector[] {a,b,c};
		center = Vector.center(a, b, c);
		distance = center.getMagnitude();
		normal = a.sub(b).cross(b.sub(c));
		normal.normalize();
	}
}
