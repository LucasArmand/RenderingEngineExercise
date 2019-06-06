
public class RenderSplit extends Thread{
	int startX, startY, gapX, gapY;
	int[] a;
	int[][][] d;
	public RenderSplit (int sX, int sY, int gX, int gY,int[][][] data, int[] arr) {
		startX = sX;
		startY = sY;
		gapX = gX;
		gapY = gY;
		a = arr;
		d = data;
	}
	public void run() {
		
		for(int i = startX; i < startX + gapX; i++) {
			for(int j = startY; j < startY + gapY; j++) {
				//System.out.println(x +": " + y +" : " + i +" " + j);
				for(int k = 0; k < 3; k++) {
					a[i*d.length*3 + j*3 + k] = d[j][i][k];
				}
			}
		}
	}
}
