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
		data = new int[width][height][3];
	}
	
	public void drawTri(Tri t) {
		
	}
	
	
	public void drawLine(int x1,int y1, int x2, int y2) {
		int startX = x1 < x2 ? x1 : x2;
		int endX = x1 >= x2 ? x1 : x2;
		int startY = y1 < y2 ? y1 : y2;
		int endY = y1 >= y2 ? y1 : y2;
		
		double ratio = ((double)(y2 - y1))/(x2 - x1);
		endY = ratio > 0 ? startY : endY;
		//endX = ratio < 0 ? startX : endX;
		//if(ratio < 1) {
			for(int i = 0; i < endX - startX; i++) {
				data[startX + i][endY + (int)(ratio * i)] = new int[] {255,255,255};
			}
		//{
			//for(int i = 0; i < endY- startY; i++) {
			//	data[startX + (int)(1/ratio * i)][endY + i] = new int[] {255,255,255};
			//}
		//}
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
