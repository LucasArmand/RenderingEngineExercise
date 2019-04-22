import javax.swing.JFrame;

public class MainEngine {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(200,100,600,600);
		frame.setVisible(true);
		RenderWindow r = new RenderWindow(600,600);
		frame.setLayout(null);
		Tri t = new Tri(new Vector(0,0,.5),new Vector(1,0,.5),new Vector(0,1,1));
		System.out.println("tri normal: " + t.getNormal());
		Ray ray = new Ray(new Vector(0,0,-10),new Vector(0,0,0));
		System.out.println(ray.getHit(t));
		
		//System.out.println(t.getNormal());
		r.setBounds(50,50,500,500);
		//System.out.println(new Vector(1,2,1).cross(new Vector(1,2,2)));
		frame.add(r);
		
		//System.out.println(new Vector(1,0,0).cross(new Vector(0,1,1)));
		//System.out.println(t.getDistance());
		double fov = 100;
		Ray[][] cameraRays = new Ray[500][500];
		for(double i = 0; i < 500; i++){
			for(double j = 0; j < 500; j++) {
				cameraRays[(int)i][(int)j] = new Ray(new Vector(0,0,-10),new Vector((i-250)/fov,(j-250)/fov,1));
				int hitVal = (int)(cameraRays[(int)i][(int)j].getHit(t));
				r.setPixel((int)i,(int)j, new int[] {hitVal,hitVal,hitVal});
			}
			//System.out.println(new Vector((i-50)/10,(0)/10,1) +", " +cameraRays[(int)i][(int)500].getHit(t));
		}
		
		r.updateRender();
	}
}
;