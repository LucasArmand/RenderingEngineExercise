import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class MainEngine {
	
	static boolean rightHeld,leftHeld,upHeld,downHeld,forwardHeld,backHeld;
	static Vector cameraPosition;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,600,600);
		frame.setVisible(true);
		RenderWindow r = new RenderWindow(600,600);
		frame.setLayout(null);
		Tri t = new Tri(new Vector(0,0,1),new Vector(20,-5,1),new Vector(0,-50,10));
		System.out.println("tri normal: " + t.getNormal());
		Ray ray = new Ray(new Vector(0,0,-10),new Vector(0,0,0));
		System.out.println(ray.getHit(t));
		
		//System.out.println(t.getNormal());
		r.setBounds(50,50,500,500);
		//System.out.println(new Vector(1,2,1).cross(new Vector(1,2,2)));
		frame.add(r);
		
		//System.out.println(new Vector(1,0,0).cross(new Vector(0,1,1)));
		//System.out.println(t.getDistance());
		double fov = 150;
		Ray[][] cameraRays = new Ray[500][500];
		cameraPosition = new Vector(0,0,-20);
		
		Timer timer = new Timer();
		
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
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
				Tri other = new Tri(new Vector(-5,5,1),new Vector (-5,-5,1), new Vector(0,0,1));
				for(double i = 0; i < 500; i++){
					for(double j = 0; j < 500; j++) {
						cameraRays[(int)i][(int)j] = new Ray(cameraPosition,new Vector((i-250)/fov,(j-250)/fov,1));
						int hitVal = (int)(cameraRays[(int)i][(int)j].getHit(t));
						hitVal += (int)(cameraRays[(int)i][(int)j].getHit(other));
						r.setPixel((int)i,(int)j, new int[] {hitVal,hitVal,hitVal});
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