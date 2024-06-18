import java.util.concurrent.Callable;

public class Stencil3DVonNeumannTask implements Callable<Boolean>{

    private int[][][] inputArray;
    private int[][][] outputArray;
    private int xIndex;
    private int yIndex;
    private int blockSize;

    public Stencil3DVonNeumannTask(int[][][] inputArray,int[][][] outputArray, int xIndex, int yIndex, int blockSize){
        this.inputArray = inputArray;
        this.outputArray = outputArray;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.blockSize = blockSize;
    }

    @Override
    public Boolean call(){
        for(int x = xIndex;(x<(xIndex+blockSize))&&(x<(inputArray.length-1));x++){
            for(int y = yIndex;(y<(yIndex+blockSize))&&(y<(inputArray.length-1));y++){
                for(int z=1;z<(inputArray.length-1);z++){
                    int stencilTotal = inputArray[x-1][y][z]+inputArray[x+1][y][z]+inputArray[x][y-1][z]+inputArray[x][y+1][z]+inputArray[x][y][z+1]+inputArray[x][y][z-1];
                    outputArray[x][y][z] = stencilTotal/6;
                }
            }

        }
        return true;
    }
}