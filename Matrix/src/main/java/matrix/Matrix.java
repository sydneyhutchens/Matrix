package matrix;

/**
 * This interface describes an API for working with two-dimensional
 * matrices of integers.
 * @author tcolburn
 */
public interface Matrix {
    
    /**
     * Creates a new matrix with given dimensions and initializes the elements
     * to zero.
     * This is a static method with a default implementation.
     * 
     * @param rows the number of rows in the matrix
     * @param columns the number of columns in the matrix
     * @throws MatrixException if dimensions are not positive
     * @return the created matrix
     */
    static Matrix create(int rows, int columns) {
         // return new ArrayImplementation(rows, columns);
	  return new ArrayListImplementation(rows, columns);
    }

    /**
     * Returns the number of rows in this matrix.
     * This is an abstract method.
     *
     * @return the number of rows in this matrix.
     */
    int getNumRows();
    
    /**
     * Sets the number of rows in this matrix.
     * @param numRows the number of rows in this matrix
     * @throws MatrixException if numRows is not positive
     */
    void setNumRows(int numRows);
    
    /**
     * Sets the number of columns in this matrix.
     * @param numColumns the number of columns in this matrix
     * @throws MatrixException if numColumns is not positive
     */
    void setNumColumns(int numColumns);

    /**
     * Returns the number of columns in this matrix.
     * This is an abstract method.
     *
     * @return the number of columns in this matrix.
     */
    int getNumColumns();

    /**
     * Gets the element at the indicated row and column in this matrix.
     * This is an abstract method.
     *
     * @param row the row position for the element.
     * @param column the column position for the element.
     * @return the element at the indicated row and column
     * @throws MatrixException if row or column is out of range
     */
    int get(int row, int column);

    /**
     * Sets the element at the indicated row and column in this matrix.
     * This is an abstract method.
     *
     * @param row the row position for the element.
     * @param column the column position for the element.
     * @param element the value to set in the matrix
     * @throws MatrixException if row or column is out of range
     */
    void set(int row, int column, int element);

    /**
     * Adds this matrix to another.
     * This is an abstract method.
     *
     * @param other the other matrix to add
     * @return a new matrix that is the sum of this matrix and other
     * @throws MatrixException if this matrix and the other matrix do not have
     * the same dimensions
     */
    Matrix add(Matrix other);

    /**
     * Multiplies this matrix by another.
     * This is an abstract method.
     *
     * @param other the other matrix to multiply
     * @return a new matrix that is the product of this matrix and other
     * @throws MatrixException if the number of columns in this matrix does not
     * match the number of rows in the other
     */
    Matrix multiply(Matrix other);
    
    /**
     * Checks a row index for this matrix. 
     * This is a default method and also a template method.
     * 
     * @param row the row index to check
     * @throws MatrixException if row is out of range
     */
    default void checkRowBounds(int row) {
        if (row >= getNumRows() || row < 0) {
            throw new MatrixException(String.format("Index (%s) out of bounds", row));
        }
    }
    
    /**
     * Checks a column index for this matrix. 
     * This is a default method and also a template method.
     * 
     * @param column the column index to check
     * @throws MatrixException if row is out of range
     */
    default void checkColumnBounds(int column) {
        if (column >= getNumColumns() || column < 0) {
            throw new MatrixException(String.format("Index (%s) out of bounds", column));
        }
    }
    
    /**
     * Checks row and column indices for this matrix. 
     * This is a default method. 
     * 
     * @param row the row index to check
     * @param column  the column index to check
     */
    default void checkBounds(int row, int column) {
        checkRowBounds(row);
        checkColumnBounds(column);
    }
    
    /**
     * Sets all elements of this matrix to zero.
     * This is a default method; it provides an implementation.
     * This is also a template method; it calls methods whose implementations
     * are provided by subclasses.
     */
    default void clear() {
        for (int r = 0; r < getNumRows(); r++) {
            for (int c = 0; c < getNumColumns(); c++) {
                set(r, c, 0);
            }
        }
    }
    
    /**
     * Creates the transpose of this matrix.
     * This is a default method; it provides an implementation.
     * This is also a template method; it calls methods whose implementations
     * are provided by subclasses.
     * 
     * @return the transpose of this matrix 
     */
    default Matrix transpose() {
        int numRowsT = this.getNumRows();
        int numColumnsT = this.getNumColumns();
        Matrix MatrixT = Matrix.create(numColumnsT, numRowsT);
        for (int r = 0; r < numRowsT; r++) {
            for (int c = 0; c < numColumnsT; c++) {
                MatrixT.set(c, r, this.get(r, c));
            }
        }
        return MatrixT;
    }
    
    /**
     * If possible, turns this matrix into an identity matrix.
     * @throws MatrixException if this matrix does not have equal
     * dimensions
     */
    default void makeIdentity() {
        int numRowsI = this.getNumRows();
        int numColumnsI = this.getNumColumns();
        if (numRowsI == numColumnsI) {
            for (int j = 0; j < numColumnsI; j++) {
                for (int i = 0; i < numRowsI; i++) {
                    if(i == j) this.set(i, j, 1);
                    else this.set(i, j, 0);
                }
            }
        }
        else{
              throw new MatrixException(String.format("Matrix does have equal dimensions", numRowsI,numColumnsI));  
                }
    }
    
    /**
     * Fills this matrix row-wise, starting with 1
     * in top left and incrementing successive elements by 1
     * from left to right.
     */
    default void fillRowWise() {
        int numEnter = 1;
        for (int r = 0; r < this.getNumRows(); r++) {
            for (int c = 0; c < this.getNumColumns(); c++) {
                this.set(r, c, numEnter);
                numEnter++;
            }
        }
    }
    
    /**
     * Fills this matrix column-wise, starting with 1
     * in top left and incrementing successive elements by 1
     * from top to bottom.
     */
    default void fillColumnWise() {
        int numEnter = 1;
        for (int c = 0; c < this.getNumColumns(); c++) {
           for (int r = 0; r < this.getNumRows(); r++) {
               this.set(r, c, numEnter);
               numEnter++;
           } 
        }
        
    }

}