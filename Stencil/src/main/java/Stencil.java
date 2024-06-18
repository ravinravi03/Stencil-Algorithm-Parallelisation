import java.util.Scanner;

public class Stencil{

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Hi there!, Welcome to my application. This application is used to test different methods in parallelising iterative stencil loops\n");

        boolean tryAgain = false;

        do {
            System.out.println("\n Please select a framework to test ISLs on: ");
            System.out.println("> 1 - Java Executor Service");
            System.out.println("> 2 - GPU Vectorisation using Aparapi (Requires GPU)");
            System.out.println("Enter your choice (1 or 2): ");

            int choice;

            do{
              choice = Integer.parseInt(scanner.nextLine().trim());

              switch (choice){
                    case 1:
                        tryAgain = handleJavaExecutorService(scanner,tryAgain);
                        break;
                    case 2:
                        tryAgain = handleGPUVectorisation(scanner,tryAgain);
                        break;
                    default:
                        System.out.println("Please enter a valid choice, either 1 or 2: ");

                }

            }while(choice!=1&&choice!=2);

        }while (tryAgain);

    }

    public static void displaySpeedUp(long sequentialTime, long parallelTime){
        double speedUp = ((double) sequentialTime / parallelTime)*100;
        System.out.printf("Speedup achieved: %.2f%% %n ", speedUp);
    }
    
    public static boolean handleJavaExecutorService(Scanner scanner, boolean tryAgain){
        System.out.print("Are we testing 1D, 2D or 3D matrices? (Enter '1D' or '2D' or '3D'): ");
        String dimension;
        do {
            dimension = scanner.nextLine().trim().toUpperCase();

            switch (dimension) {
                case "1D" -> System.out.println("You have selected 1D");
                case "2D" -> System.out.println("You have selected 2D");
                case "3D" -> System.out.println("You have selected 3D");
                default -> System.out.println("Please enter a valid input. (Has to be either '1D' or '2D' or '3D'");
            }
        } while ((!dimension.equals("1D") && !dimension.equals("2D") && !dimension.equals("3D")));

        System.out.print("Enter grid size (e.g., 1000): ");
        int gridSize = scanner.nextInt();

        System.out.print("Enter a number of iterations to apply the iterative stencil loop on (e.g., 100): ");
        int numOfIterations = scanner.nextInt();
        scanner.nextLine();

        int numOfProcessors = Runtime.getRuntime().availableProcessors();
        int blockSize = (gridSize + numOfProcessors - 1) / numOfProcessors;

        System.out.print("Enter the value of the block size (press Enter to use the default value of " + blockSize + "): ");

        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            blockSize = Integer.parseInt(input);
        }

        System.out.println("Block size: " + blockSize);

        int[][] grid = null;
        int[][] gridCopy = null;
        int[][][] threeDimensionalArray = null;
        int[][][] threeDimensionalArrayCopy = null;

        if (dimension.equals("1D") || dimension.equals("2D")) {
            //Making random 2D matrices
            grid = ArrayUtil.generateRandomGrid(gridSize, 100);
            gridCopy = ArrayUtil.copyExistingGrid(grid);
        }

        if (dimension.equals("3D")) {
            //Making random 3D matrices
            threeDimensionalArray = ArrayUtil.generateRandom3DGrid(gridSize, 100);
            threeDimensionalArrayCopy = ArrayUtil.copyExisting3DGrid(threeDimensionalArray);
        }
        String neighborhoodType;

        if (!dimension.equals("1D")) {
            System.out.print("Choose the neighborhood type: Moore (M) or Von Neumann (V): ");
            do {
                neighborhoodType = scanner.nextLine().trim().toUpperCase();

                switch (neighborhoodType) {
                    case "M" -> System.out.println("You have chosen the Moore neighbourhood");
                    case "V" -> System.out.println("You have chosen the Von Neumann neighbourhood");
                    default ->
                            System.out.println("Please enter a valid input, either 'M' for Moore or 'V' for Von Neumann");
                }
            } while ((!neighborhoodType.equals("M") && !neighborhoodType.equals("V")));
        } else {
            neighborhoodType = "V";
            System.out.println("Currently our 1D implementation only supports the Von Neumann neighbourhood");
        }

        long startTime;
        long endTime;
        long sequentialDuration = 0;
        long parallelDuration = 0;

        switch (dimension) {
            case "1D":
                startTime = System.currentTimeMillis();
                Sequential.stencilSequentialAlgorithm_VonNeumann_1D(numOfIterations, grid, gridCopy);
                endTime = System.currentTimeMillis();
                sequentialDuration = endTime - startTime;

                System.out.println("> The sequential algorithm took " + sequentialDuration + " milliseconds");

                startTime = System.currentTimeMillis();
                Parallel.stencil1DParallelisedAlgorithm(numOfIterations, grid, gridCopy, blockSize);
                endTime = System.currentTimeMillis();
                parallelDuration = endTime - startTime;

                System.out.println("> The parallel algorithm took " + parallelDuration + " milliseconds");

                break;
            case "2D":
                if (neighborhoodType.equals("V")) {
                    startTime = System.currentTimeMillis();
                    Sequential.stencilSequentialAlgorithm_VonNeumann(numOfIterations, grid, gridCopy);
                    endTime = System.currentTimeMillis();
                    sequentialDuration = endTime - startTime;

                    System.out.println("> The sequential algorithm took " + sequentialDuration + " milliseconds");

                    startTime = System.currentTimeMillis();
                    Parallel.stencilParallelisedAlgorithm(numOfIterations, grid, gridCopy, blockSize, false);
                    endTime = System.currentTimeMillis();
                    parallelDuration = endTime - startTime;

                    System.out.println("> The parallel algorithm took " + parallelDuration + " milliseconds");
                } else { // Using the moore neighbourhood
                    startTime = System.currentTimeMillis();
                    Sequential.stencilSequentialAlgorithm_Moore(numOfIterations, grid, gridCopy);
                    endTime = System.currentTimeMillis();
                    sequentialDuration = endTime - startTime;

                    System.out.println("> The sequential algorithm took " + sequentialDuration + " milliseconds");

                    startTime = System.currentTimeMillis();
                    Parallel.stencilParallelisedAlgorithm(numOfIterations, grid, gridCopy, blockSize, true);
                    endTime = System.currentTimeMillis();
                    parallelDuration = endTime - startTime;

                    System.out.println("> The parallel algorithm took " + parallelDuration + " milliseconds");
                }
                break;
            case "3D":
                if (neighborhoodType.equals("V")) {
                    startTime = System.currentTimeMillis();
                    Sequential.stencil3DSequentialAlgorithm_VonNeumann(numOfIterations, threeDimensionalArray, threeDimensionalArrayCopy);
                    endTime = System.currentTimeMillis();
                    sequentialDuration = endTime - startTime;

                    System.out.println("> The sequential algorithm took " + sequentialDuration + " milliseconds");

                    startTime = System.currentTimeMillis();
                    Parallel.stencil3DParallelisedAlgorithm(numOfIterations, threeDimensionalArray, threeDimensionalArrayCopy, blockSize, false);
                    endTime = System.currentTimeMillis();
                    parallelDuration = endTime - startTime;

                    System.out.println("> The parallel algorithm took " + parallelDuration + " milliseconds");
                } else { // Using the moore neighbourhood
                    startTime = System.currentTimeMillis();
                    Sequential.stencil3DSequentialAlgorithm_Moore(numOfIterations, threeDimensionalArray, threeDimensionalArrayCopy);
                    endTime = System.currentTimeMillis();
                    sequentialDuration = endTime - startTime;

                    System.out.println("> The sequential algorithm took " + sequentialDuration + " milliseconds");

                    startTime = System.currentTimeMillis();
                    Parallel.stencil3DParallelisedAlgorithm(numOfIterations, threeDimensionalArray, threeDimensionalArrayCopy, blockSize, true);
                    endTime = System.currentTimeMillis();
                    parallelDuration = endTime - startTime;

                    System.out.println("> The parallel algorithm took " + parallelDuration + " milliseconds");
                }
                break;
            default:
                System.out.println("This statement should not be accessed");
        }

        displaySpeedUp(sequentialDuration, parallelDuration);

        System.out.println("\n Would you like to try this again? (Y/N): ");
        input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y");
    }

    public static boolean handleGPUVectorisation(Scanner scanner, boolean tryAgain){
        System.out.print("Enter grid size (e.g., 1000): ");
        int gridSize = scanner.nextInt();

        System.out.print("Enter a number of iterations to apply the iterative stencil loop on (e.g., 100): ");
        int numOfIterations = scanner.nextInt();
        scanner.nextLine();

        int numOfProcessors = Runtime.getRuntime().availableProcessors();
        int blockSize = (gridSize + numOfProcessors - 1) / numOfProcessors;

        System.out.print("Enter the value of the block size (press Enter to use the default value of " + blockSize + "): ");

        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            blockSize = Integer.parseInt(input);
        }

        System.out.println("Block size: " + blockSize);

        int[][] grid = ArrayUtil.generateRandomGrid(gridSize, 100);
        int[][] gridCopy =  ArrayUtil.copyExistingGrid(grid);

        int[] gridVector = ArrayUtil.convertTo1D(grid);
        int[] gridCopyVector = ArrayUtil.convertTo1D(gridCopy);

        long startTime;
        long endTime;
        long sequentialDuration = 0;
        long parallelDuration = 0;

        startTime = System.currentTimeMillis();
        Sequential.stencilSequentialAlgorithm_VonNeumann(numOfIterations,grid,gridCopy);
        endTime = System.currentTimeMillis();

        sequentialDuration = endTime-startTime;

        System.out.println("The sequential algorithm took " + sequentialDuration+" milliseconds");

        startTime = System.currentTimeMillis();
        Parallel.stencilOpenCLAlgorithm(numOfIterations,gridVector,gridCopyVector);
        endTime = System.currentTimeMillis();

        parallelDuration = endTime-startTime;

        System.out.println("The algorithm using GPU took " + parallelDuration+" milliseconds");

        displaySpeedUp(sequentialDuration, parallelDuration);

        System.out.println("\n Would you like to try this again? (Y/N): ");
        input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y");

    }

}