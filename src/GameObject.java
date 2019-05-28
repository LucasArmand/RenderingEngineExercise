import java.io.IOException;

public class GameObject {
	private Mesh mesh;
	private Vector location;
	private Vector locRot;
	public GameObject(GameObject go) throws IOException {
		mesh = go.getMesh().copy();
		location = go.getLocation().copy();
		locRot = go.getRot().copy();
	}
	
	public GameObject(Mesh m) throws IOException {
		mesh = m;
		location = new Vector(0,0,0);
		locRot = new Vector(0,0,0);
	}
	public Mesh getMesh() {
		return mesh;
	}
	public void setRot(Vector rot) {
		locRot = rot.copy();
		mesh.rotate(locRot.getX(), locRot.getY(), locRot.getZ());
	}
	public void setLocation (Vector loc) {
		location = loc.copy();
		mesh.translate(location);
	}

	public Vector getLocation() {
		return location.copy();
	}
	public Vector getRot() {
		return locRot.copy();
	}
}
