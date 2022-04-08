package matrix;

/**
 * This abstract class partially implements the Matrix API.
 * Only the number of rows and columns are stored. A subclass
 * should create the data structure used to store matrix elements.
 *
 * @author tcolburn
 */
public abstract class AbstractMatrix implements Matrix {

    /**
     * Returns the number of rows in this matrix.
     *
     * @return the number of rows in this matrix.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in this matrix.
     *
     * @return the number of columns in this matrix.
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Sets the number of rows in this matrix.
     * @param numRows the number of rows in this matrix
     * @throws MatrixException if numRows is not positive
     */
    public void setNumRows(int numRows) {
        if (numRows <= 0) {
            throw new MatrixException(String.format("numRows (%s) must be positive", numRows));
        }
        this.numRows = numRows;
    }

    /**
     * Sets the number of columns in this matrix.
     * @param numColumns the number of columns in this matrix
     * @throws MatrixException if numColumns is not positive
     */
    public void setNumColumns(int numColumns) {
        if (numColumns <= 0) {
            throw new MatrixException(String.format("numColumns (%s) must be positive", numColumns));
        }
        this.numColumns = numColumns;
    }

    /**
     * Creates a visual representation of this matrix as a string. This method
     * can be used for debugging. This is a template method; it uses a method
     * (get) that must be implemented by a subclass. This method overrides a
     * method in the Object class.
     *
     * @return the string representation of this matrix.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int r = 0; r < getNumRows(); r++) {
            for (int c = 0; c < getNumColumns(); c++) {
                builder.append(String.format("%6s", get(r, c)));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Tests for equality of this matrix with another. Matrices are equal if
     * they have the same dimensions and all elements are equal by ==. This is a
     * template method; it uses a method (get) that must be implemented by a
     * subclass. This method overrides a method in the Object class, so it must
     * type check and cast its argument to the correct type.
     *
     * @param obj the other matrix to be tested for equality with this matrix
     * @return <b>true</b> if the other matrix is equal to this matrix,
     * <b>false</b> otherwise
     */
    @Override
    public boolean equals(Object obj) {
        int eqRow = this.getNumRows();
        int eqColumn = this.getNumColumns();
        int eqMatrix2Row = ((Matrix)obj).getNumRows();
        int eqMatrix2Column = ((Matrix)obj).getNumColumns();
        
        boolean equalsTo = false;
        
        if((eqRow == eqMatrix2Row) && (eqColumn == eqMatrix2Column)){
            for(int i=0; i<eqRow; i++){
                for(int j = 0; j<eqColumn; j++ ){
                    equalsTo = (this.get(i, j))==(((Matrix)obj).get(i,j));
                }
            }
        }
        
        return equalsTo;
    }

    /**
     * Adds this matrix to another.
     * @param other the other matrix to add
     * @return a new matrix that is the sum of this matrix and other
     * @throws MatrixException if this matrix and the other matrix do not have
     * the same dimensions
     */
    @Override
    public Matrix add(Matrix other) {
       int addRow = this.getNumRows();
       int addColumn = this.getNumColumns();
       
       int row1 = addRow;
       int row2 = other.getNumRows();
       int column1 = addColumn;
       int column2 = other.getNumColumns();
       
       Matrix results = Matrix.create(row1, column2);
       
       if((row1 == row2) && (column1 == column2)) {
           for (int i = 0; i < row1; i++) {
               for (int j = 0; j < column1; j++) {
                   results.set(i, j, (this.get(i, j) + other.get(i, j)));
               }
           }
       }
       return results;
    }

    /**
     * Multiplies this matrix by another.
     * @param other the other matrix to multiply
     * @return a new matrix that is the product of this matrix and other
     * @throws MatrixException if the number of columns in this matrix does not
     * match the number of rows in the other
     */
    @Override
    public Matrix multiply(Matrix other) {
        int multRow = this.getNumRows();
        int multColumn = this.getNumColumns();
        
        int row1 = multRow;
        int row2 = other.getNumRows();
        int column1 = multColumn;
        int column2 = other.getNumColumns();
        
        int matrixCount = 0;
        int result = 0;
        Matrix resultsMult = Matrix.create(row1, column2);
        
        if(column1 == row2) {
            for(int iMat1 = 0; iMat1<this.getNumRows(); iMat1++){//rows Matrix1 iMat1
                for(int jMat2 = 0; jMat2<other.getNumColumns(); jMat2++){//Columns Matrix2 is jMat2
                   for(int jMat1=0; jMat1<this.getNumRows();jMat1++){
                        //Check here in case multiplaction fails
                    result += (this.get(iMat1, jMat1))*(other.get(jMat1, jMat2));
                    matrixCount = result;
                    resultsMult.set(iMat1, jMat2, matrixCount);
                   }
                   matrixCount = 0;
                   result = 0;
                }
            }
        }
            
        return resultsMult;
    }
    
    private int numRows;
    private int numColumns;

}