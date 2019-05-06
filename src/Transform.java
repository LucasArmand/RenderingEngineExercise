
public class Transform {
	private Vector[] matrix;
	
	public Transform(Vector a, Vector b) {
		matrix  = new Vector[2];
		matrix[0] = a;
		matrix[1] = b;
	}
	public Vector getA() {
		return matrix[0];
	}
	public Vector getB() {
		return matrix[1];
	}
}
