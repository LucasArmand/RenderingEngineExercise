
public class GameObject {
	private Mesh mesh;
	private Vector location;
	private Vector locRot;
	
	public GameObject(Mesh m) {
		mesh = m;
		location = new Vector(0,0,0);
		locRot = new Vector(0,0,0);
	}
}
