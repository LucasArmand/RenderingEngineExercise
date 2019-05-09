
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
	public double[] getCoords (Vector p) {
		Vector xBase = points[1].sub(points[0]);
		xBase.normalize();
		//System.out.println(xBase);
		
		Vector yBase = normal.cross(xBase);
		yBase.normalize();
		//System.out.println(yBase);
		//System.out.println(Vector.project(xBase,p.sub(points[0])));
		return new double[] {Vector.project(xBase, p.sub(points[0])).getMagnitude(),points[0].sub(Vector.project(yBase, p.sub(points[0]))).getMagnitude()};
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
	public void update() {
		center = Vector.center(points[0], points[1], points[2]);
		distance = center.getMagnitude();
		normal = points[0].sub(points[1]).cross(points[1].sub(points[2]));
		normal.normalize();
	}
	public void rotate (double ax,double ay, double az) {
		for(int i = 0; i < 3; i++) {
			double x,y,z;
			y = Math.cos(ax) * oPoints[i].getY() + Math.sin(ax) * oPoints[i].getZ();
			z = -Math.sin(ax) * oPoints[i].getY() + Math.cos(ax) * oPoints[i].getZ();
			points[i] = new Vector(oPoints[i].getX(),y,z);
			x = Math.cos(ay) * points[i].getX() + Math.sin(ay) * points[i].getZ();
			z = -Math.sin(ay) * points[i].getX() + Math.cos(ay) * points[i].getZ();
			points[i] = new Vector(x,points[i].getY(),z);
			x = Math.cos(az) * points[i].getX() + Math.sin(az) * points[i].getY();
			y = -Math.sin(az) * points[i].getX() + Math.cos(az) * points[i].getY();
			points[i] = new Vector(x,y,points[i].getZ());
		}
		update();
	}
	/*
	public void rotateY (double t) {
		for(int i = 0; i < 3; i++) {
			
			
		}
		update();
	}
	public void rotateZ (double t) {
		for(int i = 0; i < 3; i++) {
			double 
		}
		update();
	}
	*/
	public Tri(Vector a, Vector b, Vector c) {
		points = new Vector[] {a,b,c};
		oPoints = new Vector[] {a,b,c};
		center = Vector.center(a, b, c);
		distance = center.getMagnitude();
		normal = a.sub(b).cross(b.sub(c));
		normal.normalize();
	}
}
