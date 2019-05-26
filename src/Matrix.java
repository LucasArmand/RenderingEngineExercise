import java.util.Random;

public class Matrix {
	public static final Matrix ID = new Matrix(new double[][] {{1,0,0},{0,1,0},{0,0,1}});
	public double[][] matrix;
	Matrix(int rows, int columns) {
		matrix = new double[rows][columns];
	}
	Matrix(double[] data) {
		matrix = new double[1][data.length];
		for(int i = 0; i < data.length; i++) {
			matrix[0][i] = data[i];
		}
	}
	
	Matrix(double[][] data){
		matrix = data;
	}
	Matrix (Matrix m) {
		matrix = new double[m.matrix.length][m.matrix[0].length];
	}
	
	public Matrix (Tri t) {
		matrix = new double[][] {t.getPoints()[0].getArr(), t.getPoints()[1].getArr(), t.getPoints()[2].getArr()};
	}
	public String toString() {
		String output = "";
		for (double[] x:matrix) {
			output += "[ ";
			for(double y:x){
				output += y + " ";
			}
			output += "]\n";
		}
		return (output);
	}
	
	public void printMatrix() {
		for (double[] x:matrix) {
			System.out.print("[ ");
			for(double y:x){
				System.out.print(y + " ");
			}
			System.out.print("]\n");
		}
	}
	
	
	public Matrix copy() {
		Matrix out = new Matrix(this);
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				out.matrix[i][j] = matrix[i][j];
			}
		}
		return out;
	}
	public void addBias(double b) {
		double[][] augmented = new double[1][matrix[0].length + 1];
		for (int i = 0; i < matrix[0].length;i++) {
			augmented[0][i] = matrix[0][i];
		}
		augmented[0][matrix[0].length] = b;
		matrix = augmented;
	}
	
	public Matrix square() {
		Matrix m = new Matrix(this);
		for (int i = 0; i < m.matrix.length; i++) {
			for (int j = 0; j < m.matrix[0].length; j++) {
				m.matrix[i][j] = m.matrix[i][j] * m.matrix[i][j];
			}
		}
		return m;
	}
	
	public void sigmoid() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = (1.0 / (1 + Math.pow(Math.E,-1 * matrix[i][j])));
			}
		}
	}
	
	public Matrix sub (Matrix b) {
		if (matrix.length == b.matrix.length && matrix[0].length == b.matrix[0].length) {
			Matrix result = new Matrix(matrix.length,matrix[0].length);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					result.matrix[i][j] = matrix[i][j] - b.matrix[i][j];
				}
			}
			return result;
		}else {
			return new Matrix(0,0);
		}
	}
	
	public void add(double b) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] += b;
			}
		}
	}
	
	public void fill (double a){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = a;
			}
		}
	}
	public void singleMult (double a){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] *= a;
			}
		}
	}
	
	public void populate (){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = (double)(new Random().nextInt(201) - 100) / 100;
			}
		}
	}
	
	public Vector mult (Vector other){
		double[][] b = matrix;
		double[][] a = new double[][]{other.getArr()};
		if (a[0].length == b.length) {
			int outA = a.length; //num rows in A
			int outB = b[0].length; //num columns in B
			int commonLength = a[0].length;
			Matrix product = new Matrix(outA,outB);
			for (int i = 0; i < a.length;i++) { //for each row in matrix a
				for (int j = 0; j < b[0].length;j++) { //for each column in matrix b
					for (int k = 0; k < commonLength; k++) { //going down row i in matrix a and column j in matrix b
						product.matrix[i][j] += a[i][k] * b[k][j];
					}
				}
			}
			return new Vector(product.matrix[0][0], product.matrix[0][1],product.matrix[0][2]);
		}
		else {
			System.out.println("Attempted multiplication without matching rows/columns");
			return new Vector(0,0,0);
		}
	}
	
	public Matrix mult (Matrix other){
		double[][] b = other.matrix;
		double[][] a = matrix;
		if (a[0].length == b.length) {
			int outA = a.length; //num rows in A
			int outB = b[0].length; //num columns in B
			int commonLength = a[0].length;
			Matrix product = new Matrix(outA,outB);
			for (int i = 0; i < outA;i++) { //for each row in matrix a
				for (int j = 0; j < outB;j++) { //for each column in matrix b
					for (int k = 0; k < commonLength; k++) { //going down row i in matrix a and column j in matrix b
						product.matrix[i][j] += a[i][k] * b[k][j];
					}
				}
			}
			return product;
		}
		else {
			System.out.println("Attempted multiplication without matching rows/columns");
			return new Matrix(0,0);
		}
	}
	
	public void rowSub(int tRow,int row,double scalar) {
		for(int i = 0; i < 3; i++) {
			matrix[tRow][i] -= matrix[row][i] * scalar;
		}
	}
	public void rowDiv(int row, double scalar) {
		for(int i = 0; i < 3; i++) {
			matrix[row][i] /= scalar;
		}
	}
	
	public Matrix getInverse() {
		
		Matrix solution = ID.copy();
		matrix = copy().matrix;
		double a = matrix[0][1];
		double b = matrix[0][2];
		double c = matrix[1][1];
		double d = matrix[1][2];
		double e = matrix[2][1];
		double f = matrix[2][2];
		double x = (b * e - a * f) / (d * e - c * f);
		double y = (b * c - a * d) / (f * c - e * d);
		rowSub(0,1,x);
		solution.rowSub(0, 1, x);
		rowSub(0,2,y);
		solution.rowSub(0, 2, y);
		double div = matrix[0][0];
		rowDiv(0,div);
		solution.rowDiv(0,div);
		
		c = matrix[0][0];
		d = matrix[0][2];
		a = matrix[1][0];
		b = matrix[1][2];
		e = matrix[2][0];
		f = matrix[2][2];
		x = (b * e - a * f) / (d * e - c * f);
		y = (b  *c - a * d) / (f * c - e * d);
		rowSub(1,0,x);
		solution.rowSub(1, 0, x);
		rowSub(1,2,y);
		solution.rowSub(1, 2, y);
		div = matrix[1][1];
		rowDiv(1,div);
		solution.rowDiv(1,div);
		
		c = matrix[0][0];
		d = matrix[0][1];
		e = matrix[1][0];
		f = matrix[1][1];
		a = matrix[2][0];
		b = matrix[2][1];
		x = (b * e - a * f) / (d * e - c * f);
		y = (b  *c - a * d) / (f * c - e * d);
		rowSub(2,0,x);
		solution.rowSub(2, 0, x);
		rowSub(2,1,y);
		solution.rowSub(2, 1, y);
		div = matrix[2][2];
		rowDiv(2,div);
		solution.rowDiv(2,div);
		return solution;

	}
	
	/*
	 * [vertical down][horizontal right]
	 * [-y][x]
	 * [row][column]
	 * Reference 3x3 matrix
	 *        #0     #1    #2
	 * #0 [ [0][0],[0][1],[0][2] ]
	 * #1 [ [1][0],[1][1],[1][2] ]
	 * #2 [ [2][0],[2][1],[2][2] ]
	 */
	
}


