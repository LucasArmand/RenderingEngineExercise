import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class RasterEngine {
	static int xSize = 500;
	static int ySize = 500;
	static boolean rightHeld,leftHeld,upHeld,downHeld,forwardHeld,backHeld;
	static Vector cameraPosition;
	static double xAngle = 0;
	static double yAngle = 0;
	static double zAngle = 0;
	static Ray ray;
	static double t;
	static Vector point;
	static Tri oTri;
	static Vector[] points;
	static Vector[] projs;
	static int[][][] pixels;
	static BufferedImage texture;
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,xSize,ySize);
		frame.setVisible(true);
		RenderWindow r = new RenderWindow(xSize,ySize);
		r.setBounds(0,0,xSize,ySize);
		frame.add(r);
		texture = ImageIO.read(new File( "C:\\Users\\lucas_000\\Desktop\\brick.jpg"));
		//point = new Vector(2,0,20);
		points = new Vector[] {new Vector(0,0,10),new Vector(0,-10,10),new Vector(10,0,10)};
		projs = new Vector[points.length];
		
		
		Vector origin = new Vector(0,0,0);
		Vector screenPos = new Vector(0,0,100);
		Vector screenNorm = new Vector(0,0,1);
		//System.out.println(tri.getPoints()[0] + ", " + tri.getPoints()[1] + ", " + tri.getPoints()[2] + ", " + tri.getXBase() + ", " + tri.getYBase());
		//tri = tri.project(origin, screenPos, screenNorm);
		//System.out.println(tri.getPoints()[0] + ", " + tri.getPoints()[1] + ", " + tri.getPoints()[2] + ", " + tri.getXBase() + ", " + tri.getYBase());
		
		Timer timer = new Timer();
		TimerTask update = new TimerTask() {

			@Override
			public void run() {
				r.clear();
				pixels = new int[xSize][ySize][3];
				for(int i = 0; i < points.length; i++) {
					
					if(rightHeld) {
						points[i] = points[i].add(new Vector(1,0,0));
					}
					if(upHeld) {
						points[i] = points[i].add(new Vector(0,-1,0));
					}
					if(forwardHeld) {
						points[i] = points[i].add(new Vector(0,0,.1));
					}
					if(leftHeld) {
						points[i] = points[i].add(new Vector(-1,0,0));
					}
					if(downHeld) {
						points[i] = points[i].add(new Vector(0,1,0));
					}
					if(backHeld) {
						points[i] = points[i].add(new Vector(0,0,-.1));
					}
				
				
					ray = new Ray(origin,points[i].sub(origin));
					t = (screenPos.sub(origin).dot(screenNorm))/(ray.getDirection().dot(screenNorm));
					projs[i] = ray.getDirection().multiply(t).add(origin);
					//System.out.println(point);
					
						//pixels[(int)projs[i].getX() + xSize /2][ (int)projs[i].getY() + ySize /2] = new int[] {255,255,255};
						
				}
				
				//r.drawLine(50, 10,20,20);
				//for(int i = 0; i < xSize; i++){
					//for(int j = 0; j < ySize; j++) {
				
			try {
				oTri = new Tri(points[0],points[1],points[2]);
				Tri tri = oTri.project(origin, screenPos, screenNorm);
				//Tri tri = oTri;
				System.out.println(tri.getPoints()[0] + ", " + tri.getPoints()[1] + ", " + tri.getPoints()[2] + ", " + tri.getXBase() + ", " + tri.getYBase());
				//r.drawLine((int)projs[projs.length -1 ].getX() + xSize /2, (int)projs[projs.length - 1].getY() + ySize /2, (int)projs[0].getX() + xSize /2, (int)projs[0].getY() + ySize /2);
				for(int k = 0; k < projs.length - 1; k++) {
					r.
					//r.drawLine((int)projs[k].getX() + xSize /2, (int)projs[k].getY() + ySize /2, (int)projs[k + 1].getX() + xSize /2, (int)projs[k+1].getY() + ySize /2);
				}
				r.drawLine((int)projs[0].getX() + xSize /2, (int)projs[0].getY() + ySize /2, (int)(tri.getXBase().getX() * 100) + xSize /2, (int)(tri.getXBase().getY() * 100)+ ySize /2);
				r.drawLine((int)projs[0].getX() + xSize /2, (int)projs[0].getY() + ySize /2, (int)(tri.getYBase().getX() * 100) + xSize /2, (int)(tri.getYBase().getY() * 100)+ ySize /2);
			}
			catch(Exception e) {}
						//r.setPixel(i,j, pixels[i][j]);
					//}
				//}
				r.updateRender();
			}
			
		};
		timer.schedule(update, 5,5);
		
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_E) {
					xAngle += .1;
					//System.out.println(xAngle);
				}
				if(e.getKeyCode() == KeyEvent.VK_Q) {
					xAngle -= .1;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					yAngle += .1;
					//System.out.println(yAngle);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					yAngle -= .1;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					zAngle += .1;
					//System.out.println(zAngle);
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					zAngle -= .1;
				}
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					upHeld = true;
					//System.out.println("w down");
					
					
				}
				if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
					downHeld = true;
					
					//System.out.println("w down");
					
				}
				if(e.getKeyCode() == KeyEvent.VK_W) {
					forwardHeld = true;
					//System.out.println("w down");
					
				}
				if(e.getKeyCode() == KeyEvent.VK_D) {
					rightHeld = true;
					
				}
				if(e.getKeyCode() == KeyEvent.VK_A) {
					leftHeld = true;
					
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					backHeld = true;
					
				}
				
				//guy.setBounds(guyX,guyY,51,51);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					upHeld = false;
					//System.out.println("w down");
					
				}
				if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
					downHeld = false;
					//System.out.println("w down");
					
				}
				if(e.getKeyCode() == KeyEvent.VK_W) {
					forwardHeld = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_D) {
					rightHeld = false;
					
				}
				if(e.getKeyCode() == KeyEvent.VK_A) {
					leftHeld = false;
					
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					backHeld = false;
					
				}
			}
			
		});
	}
}
