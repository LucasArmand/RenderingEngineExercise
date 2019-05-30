
public class TriRender extends Thread{
	Tri t;
	Vector o;
	Vector sp;
	Vector sn;
	Vector p;
	public TriRender(Tri tri, Vector position, Vector origin, Vector screenPos, Vector screenNorm) {
		t = tri;
		p = position;
		o = origin;
		sp = screenPos;
		sn = screenNorm;
	}
	public void run() {
		t.move(p);
		
		Tri p = t.project(o, sp, sn);
		if(p != null) {
			RasterEngine.renderer.drawTri(t, p);
			
		}
	}
}
