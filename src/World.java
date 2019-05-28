import java.util.ArrayList;

public class World {

	private ArrayList<GameObject> objects;
	public World() {
		objects = new ArrayList<GameObject>();
	}
	public void addObjects(GameObject... o) {
		for(GameObject go : o) {
			objects.add(go);
		}
	}
	public ArrayList<GameObject> getObjects(){
		return objects;
	}
	public int numObjs() {
		return objects.size();
	}
	public GameObject getObject(int i) {
		return objects.get(i);
	}
}
