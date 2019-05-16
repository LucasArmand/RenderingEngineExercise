import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Mesh {
	private ArrayList<Tri> tris;
	private Vector position;
	private BufferedImage texture;
	private double angleX, angleY, angleZ;
	
	public Mesh(Vector p, Tri... nTris) throws IOException {
		texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex1.jpg"));
		position = p.copy();
		tris = new ArrayList<Tri>();
		for(Tri t: nTris) {
			t.translate(p);
			tris.add(t);
		}
	}
	public void rotate(double ax,double ay,double az) {
		for(Tri t : tris) {
			t.rotate(ax, ay, az);
		}
	}
	public int[] renderRay(Ray r) {
		Tri[] order = new Tri[tris.size()];
		ArrayList<Tri> unorder = (ArrayList<Tri>)tris.clone();
		Tri closest = unorder.get(0);
		for(int j = 0; j < order.length; j++) {
			for(int i = 0; i < unorder.size(); i++) {
				if(r.getT(closest) > r.getT(tris.get(i))){
					closest = tris.get(i);
				}
			}
			order[j] = closest;
			unorder.remove(closest);
			
		}
		double[] hitCoord;
		int count = -1;
		do {
			count++;
			if (count >= tris.size()) {
				return new int[] {0,0,0};
			}
			hitCoord = r.getHitCoord(order[count]);
			
			
		}while(order[count].insideTri(r.multiply(r.getT(order[count]))));
		
		if(hitCoord != null && hitCoord.length == 2) {
			//System.out.println("len: " + hitCoord.length);
			return texture.getRaster().getPixel((int)(hitCoord[0]*50)%texture.getWidth(),(int)(hitCoord[1]*50)%texture.getHeight(),new int[] {0,0,0});
		}
		return new int[] {0,0,0};
		}
}
