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
	static int xSize = 600;
	static int ySize = 600;
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
	static double lastTime;
	static int frames;
	static BufferedImage texture;
	static Tri tri;
	static Tri projTri;
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,xSize,ySize);
		frame.setVisible(true);
		RenderWindow r = new RenderWindow(xSize,ySize);
		r.setBounds(0,0,xSize,ySize);
		frame.add(r);
		texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex2.jpg"));
		//point = new Vector(2,0,20);
		points = new Vector[] {new Vector(0,0,10),new Vector(0,-100,10),new Vector(100,0,5)};
		projs = new Vector[points.length];
		
		Vector origin = new Vector(0,0,50);
		Vector screenPos = new Vector(0,0,10);
		Vector screenNorm = new Vector(0,0,1);
		
		tri = new Tri(points[0],points[1],points[2]);
		projTri = tri.project(origin, screenPos, screenNorm);
		Matrix tMat = new Matrix(tri);
		Matrix pMat = new Matrix(projTri);
		Matrix projection = tMat.getInverse().mult(pMat);
		System.out.println(pMat);
		System.out.println("(" + projTri.minX() +" - "+ projTri.maxX() +") (" + projTri.minY() +" - " + projTri.maxY() +")");
		System.out.println(projTri.getPoints()[2].transform(projection.getInverse()));
		//System.out.println(tri.getPoints()[0] + ", " + tri.getPoints()[1] + ", " + tri.getPoints()[2] + ", " + tri.getXBase() + ", " + tri.getYBase());
		//tri = tri.project(origin, screenPos, screenNorm);
		//System.out.println(tri.getPoints()[0] + ", " + tri.getPoints()[1] + ", " + tri.getPoints()[2] + ", " + tri.getXBase() + ", " + tri.getYBase());
		lastTime = System.currentTimeMillis();
		frames = 0;
		tri = new Tri(points[0],points[1],points[2]);
		tri.setTexture(texture);
		r.updateRender();
		Timer timer = new Timer();
		TimerTask update = new TimerTask() {
		
			@Override
			public void run() {
				frames ++;
				if (System.currentTimeMillis() - lastTime > 1000) {
					System.out.println(frames +"fps");
					frames = 0;
					lastTime = System.currentTimeMillis();
				}
				r.clear();

				for(int i = 0; i < points.length; i++) {
					
					if(rightHeld) {
						points[i].modify(new Vector(1,0,0));
					}
					if(upHeld) {
						points[i].modify(new Vector(0,-1,0));
					}
					if(forwardHeld) {
						points[i].modify(new Vector(0,0,.1));
					}
					if(leftHeld) {
						points[i].modify(new Vector(-1,0,0));
					}
					if(downHeld) {
						points[i].modify(new Vector(0,1,0));
					}
					if(backHeld) {
						points[i].modify(new Vector(0,0,-.1));
					}
					
					/*
					ray = new Ray(origin,points[i].sub(origin));
					t = (screenPos.sub(origin).dot(screenNorm))/(ray.getDirection().dot(screenNorm));
					projs[i] = ray.getDirection().multiply(t).add(origin);
					*/

				}
				
				//tri = new Tri(points[0],points[1],points[2]);
				//tri.setTexture(texture);
				projTri = tri.project(origin, screenPos, screenNorm);
				r.drawTri(tri, projTri);
				r.updateRender();
				
			}
			
		};
		timer.schedule(update, 5,1);
		
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
