import com.aparapi.Kernel;
import com.aparapi.Range;

public class JacobiKernel extends Kernel {
    // Define input and output arrays or variables
    private int[] input;
    private int[] output;
    private int size;
    private int iterations;

    public JacobiKernel(int[] input, int[] output, int iterations) {
        this.input = input;
        this.output = output;
        this.size = (int) Math.sqrt(input.length);
        this.iterations = iterations;
    }

    @Override
    public void run() {
        int globalId = getGlobalId();
        int x = globalId % size;
        int y = globalId / size;

        int[] localInput = input;
        int[] localOutput = output;

        for (int n = 0; n < iterations; n++) {

            if (x > 0 && x < size - 1 && y > 0 && y < size - 1) {
                int stencilTotal = localInput[(y - 1) * size + x] + localInput[(y + 1) * size + x] +
                        localInput[y * size + (x - 1)] + localInput[y * size + (x + 1)];
                localOutput[y * size + x] = stencilTotal / 4;

            }

            localBarrier();

            if (n < iterations - 1) {
                int[] temp = localInput;
                localInput = localOutput;
                localOutput = temp;
            }

            localBarrier();
        }

        for (int i = 0; i < size * size; i++) {
            output[i] = localOutput[i];
        }
    }

    public int[] getOutput() {
        return output;
    }

}