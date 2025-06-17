public class Surface {
    private Vector V;

    private double A;
    private double B;
    private double C;

    private String character;

    private final int SURFACE_WIDTH;

    public Surface(Vector V, String character, int SURFACE_WIDTH) {
        this.V = V;
        this.character = character;
        this.SURFACE_WIDTH = SURFACE_WIDTH;

        this.A = 0.0;
        this.B = 0.0;
        this.C = 0.0;
    }

    public Surface(Vector V, String character, int SURFACE_WIDTH, double A, double B, double C) {
        this.V = V;
        this.character = character;
        this.SURFACE_WIDTH = SURFACE_WIDTH;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    private double[][] getRotationMatrixX() {
        return new double[][] {
            {1, 0, 0},
            {0, Math.cos(A), -Math.sin(A)},
            {0, Math.sin(A), Math.cos(A)}
        };
    }

    private double[][] getRotationMatrixY() {
        return new double[][] {
            {Math.cos(B), 0, Math.sin(B)},
            {0, 1, 0},
            {-Math.sin(B), 0, Math.cos(B)}
        };
    }

    private double[][] getRotationMatrixZ() {
        return new double[][] {
            {Math.cos(C), -Math.sin(C), 0},
            {Math.sin(C), Math.cos(C), 0},
            {0, 0, 1}
        };
    }

    private double[][] multiplyMatrices(double A[][], double B[][]) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int rowsB = B.length;
        int colsB = B[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("The number of columns in first matrix should equal the number of rows in second matrix");
        }
        
        double result[][] = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    public Vector rotateVector(Vector vector) {
        double[][] rotX = getRotationMatrixX();
        double[][] rotY = getRotationMatrixY();
        double[][] rotZ = getRotationMatrixZ();
        
        double[][] combined = multiplyMatrices(multiplyMatrices(rotZ, rotY), rotX);

        double I = combined[0][0] * vector.i + combined[0][1] * vector.j + combined[0][2] * vector.k;
        double J = combined[1][0] * vector.i + combined[1][1] * vector.j + combined[1][2] * vector.k;
        double K = combined[2][0] * vector.i + combined[2][1] * vector.j + combined[2][2] * vector.k;

        return new Vector(I, J, K);
    }

    public void setRotationAngles(double A, double B, double C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }
    
    public double getVectorI() {
        return this.V.i;
    }

    public double getVectorJ() {
        return this.V.j;
    } 

    public double getVectorK() {
        return this.V.k;
    }
    
    public Vector getVector() {
        return this.V;
    }
    
    public String getCharacter() {
        return this.character;
    }
    
    public void setCharacter(String character) {
        this.character = character;
    }

    public int getWidth() {
        return this.SURFACE_WIDTH;
    }
    
    public double getRotationA() {
        return this.A;
    }
    
    public double getRotationB() {
        return this.B;
    }
    
    public double getRotationC() {
        return this.C;
    }
}