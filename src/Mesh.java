import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Mesh {
	private ArrayList<Tri> tris;
	private Vector position;
	private BufferedImage texture;
	private Vector locRot;
	
	public Mesh(Vector p, Tri... nTris) throws IOException {
		//texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex1.jpg")); school
		texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex2.jpg"));
		position = p.copy();
		locRot = new Vector(0,0,0);
		tris = new ArrayList<Tri>();
		for(Tri t: nTris) {
			tris.add(t.copy());
		}
	}
	public Mesh copy() throws IOException {
			Tri[] copy = new Tri[tris.size()];
			for(int t = 0; t < copy.length; t++) {
				copy[t] = (tris.get(t).copy());
			}
			return new Mesh(position.copy(),copy);

	}
	
	public void renderMesh(Vector origin, Vector screenPos, Vector screenNorm, RenderWindow r) {
		ExecutorService es = Executors.newCachedThreadPool();
		for(Tri t : tris) {
			TriRender tr = new TriRender(t,position,origin,screenPos,screenNorm,r);
			es.execute(tr);
			//System.out.println(threads.size());
		}
		es.shutdown();
		try {
			es.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void rotate(double ax,double ay,double az) {
		for(Tri t : tris) {
			t.rotate(ax, ay, az);
		}
	}
	public void setPos(Vector p) {
		position = p.copy();
	}
	public void translate(Vector p) {
		position = position.add(p);
	}
	public int[] renderRay(Ray r) {
		Tri[] order = new Tri[tris.size()];
		ArrayList<Tri> unorder = (ArrayList<Tri>)tris.clone();
		
		for(int j = 0; j < order.length; j++) {
			Tri closest = unorder.get(0);
			for(int i = 0; i < unorder.size(); i++) {
				if(r.getT(closest) > r.getT(unorder.get(i))){
					closest = unorder.get(i);
				}
			}
			
			order[j] = closest;
			unorder.remove(closest);
			
		}
		double[] hitCoord;
		
		int count = 0;
		do {
			
			if (count >= order.length) {
				return new int[] {0,0,0};
			}
			hitCoord = r.getHitCoord(order[count]);
			count++;
			
		}while(hitCoord.length != 2);
		
		if(hitCoord != null && hitCoord.length == 2) {
			//System.out.println("len: " + hitCoord.length);
			return texture.getRaster().getPixel((int)(hitCoord[0]*50)%texture.getWidth(),(int)(hitCoord[1]*50)%texture.getHeight(),new int[] {0,0,0});
			
		}
		return new int[] {0,0,0};
		}
}
