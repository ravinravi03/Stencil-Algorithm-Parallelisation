import java.util.concurrent.Callable;

public class Stencil1DVonNeumannTask implements Callable<Boolean>{

    private int[] inputArray;
    private int[] outputArray;
    private int index;
    private int blockSize;
    private int size;

    public Stencil1DVonNeumannTask(int[] inputArray,int[] outputArray, int index, int blockSize){
        this.inputArray = inputArray;
        this.outputArray = outputArray;
        this.index = index;
        this.blockSize = blockSize;
        this.size = (int) Math.sqrt(inputArray.length);
    }

    @Override
    public Boolean call(){
        for (int n = index; n < index + blockSize; n++) {
            int i = n / size;
            int j = n % size;

            if (i > 0 && i < size - 1 && j > 0 && j < size - 1) {
                int stencilTotal = inputArray[(i - 1) * size + j] + inputArray[(i + 1) * size + j] +
                        inputArray[i * size + (j - 1)] + inputArray[i * size + (j + 1)];
                outputArray[i * size + j] = stencilTotal / 4;
            }
        }
        return true;
    }
}