import java.util.concurrent.Callable;

public class StencilVonNeumannTask implements Callable<Boolean>{

    private int[][] inputArray;
    private int[][] outputArray;
    private int index;
    private int blockSize;

    public StencilVonNeumannTask(int[][] inputArray,int[][] outputArray, int index, int blockSize){
        this.inputArray = inputArray;
        this.outputArray = outputArray;
        this.index = index;
        this.blockSize = blockSize;
    }

    @Override
    public Boolean call(){
        for(int i = index;(i<(index+blockSize))&&(i<(inputArray.length-1));i++){
            for(int j=1;j<(inputArray.length-1);j++){

                int stencilTotal = inputArray[i-1][j]+inputArray[i+1][j]+inputArray[i][j-1]+inputArray[i][j+1];
                outputArray[i][j] = stencilTotal/4;

            }
        }
        return true;
    }
}