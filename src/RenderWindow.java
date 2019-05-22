import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

import javax.swing.*;

public class RenderWindow extends JPanel{
	private BufferedImage render;
	private int[][][] data;
	private WritableRaster raster;
	
	private int width;
	private int height;
	RenderWindow(int x, int y){
		render = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
		raster= render.getRaster();
		data = new int[x][y][3];
		width = x;
		height = y;
	}
	public void clear() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
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
		Matrix ap = projection.getInverse();
		for(int x = (int)p.minX(); x <= (int)p.maxX(); x++) {
			for(int y = (int)p.minY(); y <= (int)p.maxY(); y++) {
				//System.out.println(x +", " + y);
				Vector point = new Vector(x, y,p.getPoints()[0].getZ());
				
				if(p.insideTri(point)){
					if(width/2 + x > 0 && width/2 + x < width && height/2 + y > 0 && height/2 + y < height )
						data[width/2 + x][height/2 + y] = new int[] {255,255,255};//t.getTexData(t.getCoords(point.transform(ap)));
					
					//System.out.println(x +", "+ y +" is in the tri");
				}
			}
		}
	}
	
	
	public void drawLine(int x1,int y1, int x2, int y2) {
		
		System.out.println(y1);
		double yDiff = y2-y1;
		double xDiff = x2-x1;
		double ratio;
		if (Math.abs(yDiff) < Math.abs(xDiff)) {
			ratio = ((double)(yDiff))/(xDiff == 0 ? 1 : xDiff);
			for(int x = x1; x <= x2; x = (x1 < x2 ? x + 1 : x-1)) {
					data[x1 + x][y1 + (int)(ratio * x)] = new int[] {255,255,255};
			}
		}
		else {
			ratio = ((double)(xDiff))/(yDiff == 0 ? 1 : yDiff);
			for(int y = y1; y <= y2; y = (y1 < y2 ? y + 1 : y-1)) {
				data[x1 + (int)(ratio * y)][y1 + y] = new int[] {255,255,255};
			}
		}
		
	}
	public void setPixel(int x, int y, int[] val) {
		for(int i = 0; i < 3; i++) {
			data[x][y][i] = val[i];
		}
	}
	
	public void updateRender() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				raster.setPixel(i,j,data[i][j]);
			}
		}
		repaint();
	}
	public void paint(Graphics g) {
		g.drawImage(render,0,0,null);
	}
}
