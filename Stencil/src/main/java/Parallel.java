import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel {

    public static int[][] stencilParallelisedAlgorithm(int numOfIterations, int[][] grid,int[][] gridCopy, int blockSize, boolean useMoore){
        int[][] outputArray = gridCopy;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for(int n=0;n<numOfIterations;n++){

            List<Callable<Boolean>> tasks = new ArrayList<>();
            if (useMoore) {
                for (int i = 1; i < (grid.length - 1); i += blockSize) {
                    tasks.add(new StencilMooreTask(grid, outputArray, i, blockSize));
                }
            } else {
                for (int i = 1; i < (grid.length - 1); i += blockSize) {
                    tasks.add(new StencilVonNeumannTask(grid, outputArray, i, blockSize));
                }
            }

            try{
                List<Future<Boolean>> future = executor.invokeAll(tasks);
                for(Future<Boolean> currentFuture : future){
                    currentFuture.get();
                }

                //Switching arrays
                if(n<(numOfIterations-1)){
                    int[][] temp = outputArray;
                    outputArray = grid;
                    grid = temp;
                }

            }catch(Exception e){
                System.out.println("Error invoking all tasks"+e);
            }

        }

        executor.shutdown();

        return outputArray;

    }


    public static int[][] stencil1DParallelisedAlgorithm(int numOfIterations, int[][] grid,int[][] gridCopy, int blockSize){
        int[] gridVector = ArrayUtil.convertTo1D(grid);
        int[] gridCopyVector = ArrayUtil.convertTo1D(gridCopy);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for(int n=0;n<numOfIterations;n++){

            List<Callable<Boolean>> tasks = new ArrayList<>();
            for (int i = 1; i < (grid.length - 1); i += blockSize) {
                tasks.add(new Stencil1DVonNeumannTask(gridVector, gridCopyVector, i * grid.length + 1, blockSize*grid.length));
            }

            try{
                List<Future<Boolean>> future = executor.invokeAll(tasks);
                for(Future<Boolean> currentFuture : future){
                    currentFuture.get();
                }

                //Switching arrays
                if(n<(numOfIterations-1)){
                    int[] temp = gridCopyVector;
                    gridCopyVector = gridVector;
                    gridVector = temp;
                }

            }catch(Exception e){
                System.out.println("Error invoking all tasks"+e);
            }

        }

        executor.shutdown();

        int[][] outputArray = ArrayUtil.convertTo2D(gridCopyVector);
        return outputArray;

    }

    public static int[][][] stencil3DParallelisedAlgorithm(int numOfIterations, int[][][] grid,int[][][] gridCopy, int blockSize, boolean useMoore){
        int[][][] outputArray = gridCopy;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for(int n=0;n<numOfIterations;n++){

            List<Callable<Boolean>> tasks = new ArrayList<>();

            if(useMoore){
                for(int i=1;i<(grid.length-1);i+=blockSize){
                    for(int j=1;j<(grid.length-1);j+=blockSize){
                        tasks.add(new Stencil3DMooreTask(grid,outputArray,i,j,blockSize));
                    }
                }
            }else{
                for(int i=1;i<(grid.length-1);i+=blockSize){
                    for(int j=1;j<(grid.length-1);j+=blockSize){
                        tasks.add(new Stencil3DVonNeumannTask(grid,outputArray,i,j,blockSize));
                    }
                }
            }


            try{
                List<Future<Boolean>> future = executor.invokeAll(tasks);
                for(Future<Boolean> currentFuture : future){
                    currentFuture.get();
                }

                //Switching arrays
                if(n<(numOfIterations-1)){
                    int[][][] temp = outputArray;
                    outputArray = grid;
                    grid = temp;
                }

            }catch(Exception e){
                System.out.println("Error invoking all tasks"+e);
            }

        }

        executor.shutdown();

        return outputArray;

    }
}
