
public class Ray {
	private Vector origin;
	private Vector direction;
	double getHit(Tri tri) {
		double t = (tri.getCenter().sub(origin).dot(tri.getNormal()))/(direction.dot(tri.getNormal()));
		//System.out.println(t);
		if (t >= 255 ) {
			//return null;
			return 255;
		}
		if(t<0) {
			return 0;
		}
		return t;
		//return direction.multiply(t).add(origin);
	}
	
	Ray(Vector o, Vector d){
		origin = o;
		direction = d;
		direction.normalize();
	}
	
}
