import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
	
	public void clear() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				//zBuffer[i][j] = maxDist;
				for(int k = 0; k < 3; k++) {
					if(data[i][j][k] != 0) {
						data[i][j][k] = 0;
						
					}
					
				}
			}
		}
	}
	
	public void drawTri(Tri t,Tri p) {
		Matrix tMat = new Matrix(t);
		Matrix pMat = new Matrix(p);

		Matrix projection = tMat.getInverse().mult(pMat);
		
		Matrix antiProj = projection.getInverse();
		Vector newBaseX = t.getXBase().transform(projection);
		Vector newBaseY = t.getYBase().transform(projection);
		//System.out.println(projection);
		//System.out.println(t.getXBase());
		//System.out.println(newBaseX);
		BufferedImage texture = t.texture;
		BufferedImage nTex = new BufferedImage(texture.getWidth(),texture.getHeight(),texture.getType());
		AffineTransform transform = new AffineTransform(new double[] {newBaseX.getX(),newBaseX.getY(),newBaseY.getX(),newBaseY.getY()});
		AffineTransformOp op = new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
		nTex = op.filter(texture, nTex);
		Vector point = new Vector(0,0,0);
		for(int x = Math.max(-width/2,(int)p.minX()); x <= Math.min(width/2,(int)p.maxX()); x++) {
			for(int y = Math.max(-height/2,(int)p.minY()); y <= Math.min(height/2, (int)p.maxY()); y++) {
				point.setX(x);
				point.setY(y);
				point.setZ(p.getPoints()[0].getZ());
				if(width/2 + x > 0 && width/2 + x < width && height/2 + y > 0 && height/2 + y < height) {
					if(p.insideTri(point)) {
						double[] c = p.getCoords(point);
						if(c[0] > 0 && c[0] < texture.getWidth() && c[1] > 0 && c[1] < texture.getHeight()) {
							RasterEngine.r.setPixel(width/2 + x, height/2 + y,nTex.getRaster().getPixel((int)c[0],(int)c[1], new int[3]));
							//System.out.println("setting pixel: "  +x +", " + y);
						}
						//data[width/2 + x][height/2 + y] = texture.getRaster().getPixel((int)coords[0], (int)coords[1], new int[3]);
					}
				}
			}
		}
	}
}
