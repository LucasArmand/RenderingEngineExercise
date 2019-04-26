
public class Ray {
	private Vector origin;
	private Vector direction;
	double getHit(Tri tri) {
		double t = (tri.getCenter().sub(origin).dot(tri.getNormal()))/(direction.dot(tri.getNormal()));
		//System.out.println(t);
		if (t >= 255 ) {
			//return null;
			//return 255;
		}
		Vector hitP = direction.multiply(t).add(origin);
		//if(hitP.getX() == 0 && hitP.getY() == 0) {
			
			if(tri.insideTri(hitP)) {
				//System.out.println(hitP +" is in the triangle");
				return  1/ (new Vector(0,0,0).getDistance(hitP)) * 255;
				
			}
		//}
		
		
		return 0;
		//return direction.multiply(t).add(origin);
	}
	
	Ray(Vector o, Vector d){
		origin = o;
		direction = d;
		direction.normalize();
	}
	
}
