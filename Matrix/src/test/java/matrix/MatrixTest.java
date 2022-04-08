package matrix;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This class tests the Matrix API.
 * @author tcolburn
 */
public class MatrixTest {
    
     public MatrixTest() {
        m3x2 = Matrix.create(3, 2);
        m2x3 = Matrix.create(2, 3);
        m3x3 = Matrix.create(3, 3);
    }

    @Test
    public void testDimensions() {
        assertTrue(m3x2.getNumRows() == 3);
        assertTrue(m3x2.getNumColumns() == 2);
    }

    @Test
    public void testBounds() {
        // these checks should succeed
        for (int r = 0; r < m3x2.getNumRows(); r++) {
            for (int c = 0; c < m3x2.getNumColumns(); c++) {
                m3x2.checkBounds(r, c);
            }
        }
        // Test for exceptions
        tryBadIndex(-1, 2); // bad row
        tryBadIndex(2, -1); // bad column
        tryBadIndex(3, 1);  // bad row
        tryBadIndex(2, 2);  // bad column
    }
    
    private void tryBadIndex(int row, int col) { // row or column should be
        try {                                    // out of bounds
            m3x2.checkBounds(row, col);
            fail("get should not have succeeded");
        }
        catch(MatrixException ex) { }
    }
    
    @Test
    public void testGet() {  // m should have all zeroes
        for (int r = 0; r <  m3x2.getNumRows(); r++) 
            for (int c = 0; c < m3x2.getNumColumns(); c++) 
                assertTrue(m3x2.get(r, c) == 0);
                System.out.println(m3x2);
    }
    
    @Test
    public void testSetAndGet() {
        m3x2.set(0, 0, 1);   m3x2.set(0, 1, 4);  // 1 4
        m3x2.set(1, 0, 2);   m3x2.set(1, 1, 5);  // 2 5
        m3x2.set(2, 0, 3);   m3x2.set(2, 1, 6);  // 3 6
        assertTrue(m3x2.get(0, 0) == 1);  assertTrue(m3x2.get(0, 1) == 4);
        assertTrue(m3x2.get(1, 0) == 2);  assertTrue(m3x2.get(1, 1) == 5);
        assertTrue(m3x2.get(2, 0) == 3);  assertTrue(m3x2.get(2, 1) == 6);
    }
    
    @Test
    public void testClear() {
        m3x2.set(0, 0, 1);   m3x2.set(0, 1, 4);  // 1 4
        m3x2.set(1, 0, 2);   m3x2.set(1, 1, 5);  // 2 5
        m3x2.set(2, 0, 3);   m3x2.set(2, 1, 6);  // 3 6
        
        m3x2.clear();
        
        assertTrue(m3x2.get(0, 0) == 0);  assertTrue(m3x2.get(0, 1) == 0);
        assertTrue(m3x2.get(1, 0) == 0);  assertTrue(m3x2.get(1, 1) == 0);
        assertTrue(m3x2.get(2, 0) == 0);  assertTrue(m3x2.get(2, 1) == 0);
    }
    
    @Test
    public void testFillRowWise() {
        m3x2.fillRowWise();           // 1 2
                                      // 3 4
                                      // 5 6
        assertTrue(m3x2.get(0, 0) == 1);  assertTrue(m3x2.get(0, 1) == 2);
        assertTrue(m3x2.get(1, 0) == 3);  assertTrue(m3x2.get(1, 1) == 4);
        assertTrue(m3x2.get(2, 0) == 5);  assertTrue(m3x2.get(2, 1) == 6);
    }
    
    @Test
    public void testFillColumnWise() {
        m3x2.fillColumnWise();        // 1 4
                                      // 2 5
                                      // 3 6
        assertTrue(m3x2.get(0, 0) == 1);  assertTrue(m3x2.get(0, 1) == 4);
        assertTrue(m3x2.get(1, 0) == 2);  assertTrue(m3x2.get(1, 1) == 5);
        assertTrue(m3x2.get(2, 0) == 3);  assertTrue(m3x2.get(2, 1) == 6);
    }
    
    @Test
    public void testTranspose() {
        m3x2.fillColumnWise();          // 1 4
                                        // 2 5
                                        // 3 6
                                        
        m2x3 = m3x2.transpose();        // 1 2 3
                                        // 4 5 6
        assertTrue(m2x3.get(0, 0) == 1 && m2x3.get(0, 1) == 2 && m2x3.get(0, 2) == 3);
        assertTrue(m2x3.get(1, 0) == 4 && m2x3.get(1, 1) == 5 && m2x3.get(1, 2) == 6);
    }
    
    @Test
    public void testMakeIdentity() {
        try {                                    
            m3x2.makeIdentity();
            fail("get should not have succeeded");
        }
        catch(MatrixException ex) { }
        
        m3x3.makeIdentity();    // 1 0 0
                                // 0 1 0
                                // 0 0 1
        assertTrue(m3x3.get(0, 0) == 1 && m3x3.get(0, 1) == 0 && m3x3.get(0, 2) == 0);
        assertTrue(m3x3.get(1, 0) == 0 && m3x3.get(1, 1) == 1 && m3x3.get(1, 2) == 0);
        assertTrue(m3x3.get(2, 0) == 0 && m3x3.get(2, 1) == 0 && m3x3.get(2, 2) == 1);
    }
    
    /**
     * This test should test the equality of:
     *   1. A matrix with itself (should be true)
     *   2. Several pairs of matrices of differing dimensions 
     *      (should be false)
     *   3. Two empty matrices of the same dimensions (should be true)
     *   4. Two nonempty matrices of the same dimensions with the same 
     *      values for elements (should be true)
     *   5. Two nonempty matrices of the same dimensions with the same 
     *      values except for one element (should be false)
     *   6. A nonempty matrix with the transpose of the transpose of
     *      itself (should be true)
     */
    @Test
    public void testEquals() {
        //1.Matrix with itself.
        m3x3.fillRowWise();
        assertTrue(m3x3.equals(m3x3)==true); //1 2 3
                                             //4 5 6
                                             //7 8 9
        //2.Several pairs of matrices of differing dimensions
        m3x3.clear();
        m3x2.fillRowWise(); //1 2
                            //3 4
                            //5 6
        m3x3.fillColumnWise(); //1 4 7
                               //2 5 8
                               //3 6 9                    
        m2x3.fillRowWise();         //1 2 3
                                    //4 5 6
        assertTrue(m3x2.equals(m3x3)==false);
        assertTrue(m2x3.equals(m3x3)==false);
        assertTrue(m2x3.equals(m3x3)==false);
        assertTrue(m2x3.equals(m3x2)==false);
        
        //3.Two empty matrices of same dimension should be true
        m3x3.clear();
        m2x3.clear();
        m3x2.clear();
        assertTrue(m3x3.equals(m3x3)==true);
        
        //4.Two nonempty matrices of the same dimensions with the same 
        //  values for elements (should be true)
        m3x3.fillColumnWise();
        assertTrue(m3x3.equals(m3x3)==true);
        
        //5.Two nonempty matrices of the same dimensions with the same 
        //   values except for one element (should be false)
        
        m2x3.clear();
        m2x3.fillColumnWise();
        Matrix eqTestMatrix = Matrix.create(2, 3);
        
        eqTestMatrix.set(0, 0, 1);
        eqTestMatrix.set(1, 0, 2);
        eqTestMatrix.set(0, 1, 3);
        eqTestMatrix.set(1, 1, 4);
        eqTestMatrix.set(0, 2, 5);
        eqTestMatrix.set(1, 2, 7);      // 1 3 5
                                        // 2 4 7
                            
        
        assertTrue(eqTestMatrix.equals(m2x3)==false);
        assertTrue(eqTestMatrix.get(0, 0) == 1);  assertTrue(eqTestMatrix.get(1, 1) == 4);
        assertTrue(eqTestMatrix.get(1, 0) == 2);  assertTrue(eqTestMatrix.get(0, 2) == 5);
        assertTrue(eqTestMatrix.get(0, 1) == 3);  assertTrue(eqTestMatrix.get(1, 2) == 7);
        //6. A nonempty matrix with the transpose of the transpose of
        //     itself (should be true)
        m3x3.clear();
        Matrix madeEqualT = Matrix.create(3, 3);
        madeEqualT.clear();
        madeEqualT.fillRowWise();
                               //1 2 3
                               //4 5 6
                               //7 8 9        
        assertTrue(madeEqualT.transpose().transpose().equals(madeEqualT)==true);   
    }
    
    /**
     * This test should:
     *   1. Try to add two matrices of different dimensions and catch the
     *      thrown exception
     *   2. Add two empty matrices of the same dimensions and confirm
     *      the result with assertions
     *   3. Add two nonempty matrices of the same dimensions and confirm
     *      the result with assertions
     */
    @Test
    public void testAdd() {
       // 1.Try to add two matrices of different dimensions and catch the
       // thrown exception
       try{
         m2x3.clear();
         m2x3.fillColumnWise();
         Matrix testAdd1 = Matrix.create(3,3);
         testAdd1.fillRowWise();
         
         m2x3.add(testAdd1);         
       }
       catch(MatrixException ex ){
           fail("get should not have succeeded");
       }
       //2.Add two empty matrix
       m3x3.clear();
       Matrix testAdd2 = Matrix.create(3, 3);
       Matrix testAdd2x = Matrix.create(3, 3);
       testAdd2x.clear();
       testAdd2 = testAdd2x.add(m3x3);
       assertTrue(testAdd2.get(0, 0) == 0 && testAdd2.get(0, 1) == 0 && testAdd2.get(0, 2) == 0);
       assertTrue(testAdd2.get(1, 0) == 0 && testAdd2.get(1, 1) == 0 && testAdd2.get(1, 2) == 0);
       assertTrue(testAdd2.get(2, 0) == 0 && testAdd2.get(2, 1) == 0 && testAdd2.get(2, 2) == 0); 
       //3. Add two nonempty matrices of the same dimensions and confirm
       //   the result with assertions
       m3x3.clear();
       Matrix testAdd3 = Matrix.create(3, 3);
       testAdd3.clear();
       m3x3.fillRowWise();
       testAdd3.set(0, 0, 1);
       testAdd3.set(0, 1, 2);
       testAdd3.set(0, 2, 3);
                testAdd3.set(1, 0, 8);
                testAdd3.set(1, 1, 9);
                testAdd3.set(1, 2, 10);
                            testAdd3.set(2, 0, 7);
                            testAdd3.set(2, 1, 5);
                            testAdd3.set(2, 2, 4);
        Matrix ans3 = Matrix.create(3, 3);
        ans3 = m3x3.add(testAdd3); // 2  4   6
                                   // 12 14 16
                                   // 14 13 13
    assertTrue(ans3.get(0, 0) == 2 && ans3.get(0, 1) == 4 && ans3.get(0, 2) == 6);
    assertTrue(ans3.get(1, 0) == 12 && ans3.get(1, 1) == 14 && ans3.get(1, 2) == 16);
    assertTrue(ans3.get(2, 0) == 14 && ans3.get(2, 1) == 13 && ans3.get(2, 2) == 13);                           
    }
    
    /**
     * This test should:
     *   1. Try to multiply several pairs of incompatible matrices and catch the
     *      thrown exceptions
     *   2. Multiply two nonempty compatible matrices and confirm
     *      the result with assertions
     *   3. Multiply a nonempty square matrix by the identity matrix of the same
     *      dimensions and confirm that the result is the original matrix
     */
    @Test
    public void testMultiply() {
        //1. Try to multiply several pairs of incompatible matrices and catch the
        //     thrown exceptions
        try{
           m3x2.fillRowWise();
           m3x3.fillRowWise();
           m2x3.fillColumnWise();
           
           m2x3.multiply(m2x3);
           m3x3.multiply(m2x3);         
        }
        catch(MatrixException ex ){
                        fail("get should not have succeeded");   
        }
        //2. Multiply two nonempty compatible matrices and confirm
        //   the result with assertions
        m3x2.fillRowWise();   // 1 2
                              // 3 4
                              // 5  6
        m2x3.clear();
        m2x3.fillRowWise();   // 1 2 3
                              // 4 5 6 
        Matrix multMat2 = Matrix.create(3, 3); //9 12 15  
                                               //19 26 33
                                               //29 40 51
                                               
        multMat2.clear();
        try{
            multMat2 = m3x2.multiply(m2x3);
               for (int r = 0; r < multMat2.getNumRows(); r++) {
            for (int c = 0; c < multMat2.getNumColumns(); c++) {
                multMat2.checkBounds(r, c);
            }
        }
        assertTrue(multMat2.get(0, 0) == 9 && multMat2.get(0, 1) == 12 && multMat2.get(0, 2) == 15) ;
        assertTrue(multMat2.get(1, 0) == 19 && multMat2.get(1, 1) == 26 && multMat2.get(1, 2) == 33) ;
        assertTrue(multMat2.get(2, 0) == 29 && multMat2.get(2, 1) == 40 && multMat2.get(2, 2) == 51) ;
             fail("get should not have succeeded");       
        }
        catch(MatrixException ex ){}

    //3. Multiply a nonempty square matrix by the identity matrix of the same
    // dimensions and confirm that the result is the original matrix
        
        m3x3.fillRowWise();
        Matrix identity = Matrix.create(3, 3);
        identity.makeIdentity();
        
        Matrix resultMult = Matrix.create(3, 3);
        try{
                    resultMult = m3x3.multiply(identity);    
        assertTrue(resultMult.equals(m3x3));
        
        }
        catch(MatrixException ex ){
        fail("get should not have succeeded");
        }
    }
    
    private final Matrix m3x2;
    private Matrix m2x3;
    private Matrix m3x3;
}