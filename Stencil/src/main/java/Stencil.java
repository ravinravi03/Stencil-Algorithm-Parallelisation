public class Stencil{

    public static void main(String[] args){


        //Making random 2D matrices
        int[][] grid = ArrayUtil.generateRandomGrid(1000,100);
        int[][] gridCopy = ArrayUtil.copyExistingGrid(grid);

        //Making random 3D matrices
        int[][][] threeDimensionalArray = ArrayUtil.generateRandom3DGrid(100, 100);
        int[][][] threeDimensionalArrayCopy = ArrayUtil.copyExisting3DGrid(threeDimensionalArray);

        long startTime = System.currentTimeMillis();
        //Sequential.stencilSequentialAlgorithm_Moore(100, grid, gridCopy);
        Sequential.stencilSequentialAlgorithm_VonNeumann(100, grid, gridCopy);

        //Sequential.stencil3DSequentialAlgorithm_Moore(100, threeDimensionalArray, threeDimensionalArrayCopy);
        //Sequential.stencil3DSequentialAlgorithm_VonNeumann(100, threeDimensionalArray, threeDimensionalArrayCopy);

        long endTime = System.currentTimeMillis();

        long duration = endTime-startTime;

        System.out.println("The sequential algorithm took " +duration+" milliseconds");

        startTime = System.currentTimeMillis();

        //Set the useMoore boolean to true or false for the parallel implementation to use either the moore or von neumann neighbourhood

        Parallel.stencilParallelisedAlgorithm(100, grid,gridCopy, 10, false);
        //Parallel.stencil3DParallelisedAlgorithm(100, threeDimensionalArray, threeDimensionalArrayCopy, 12, true);

        endTime = System.currentTimeMillis();

        duration = endTime-startTime;

        System.out.println("The parallel algorithm took " +duration+" milliseconds");

    }

}