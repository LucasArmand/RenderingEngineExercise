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
		double[] hitCoord = null;
		for(Tri t : tris) {
			double[] hit = r.getHit(t);
			if(hit.length == 3) {
				if(hitCoord == null || (hit[2] < hitCoord[2])) {
					hitCoord = hit;
				}
			}
		}
		if(hitCoord != null) {
			return texture.getRaster().getPixel((int)(hitCoord[0]*50)%texture.getWidth(),(int)(hitCoord[1]*50)%texture.getHeight(),new int[] {0,0,0});
		}
		return new int[] {0,0,0};
		}
}
