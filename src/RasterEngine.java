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
	static int xSize = 400;
	static int ySize = 400;
	static boolean rightHeld,leftHeld,upHeld,downHeld,forwardHeld,backHeld;
	static Vector cameraPosition;
	static Renderer renderer;
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
	static RenderWindow r;
	static BufferedImage texture;
	static Tri tri;
	static Mesh m;
	static Tri tri2;
	static Tri projTri;
	static Mesh m1;
	static World world;
	static double speed;
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,xSize,ySize);
		frame.setVisible(true);
		r = new RenderWindow(xSize,ySize);
		r.setBounds(0,0,xSize,ySize);
		frame.add(r);
		texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex2.jpg"));
		//point = new Vector(2,0,20);
		points = new Vector[] {new Vector(0,0,100),new Vector(2,0,100),new Vector(0,-200,100),new Vector(0,0,10), new Vector(10,0,10), new Vector(100,1,5)};
		//projs = new Vector[points.length];
		speed = 10;
		Vector origin = new Vector(0,0,0);
		Vector screenPos = new Vector(0,0,1000);
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
		tri2 = new Tri(points[3],points[4],points[5]);
		renderer = new Renderer(xSize,ySize);
		Vector a = new Vector(50,0,0);
		Vector b = new Vector(50,0,50);
		Vector c = new Vector(0,0,50);
		Vector d = new Vector(0,0,0);
		Vector a1= new Vector(50,50,0);
		Vector b1 = new Vector(50,50,50);
		Vector c1 = new Vector(0,50,50);
		Vector d1 = new Vector(0,50,0);
		
		Tri t1 = new Tri(a,b,c);
		Tri t2 = new Tri(c,d,a);
		Tri t3 = new Tri(a1,b1,c1);
		Tri t4 = new Tri(c1,d1,a1);
		Tri t5 = new Tri(a,d,a1);
		Tri t6 = new Tri(d,a1,d1);
		Tri t7 = new Tri(d,c,c1);
		Tri t8 = new Tri(d,d1,c1);
		Tri t9 = new Tri(c,b,b1);
		Tri t10 = new Tri(b1,c1,c);
		Tri t11 = new Tri(a,b,b1);
		Tri t12 = new Tri(b1,a,a1);
		
		//tri.setTexture(texture);
		
		Tri tri = new Tri(new Vector(0,0,100),new Vector(100,0,100),new Vector(0,-100,100));
		m = new Mesh(new Vector(0,0,10),tri);
		
		tri2.setTexture(texture);
		m = new Mesh(new Vector(0,0,100),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12);
		//m1 = new Mesh(new Vector(-100,0,100),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12);
		world = new World();
		GameObject object = new GameObject(m);
		//world.addObjects(new GameObject(m));
		world.addObjects(object);
		//System.out.println(tri.flatInsideTri(new Vector(1,10,0)));
		/*
		world.addObjects(new GameObject(m));
		//world.getObject(1).setLocation(new Vector(50,0,0));
		for(int i = 0; i < 10; i++) {
			world.addObjects(new GameObject(m.copy()));
			world.getObject(world.getObjects().size() - 1).setLocation(new Vector(50 * (i + 1),0,0));
		}
		*/
		r.updateRender();
		Timer timer = new Timer();
		//TimerTask update = new TimerTask() {
		
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
			//public void run() {
			while(true) {
				frames ++;
				if (System.currentTimeMillis() - lastTime > 1000) {
					System.out.println(frames +"fps");
					frames = 0;
					lastTime = System.currentTimeMillis();
				}
				//renderer.clear();
				r.clear();
				for(GameObject o : world.getObjects()) {
					
					if(rightHeld) {
						o.setLocation(new Vector(speed,0,0));
						
					}
					if(upHeld) {
						o.setLocation(new Vector(0,-speed,0));
					}
					if(forwardHeld) {
						o.setLocation(new Vector(0,0,speed));
					}
					if(leftHeld) {
						o.setLocation(new Vector(-speed,0,0));
					}
					if(downHeld) {
						o.setLocation(new Vector(0,speed,0));
					}
					if(backHeld) {
						o.setLocation(new Vector(0,0,-speed));
					}
					
				
				
					o.setRot(new Vector(xAngle,yAngle,zAngle));
	
					o.getMesh().renderMesh(origin, screenPos, screenNorm);
				
					//projTri = tri.project(origin, screenPos, screenNorm);s
				
					//r.drawTri(tri, projTri);
				}
				r.updateRender();
				
				
			}
			
		}
		//timer.schedule(update, 5,1);
		
		
	}

