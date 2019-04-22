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
