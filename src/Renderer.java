import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Renderer {
	private int[][][] data;
	private int width;
	private int height;
	Vector point;
	public Renderer(int w, int h) {
		width = w;
		height = h;
		data = new int[w][h][3];
		point = new Vector(0,0,0);
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
		Vector point = new Vector(0,0,0);
		//System.out.println(nTex.getHeight());
		for(int x = Math.max(-width/2,(int)p.minX()); x <= Math.min(width/2,(int)p.maxX()); x++) {
			for(int y = Math.max(-height/2,(int)p.minY()); y <= Math.min(height/2, (int)p.maxY()); y++) {
				point.setX(x);
				point.setY(y);
				point.setZ(p.getPoints()[0].getZ());
				if(width/2 + x > 0 && width/2 + x < width && height/2 + y > 0 && height/2 + y < height) {
					
					if(p.flatInsideTri(point)) {
//
							//System.out.println(nTex.getRaster().getPixel((int)c[0],(int)c[1], new int[3])[0]);
							int colorData[] = new int[] {0,255,0};
							//System.out.println(t.getPoints()[2] +", " + t.getPoints()[2].transform(ap));
							//colorData = t.getTexData(t.getCoords(point.transform(ap)));
							RasterEngine.r.setPixel(width/2 + x, height/2 + y,colorData);
							//RasterEngine.r.setPixel(width/2 + x, height/2 + y,nTex.getRaster().getPixel((int)c[0],(int)c[1], new int[3]));
							//RasterEngine.r.setPixel(width/2 + x, height/2 + y,nTex.getRaster().getPixel((int)c[0],(int)c[1], new int[3]));
							//System.out.println("setting pixel: "  +x +", " + y);
						//data[width/2 + x][height/2 + y] = texture.getRaster().getPixel((int)coords[0], (int)coords[1], new int[3]);
					}
				}
			}
		}
	}
}
