
public class TriRender extends Thread{
	Tri t;
	Vector o;
	Vector sp;
	Vector sn;
	Vector p;
	RenderWindow r;
	public TriRender(Tri tri, Vector position, Vector origin, Vector screenPos, Vector screenNorm,RenderWindow rw) {
		t = tri;
		p = position;
		o = origin;
		sp = screenPos;
		sn = screenNorm;
		r = rw;
	}
	public void run() {
		t.move(p);
		Tri p = t.project(o, sp, sn);
		if(p != null) {
			r.drawTri(t, p);
			
		}
	}
}
