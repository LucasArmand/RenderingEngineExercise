import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainEngine {
	
	static boolean rightHeld,leftHeld,upHeld,downHeld,forwardHeld,backHeld;
	static Vector cameraPosition;
	static double xAngle = 0;
	static double yAngle = 0;
	static double zAngle = 0;
	static Mesh m;
	static BufferedImage texture;
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,500,500);
		frame.setVisible(true);
		RenderWindow r = new RenderWindow(250,250);
		frame.setLayout(null);
		Tri t = new Tri(new Vector(-10,0,0),new Vector(0,10,0),new Vector(10,0,0));
		System.out.println("tri normal: " + t.getNormal());
		Ray ray = new Ray(new Vector(0,0,-10),new Vector(0,0,0));
		System.out.println(ray.getHit(t));

		texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex1.jpg"));
		System.out.println(texture.getHeight());

		r.setBounds(0,0,250,250);
		//System.out.println(new Vector(1,2,1).cross(new Vector(1,2,2)));
		frame.add(r);
		JLabel framerateText = new JLabel("FPS: ");
		framerateText.setBounds(250,250,200,100);
		frame.add(framerateText);
		double fov = 300;
		Ray[][] cameraRays = new Ray[250][250];
		cameraPosition = new Vector(0,0,-20);
		Vector a = new Vector(-5,0,-5);
		Vector b = new Vector(5,0,-5);
		Vector c = new Vector(0,0,5);
		Vector d = new Vector(0,-7.07,0);
		Tri t1 = new Tri(a,b,d);
		Tri t2 = new Tri(b, c,d);
		Tri t3 = new Tri(c, a,d);
		Tri t4 = new Tri(a,b,c);
		System.out.println(t2.getXBase() +", : " + t2.getYBase());
		try {
			m = new Mesh(new Vector(0,0,0),t1,t2,t3,t4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timer timer = new Timer();
		Tri test = new Tri(new Vector(0,0,0),new Vector(1,1,0),new Vector(1,2,0));
		double[] point = test.getCoords(new Vector(.5,1.5,0));
		System.out.println(point[0] +", " + point[1]);
		/*
		Vector v1 = new Vector (1,1,0);
		Vector v2 = new Vector(0,0,1);
		System.out.println(Vector.project(v2, v1));
		*/
		
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

		
		TimerTask update = new TimerTask() {
			public void run() {
				m.rotate(xAngle,yAngle,zAngle);
				if(forwardHeld) {
					cameraPosition = cameraPosition.add(new Vector(0,0,.5));
				}
				if(backHeld) {
					cameraPosition = cameraPosition.add(new Vector(0,0,-.5));
				}
				if(upHeld) {
					cameraPosition = cameraPosition.add(new Vector(0,-.5,0));
				}
				if(rightHeld) {
					cameraPosition = cameraPosition.add(new Vector(.5,0,0));
				}
				if(leftHeld) {
					cameraPosition = cameraPosition.add(new Vector(-.5,0,0));
				}
				if(downHeld) {
					cameraPosition = cameraPosition.add(new Vector(0,.5,0));
				}
				
				
				//Tri other = new Tri(new Vector(-5,0,1),new Vector (-5,0,1), new Vector(0,0,1));
				for(double i = 0; i < 250; i++){
					for(double j = 0; j < 250; j++) {
						cameraRays[(int)i][(int)j] = new Ray(cameraPosition,new Vector((i-250)/fov,(j-250)/fov,1));
						int[] pixel = m.renderRay(cameraRays[(int)i][(int)j]);
						/*
						  double[] hitVals = (cameraRays[(int)i][(int)j].getHit(t));
						 
						int[] pixel;
						if(hitVals.length == 2) {
							//System.out.println(hitVals[0] +", " + hitVals[1]);
							
							pixel = texture.getRaster().getPixel((int)(hitVals[0]*50)%texture.getWidth(),(int)(hitVals[1]*50)%texture.getHeight(),new int[] {0,0,0});
							//System.out.println(pixel[0]);
							//pixel = new int[] {5,10,100};
							
						}else {
							pixel = new int[]{0,0,0};
						}
						*/
						r.setPixel((int)i,(int)j, pixel);
						//int[] oHitVals = (cameraRays[(int)i][(int)j].getHit(other));
						
					}
					//System.out.println(new Vector((i-50)/10,(0)/10,1) +", " +cameraRays[(int)i][(int)500].getHit(t));
				}
				r.updateRender();
			}
		};
		
		timer.schedule(update, 5,10);
		
	}
}
;