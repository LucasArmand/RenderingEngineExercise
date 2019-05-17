import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

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
	static int[][][] pixels;
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,xSize,ySize);
		frame.setVisible(true);
		RenderWindow r = new RenderWindow(xSize,ySize);
		r.setBounds(0,0,xSize,ySize);
		frame.add(r);
		
		point = new Vector(2,0,20);
		Vector origin = new Vector(0,0,0);
		Vector screenPos = new Vector(0,0,1);
		Vector screenNorm = new Vector(0,0,1);
		
		Timer timer = new Timer();
		TimerTask update = new TimerTask() {

			@Override
			public void run() {
				pixels = new int[xSize][ySize][3];
				if(rightHeld) {
					point = point.add(new Vector(1,0,0));
				}
				if(upHeld) {
					point = point.add(new Vector(0,-1,0));
				}
				if(forwardHeld) {
					point = point.add(new Vector(0,0,-.1));
				}
				if(leftHeld) {
					point = point.add(new Vector(-1,0,0));
				}
				if(downHeld) {
					point = point.add(new Vector(0,1,0));
				}
				if(backHeld) {
					point = point.add(new Vector(0,0,.1));
				}
				

				ray = new Ray(origin,point.sub(origin));
				t = (screenPos.sub(origin).dot(screenNorm))/(ray.getDirection().dot(screenNorm));
				Vector proj = ray.getDirection().multiply(t).add(origin);
				
				pixels[(int)proj.getX() + xSize /2][ (int)proj.getY() + ySize /2] = new int[] {255,255,255};
				//System.out.println(proj);
				for(int i = 0; i < xSize; i++){
					for(int j = 0; j < ySize; j++) {
						
						//cameraRays[(int)i][(int)j] = new Ray(cameraPosition,new Vector((i-xSize/2)/fov,(j-ySize/2)/fov,1));
						//int[] pixel = m.renderRay(cameraRays[(int)i][(int)j]);
						
						r.setPixel(i,j, pixels[i][j]);
						//int[] oHitVals = (cameraRays[(int)i][(int)j].getHit(other));
						
					}
					//System.out.println(new Vector((i-50)/10,(0)/10,1) +", " +cameraRays[(int)i][(int)500].getHit(t));
				}
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
