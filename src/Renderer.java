import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Renderer {
	private int[][][] data;
	private int width;
	private int height;
	public Renderer(int w, int h) {
		width = w;
		height = h;
		data = new int[w][h][3];
	}
	public void drawTri(Tri t,Tri p) {
		Matrix tMat = new Matrix(t);
		Matrix pMat = new Matrix(p);
		Matrix projection = tMat.getInverse().mult(pMat);
		Matrix antiProj = projection.getInverse();
		Vector newBaseX = t.getXBase().transform(projection);
		Vector newBaseY = t.getYBase().transform(projection);
		BufferedImage texture = t.texture;
		AffineTransform transform = new AffineTransform(new double[] {newBaseX.getX(),newBaseX.getY(),newBaseY.getX(),newBaseY.getY()});
		
		for(int x = Math.max(-width/2,(int)p.minX()); x <= Math.min(width/2,(int)p.maxX()); x++) {
			for(int y = Math.max(-height/2,(int)p.minY()); y <= Math.min(height/2, (int)p.maxY()); y++) {
				
			}
		}
	}
}
