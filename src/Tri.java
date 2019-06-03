import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tri {
	private Vector[] points;
	private Vector[] oPoints;
	private Vector normal;
	private double distance;
	private Vector center;
	private Vector offset;
	private Vector xBase;
	private Vector yBase;
	BufferedImage texture;

	public Vector[] getPoints() {
		return points;
	}
	public boolean flatInsideTri(Vector p) {
		
		double area = Math.abs((points[0].getX() * (points[1].getY() - points[2].getY()) + points[1].getX() * (points[2].getY() - points[0].getY()) + points[2].getX() * (points[0].getY()-points[1].getY()))/2);
		double a1 =  Math.abs((p.getX() * (points[1].getY() - points[2].getY()) + points[1].getX() * (points[2].getY() - p.getY()) + points[2].getX() * (p.getY()-points[1].getY()))/2);
		double a2 =  Math.abs((points[0].getX() * (p.getY() - points[2].getY()) + p.getX() * (points[2].getY() - points[0].getY()) + points[2].getX() * (points[0].getY()-p.getY()))/2);
		double a3 =  Math.abs((points[0].getX() * (points[1].getY() - p.getY()) + points[1].getX() * (p.getY() - points[0].getY()) + p.getX() * (points[0].getY()-points[1].getY()))/2);
		//System.out.println(area);
		//System.out.println(a1 + a2 + a3);
		
		if(Math.abs(a1 + a2 + a3  - area) < 0.1) {
			return true;
		}
		return false;
	}
	
	public boolean insideTri(Vector p) {
		
		boolean noHit = false;
		Vector ABcrossAP = points[0].sub(points[1]).cross(points[0].sub(p));
		Vector ABcrossAC = points[0].sub(points[1]).cross(points[0].sub(points[2]));
		//System.out.println(ABcrossAP +" and " + ABcrossAC);
		if(ABcrossAP.dot(ABcrossAC) < 0) {
			noHit = true;
		}
		
		Vector BCcrossBP = points[1].sub(points[2]).cross(points[1].sub(p));
		Vector BCcrossBA = points[1].sub(points[2]).cross(points[1].sub(points[0]));
		//System.out.println(BCcrossBP +" and " + BCcrossBA);
		if(BCcrossBP.dot(BCcrossBA) < 0) {
			noHit = true;
		}
		
		Vector CAcrossCP = points[2].sub(points[0]).cross(points[2].sub(p));
		Vector CAcrossCB = points[2].sub(points[0]).cross(points[2].sub(points[1]));
		//System.out.println(CAcrossCP +" and " + CAcrossCB);
		if(CAcrossCP.dot(CAcrossCB) < 0) {
			noHit = true;
		}
		return !noHit;
	}
	public double[] getCoords (Vector p) {
		Vector xBase = points[1].sub(points[0]);
		xBase.normalize();
		//System.out.println("x:" + xBase);
		
		Vector yBase = normal.cross(xBase);
		yBase.normalize();
		//System.out.println("y: " + yBase);
		//System.out.println(Vector.project(xBase,p.sub(points[0])));
		return new double[] {Vector.project(xBase, points[0].sub(p)).getMagnitude(),Vector.project(yBase, p.sub(points[0])).getMagnitude()};
	}
	public int[] getTexData(double[] c) {
		if(c[0] > 0 && c[0] < texture.getWidth() && c[1] > 0 && c[1] < texture.getHeight()) {
			return texture.getRaster().getPixel((int)c[0],(int)c[1], new int[3]);
		}
		return new int[] {0,0,0};
	}
	public Vector getXBase() {
		return xBase;
	}
	public Vector getYBase() {
		return yBase;
	}
	
	public Vector getNormal() {
		return normal;
	}
	public Vector getCenter() {
		return center;
	}
	public double getDistance() {
		return distance;
	}
	public void update() {
		center = Vector.center(points[0], points[1], points[2]);
		distance = center.getMagnitude();
		normal = points[0].sub(points[1]).cross(points[1].sub(points[2]));
		normal.normalize();
	}
	public void rotate (double ax,double ay, double az) {
		for(int i = 0; i < 3; i++) {
			double x,y,z;
			y = Math.cos(ax) * oPoints[i].getY() + Math.sin(ax) * oPoints[i].getZ();
			z = -Math.sin(ax) * oPoints[i].getY() + Math.cos(ax) * oPoints[i].getZ();
			points[i] = new Vector(oPoints[i].getX(),y,z);
			x = Math.cos(ay) * points[i].getX() + Math.sin(ay) * points[i].getZ();
			z = -Math.sin(ay) * points[i].getX() + Math.cos(ay) * points[i].getZ();
			points[i] = new Vector(x,points[i].getY(),z);
			x = Math.cos(az) * points[i].getX() + Math.sin(az) * points[i].getY();
			y = -Math.sin(az) * points[i].getX() + Math.cos(az) * points[i].getY();
			points[i] = new Vector(x,y,points[i].getZ());
			points[i] = points[i].add(offset);
		}
		update();
	}
	public Tri translate(Vector v) {
		return new Tri(points[0].add(v),points[1].add(v),points[2].add(v));
	}
	public void move(Vector v) {
		for(int i = 0; i < points.length; i++)
			points[i].modify(v);
	}
	
	public void setXBase(Vector b) {
		xBase = b;
	}
	public void setYBase(Vector b) {
		yBase = b;
	}
	public Tri copy() {
		return new Tri(points[0],points[1],points[2]);
	}
	public double maxY() {
		double max = points[0].getY() > points[1].getY() ? points[0].getY() : points[1].getY();
		return max > points[2].getY() ? max : points[2].getY();
	}
	public double maxX() {
		double max = points[0].getX() > points[1].getX() ? points[0].getX() : points[1].getX();
		return max > points[2].getX() ? max : points[2].getX();
	}
	public double minY() {
		double min = points[0].getY() < points[1].getY() ? points[0].getY() : points[1].getY();
		return min < points[2].getY() ? min : points[2].getY();
	}
	public double minX() {
		double min = points[0].getX() < points[1].getX() ? points[0].getX() : points[1].getX();
		return min < points[2].getX() ? min : points[2].getX();
	}
	
	public Tri project (Vector origin, Vector screenPos, Vector screenNorm) {
		//Tri save = copy();

		Vector[] nPoints = new Vector[3];
		for(int i = 0; i < 3; i++) {
			Ray r = new Ray(origin, points[i].sub(origin));
			double t = (screenPos.sub(r.getOrigin()).dot(screenNorm))/(r.getDirection().dot(screenNorm));
			if(t <0) {
				return null;
			}
			nPoints[i] = r.getDirection().multiply(t).add(r.getOrigin());
		}

		return new Tri(nPoints[0],nPoints[1],nPoints[2]);
		
	}

	
	public void setTexture(BufferedImage tex) {
		texture = tex;
	}
	public Tri(Vector a, Vector b, Vector c) {
		points = new Vector[] {a,b,c};
		oPoints = new Vector[] {a,b,c};
		offset = new Vector(0,0,0);
		center = Vector.center(a, b, c);
		distance = center.getMagnitude();
		normal = a.sub(b).cross(b.sub(c));
		normal.normalize();
		try {
			texture = ImageIO.read(new File( "C:\\Users\\larmand21\\Desktop\\tex2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xBase = points[1].sub(points[0]);
		xBase.normalize();
		yBase = normal.cross(xBase);
		yBase.normalize();
	}
}
