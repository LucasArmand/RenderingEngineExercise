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
	private double[][] zBuffer;
	private int width;
	private int height;
	private double maxDist = 10000;
	RenderWindow(int x, int y){
		render = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
		raster= render.getRaster();
		data = new int[x][y][3];
		width = x;
		height = y;
		zBuffer = new double[x][y];
	}
	public void clear() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				zBuffer[i][j] = maxDist;
				for(int k = 0; k < 3; k++) {
					if(data[i][j][k] != 0) {
						data[i][j][k] = 0;
						
					}
					
				}
			}
		}
	}
	
	public void drawTri(Tri t,Tri p) {
		double a = System.currentTimeMillis();
		Matrix tMat = new Matrix(t);
		Matrix pMat = new Matrix(p);
		Matrix projection = tMat.getInverse().mult(pMat);
		Matrix ap = projection.getInverse();
		Vector point = new Vector(0,0,0);
		
		for(int x = Math.max(-width/2,(int)p.minX()); x <= Math.min(width/2,(int)p.maxX()); x++) {
			for(int y = Math.max(-height/2,(int)p.minY()); y <= Math.min(height/2, (int)p.maxY()); y++) {
				//System.out.println(x +", " + y);
				
				point.setArr(new double[] {x,y,p.getPoints()[0].getZ()});

				if(width/2 + x > 0 && width/2 + x < width && height/2 + y > 0 && height/2 + y < height) {
					if(zBuffer[width/2 + x][height/2 + y] > point.transform(ap).getMagnitude()){
						if(p.insideTri(point)) {
							data[width/2 + x][height/2 + y] = t.getTexData(t.getCoords(point.transform(ap)));
							zBuffer[width/2 + x][height/2 + y] = point.transform(ap).getMagnitude();
						}

					}

				}
				
			}
		}
		//System.out.println(System.currentTimeMillis() - a);
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
		//raster.setPixel(x, y, val);
		for(int i = 0; i < 3; i++) {
			data[x][y][i] = val[i];
		}
	}
	
	public void updateRender() {
		
		double a = System.currentTimeMillis();
		int [] bigData = new int[data.length * data[0].length * 3];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				for(int k = 0; k < 3; k++) {
					bigData[i*height*3 + j*3 + k] = data[j][i][k];
				}
				
			}
		}
		raster.setPixels(0,0,width,height,bigData);
		
		//System.out.println(System.currentTimeMillis()
		repaint();
	}
	public void paint(Graphics g) {
		g.drawImage(render,0,0,null);
	}
}
