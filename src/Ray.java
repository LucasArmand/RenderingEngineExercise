
public class Ray {
	private Vector origin;
	private Vector direction;
	private double t;
	
	double getT(Tri tri) {
		return (tri.getCenter().sub(origin).dot(tri.getNormal()))/(direction.dot(tri.getNormal()));
		
	}
	public Vector multiply(double  t) {
		return direction.multiply(t);
	}
	double[] getHitCoord(Tri tri) {
		 t = (tri.getCenter().sub(origin).dot(tri.getNormal()))/(direction.dot(tri.getNormal()));
		//System.out.println(t);
		if (t < 0  || t > 500) {
			//return null;
			return new double[] {-1};
			//return 255;
		}
		Vector hitP = direction.multiply(t).add(origin);
		//if(hitP.getX() == 0 && hitP.getY() == 0) {
			
			if(tri.insideTri(hitP)) {
				//System.out.println(hitP +" is in the triangle");
				//return (new int[] {(int)(255/t),(int)(255/t),(int)(255/t)});
				double [] out = new double[]{tri.getCoords(hitP)[0],tri.getCoords(hitP)[1]};
				 return(out);
				
				//return  new int[] {(int)(1/ (tri.getPoints()[0].getDistance(hitP)) * 255),(int)(1/ (tri.getPoints()[1].getDistance(hitP)) * 255),(int) (1/ (tri.getPoints()[2].getDistance(hitP)) * 255)};
				
			}

		return new double[] {-1};
		//return direction.multiply(t).add(origin);
	}
	public Vector getDirection() {
		return direction;
	}
	public Vector getOrigin() {
		return origin;
	}
	Ray(Vector o, Vector d){
		origin = o;
		direction = d;
		direction.normalize();
	}
	
}
