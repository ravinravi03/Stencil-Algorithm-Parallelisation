import java.util.concurrent.Callable;

public class Stencil3DMooreTask implements Callable<Boolean>{

    private int[][][] inputArray;
    private int[][][] outputArray;
    private int xIndex;
    private int yIndex;
    private int blockSize;

    public Stencil3DMooreTask(int[][][] inputArray,int[][][] outputArray, int xIndex, int yIndex, int blockSize){
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
                    int stencilTotal = 0;
                    for(int i = -1;i <= 1;i++){
                        for(int j = -1; j <= 1;j++){
                            stencilTotal += inputArray[x+1][y+j][z-1] + inputArray[x+i][y+j][z+1];
                        }
                    }

                    stencilTotal += inputArray[x-1][y-1][z] +  inputArray[x-1][y][z] + inputArray[x-1][y+1][z] + inputArray[x][y-1][z] + inputArray[x][y+1][z] +  inputArray[x+1][y-1][z] + inputArray[x+1][y][z] + inputArray[x+1][y+1][z];
                    inputArray[x][y][z] = stencilTotal/26;
                }
            }

        }
        return true;
    }
}
