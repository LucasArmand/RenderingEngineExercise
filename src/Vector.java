
public class Vector {
	private double[] point;
	
	Vector(double x, double y, double z) {
		point = new double[] {x,y,z};
	}
	Vector(double[] a){
		point = a;
	}
	
	public static Vector project (Vector  a, Vector b) {
		return a.multiply(a.dot(b)/(Math.pow(a.getMagnitude(),2)));
	}
	
	public void setX(double x) {
		point[0] = x;
	}
	public void setY(double y) {
		point[1] = y;
	}
	public void setZ(double z) {
		point[2] = z;
	}
	public double getX() {
		return point[0];
	}
	public double getY() {
		return point[1];
	}
	public double getZ() {
		return point[2];
	}
	public double getMagnitude() {
		return Math.sqrt(Math.pow(getX(),2) +  Math.pow(getY(), 2) + Math.pow(getZ(), 2));
	}
	public void normalize() {
		double mag = getMagnitude();
		setX(getX()/mag);
		setY(getY()/mag);
		setZ(getZ()/mag);
	}
	public Vector multiply(double a) {
		return new Vector (getX() * a, getY() * a, getZ() * a);
	}
	public Vector add(Vector other) {
		return new Vector(getX()+other.getX(),getY()+other.getY(),getZ()+other.getZ());
	}
	public Vector sub(Vector other) {
		return new Vector(getX()-other.getX(),getY()-other.getY(),getZ()-other.getZ());
	}
	public double dot(Vector other) {
		return getX() * other.getX() + getY() * other.getY() + getZ() * other.getZ();
	}
	public Vector cross(Vector other) {
		return new Vector(getY() * other.getZ() - getZ() * other.getY(), getZ() * other.getX() - getX() * other.getZ(), getX() * other.getY() - getY() * other.getX());
	}
	
	public double getDistance(Vector other) {
		return sub(other).getMagnitude();
	}
	public static Vector center(Vector a, Vector b, Vector c) {
		double X = (a.getX() + b.getX() + c.getX())/3;
		double Y = (a.getY() + b.getY() + c.getY())/3;
		double Z = (a.getZ() + b.getZ() + c.getZ())/3;
		return(new Vector(X,Y,Z));
	}

	public String toString() {
		return "<"+getX()+","+getY()+","+getZ()+">";
	}
}
