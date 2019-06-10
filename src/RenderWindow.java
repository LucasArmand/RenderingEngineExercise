import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class RenderWindow extends JPanel{
	private BufferedImage render;
	private static int[][][] data;
	private WritableRaster raster;
	private double[][] zBuffer;
	private int width;
	private int height;
	private int w;
	private int h;
	private double maxDist = 10000;
	int [] bigData;
	RenderWindow(int x, int y){
		render = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
		raster= render.getRaster();
		data = new int[x][y][3];
		width = x;
		height = y;
		w = width/2;
		h = height/2;
		zBuffer = new double[x][y];
		bigData = new int[data.length * data[0].length * 3];
	}
	public void clear() {
		
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				for(int k = 0; k < 3; k++){
					data[i][j][k] = 0;
					}
				//raster.setPixel(i, j, new int[] {0,0,0});
			}
		}
		
	}
	
	public void drawTri(Tri t,Tri p) {
		Matrix tMat = new Matrix(t);
		Matrix pMat = new Matrix(p);
		Matrix projection = tMat.getInverse().mult(pMat);
		Matrix ap = projection.getInverse();
		Vector point = new Vector(0,0,0);
		
		//System.out.println("init: " + (System.nanoTime() - a));
		
		
		for(int x = Math.max(-width/2,(int)p.minX()); x <= Math.min(width/2,(int)p.maxX()); x++) {
			for(int y = Math.max(-height/2,(int)p.minY()); y <= Math.min(height/2, (int)p.maxY()); y++) {
				//System.out.println(x +", " + y);
				//point= new Vector(x,y,p.getPoints()[0].getZ());
				
				point.setX(x);
				point.setY(y);
				point.setZ(p.getPoints()[0].getZ());
				//point.setArr(new double[] {x,y,p.getPoints()[0].getZ()});
				
				if(w+ x > 0 && w + x < width && h + y > 0 && h + y < height) {
					
					//if(zBuffer[w + x][h + y] > point.transform(ap).getMagnitude()){
						//System.out.println("e");
						//System.out.println(point);
						if(p.flatInsideTri(point)) {
							
							//System.out.println("hi");
							//raster.setPixel(w+x, h+y,t.getTexData(t.getCoords(point.transform(ap))));
							data[w + x][h + y] = t.getTexData(t.getCoords(point.transform(ap)));
							
							//zBuffer[w + x][h + y] = point.transform(ap).getMagnitude();
						}

					//}

				}
				
				
				
				
			}
		}
		
		//System.out.println("loop: " +( total));
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
		data[x][y] = val;
		//raster.setPixel(x, y, val);
		
	}
	
	public void updateRender() {
		
		
		int divX = 20;
		int divY = 20;
		int xGap = data.length / divX;
		int yGap = data[0].length / divY;
		//System.out.println(xGap);
		ExecutorService es = Executors.newCachedThreadPool();
		for(int x = 0; x < divX; x++) {
			for(int y = 0; y < divY; y++) {
				RenderSplit rs = new RenderSplit(x * xGap,y * yGap,xGap,yGap,data,bigData);
				es.execute(rs);
				/*
				for(int i = x * xGap; i < (x+1)*xGap; i++) {
					for(int j = y * yGap; j < (y+1)*yGap; j++) {
						//System.out.println(x +": " + y +" : " + i +" " + j);
						for(int k = 0; k < 3; k++) {
							bigData[i*height*3 + j*3 + k] = data[j][i][k];
						}
					}
				}
				*/
				
			}
		}
		es.shutdown();
		try {
			es.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				for(int k = 0; k < 3; k++) {
					//raster.setPixel(i,j,data[i][j]);
					bigData[i*height*3 + j*3 + k] = data[j][i][k];
				}
				
			}
		}
		*/
		raster.setPixels(0,0,width,height,bigData);
		
		//System.out.println(System.currentTimeMillis()
		repaint();
	}
	public void paint(Graphics g) {
		//AffineTransform t = new AffineTransform(new double[] {2,0,0,1});
		//AffineTransformOp op = new AffineTransformOp(t,AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(render,0,0,null);
	}
}
