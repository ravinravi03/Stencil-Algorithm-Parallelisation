public class Sequential {

    /**
     * The following method is the sequential stencil algorithm
     * @param numOfIterations then number of iterations the algorithm does
     * @param grid the integer array that the algorithm is applied on
     */
    public static int[][] stencilSequentialAlgorithm_VonNeumann(int numOfIterations, int[][] grid, int[][] gridCopy){

        for(int k = 0;k<numOfIterations;k++){
            //ArrayUtil.printGrid(grid);
            for(int i=1;i<(grid.length-1);i++){
                for(int j=1;j<(grid.length-1);j++){
                    int stencilTotal = grid[i-1][j]+grid[i+1][j]+grid[i][j-1]+grid[i][j+1];
                    gridCopy[i][j] = stencilTotal/4;
                }
            }
            if(k<(numOfIterations-1)){
                int[][] temp = gridCopy;
                gridCopy = grid;
                grid = temp;
            }
        }

        return gridCopy;

    }


    /**
     * The same sequential algorithm that uses von neumann neighbourhood but works on a vectorised representation of the 2d input matrix
     */
    public static int[][] stencilSequentialAlgorithm_VonNeumann_1D(int numOfIterations, int[][] grid, int[][] gridCopy) {
        int[] gridVector = ArrayUtil.convertTo1D(grid);
        int[] gridCopyVector = ArrayUtil.convertTo1D(gridCopy);

        int size = (int) Math.sqrt(gridVector.length);
        for (int k = 0; k < numOfIterations; k++) {
            for (int i = 1; i < size - 1; i++) {
                for (int j = 1; j < size - 1; j++) {
                    int index = i * size + j;
                    int stencilTotal = gridVector[(i - 1) * size + j] + gridVector[(i + 1) * size + j] +
                            gridVector[i * size + (j - 1)] + gridVector[i * size + (j + 1)];
                    gridCopyVector[index] = stencilTotal / 4;
                }
            }

            if(k<(numOfIterations-1)){
                int[] temp = gridCopyVector;
                gridCopyVector = gridVector;
                gridVector = temp;
            }

        }

        gridCopy = ArrayUtil.convertTo2D(gridCopyVector);

        return gridCopy;
    }

    /**
     * The following method is the sequential stencil algorithm
     * @param numOfIterations then number of iterations the algorithm does
     * @param grid the integer array that the algorithm is applied on
     */
    public static int[][] stencilSequentialAlgorithm_Moore(int numOfIterations, int[][] grid, int[][] gridCopy){

        for(int k = 0;k<numOfIterations;k++){
            //ArrayUtil.printGrid(grid);
            for(int i=1;i<(grid.length-1);i++){
                for(int j=1;j<(grid.length-1);j++){
                    int stencilTotal = grid[i-1][j-1] + grid[i-1][j] + grid[i-1][j+1] + grid[i][j-1] + grid[i][j+1] + grid[i+1][j-1] + grid[i+1][j] + grid[i+1][j+1];
                    gridCopy[i][j] = stencilTotal / 8;
                }
            }
            if(k<(numOfIterations-1)){
                int[][] temp = gridCopy;
                gridCopy = grid;
                grid = temp;
            }
        }

        return gridCopy;

    }

    /**
     * The sequential jacobi algorithm for 3D matrices
     * @param numOfIterations number of iterations we would like the jacobi stencil to take
     * @param grid the 3D input array
     * @param gridCopy the output array after the stencil has been applied
     * @return
     */
    public static int[][][] stencil3DSequentialAlgorithm_VonNeumann(int numOfIterations, int[][][] grid, int[][][] gridCopy){

        for(int a = 0;a<numOfIterations;a++){
            //ArrayUtil.print3DGrid(grid);
            for(int i=1;i<(grid.length-1);i++){
                for(int j=1;j<(grid.length-1);j++){
                    for(int k=1;k<(grid.length-1);k++){
                        int stencilTotal = grid[i-1][j][k]+grid[i+1][j][k]+grid[i][j-1][k]+grid[i][j+1][k]+grid[i][j][k+1]+grid[i][j][k-1];
                        gridCopy[i][j][k] = stencilTotal/6;
                    }

                }
            }
            if(a<(numOfIterations-1)){
                int[][][] temp = gridCopy;
                gridCopy = grid;
                grid = temp;
            }
        }

        return gridCopy;
    }

    /**
     * The sequential jacobi algorithm for 3D matrices
     * @param numOfIterations number of iterations we would like the jacobi stencil to take
     * @param grid the 3D input array
     * @param gridCopy the output array after the stencil has been applied
     * @return
     */
    public static int[][][] stencil3DSequentialAlgorithm_Moore(int numOfIterations, int[][][] grid, int[][][] gridCopy){

        for(int a = 0;a<numOfIterations;a++){
            //ArrayUtil.print3DGrid(grid);
            for(int i=1;i<(grid.length-1);i++){
                for(int j=1;j<(grid.length-1);j++){
                    for(int k=1;k<(grid.length-1);k++){

                        int stencilTotal = 0;
                        for(int x = -1;x <= 1;x++){
                            for(int y = -1; y <= 1;y++){
                                stencilTotal += grid[i+x][j+y][k-1] + grid[i+x][j+y][k+1];
                            }
                        }

                        stencilTotal += grid[i-1][j-1][k] +  grid[i-1][j][k] + grid[i-1][j+1][k] + grid[i][j-1][k] + grid[i][j+1][k] +  grid[i+1][j-1][k] + grid[i+1][j][k] + grid[i+1][j+1][k];
                        gridCopy[i][j][k] = stencilTotal/26;
                    }

                }
            }
            if(a<(numOfIterations-1)){
                int[][][] temp = gridCopy;
                gridCopy = grid;
                grid = temp;
            }
        }

        return gridCopy;
    }

}
