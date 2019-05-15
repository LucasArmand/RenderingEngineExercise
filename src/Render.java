import java.awt.image.BufferedImage;

public class Render implements Runnable{
	int startX;
	int startY;
	int xRange;
	int yRange;
	int xSize;
	int ySize;
	Mesh m;
	int[][][] pixels;
	BufferedImage tex;
	Vector cameraPos;
	public Render(int x, int y,int xr, int yr,int xs,int ys,Vector pos, Mesh mesh,int[][][] p,BufferedImage texture) {
		startX = x;
		startY = y;
		xRange = xr;
		yRange = yr;
		xSize = xs;
		ySize = ys;
		cameraPos = pos;
		m = mesh;
		pixels = p;
		tex = texture;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(double i = startX; i < startX + xRange; i++){
			for(double j = startY; j < startY + yRange; j++) {
				Ray ray = new Ray(cameraPos,new Vector((i-xSize/2)/300,(j-ySize/2)/300,1));
				pixels[(int)i][(int)j] = m.renderRay(ray);
			}
		}
	}

}
