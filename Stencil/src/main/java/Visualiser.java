import javax.swing.*;
import java.awt.*;

//WHEN YOU USE VISUALISER.VISUALISE(grid,maxValue) IT WILL PRINT OUT THE GRID AFTER THE ALGORITHM HAS WORKED ON IT. WILL HAVE TO FIND A WAY TO PRINT THE ORIGINAL GRID IN Stencil.java
public class Visualiser extends JPanel {
    private int[][] grid; //The grid that is going to be visualised
    private int maxValue; //The max value in the grid, is also passed on to generateRandomGrid as the max value for the random number generator

    public Visualiser(int[][] grid, int maxValue) {
        this.grid = grid;
        this.maxValue = maxValue;
    }

    /**
     * Calculates the greyscale intensity value from 0 to 255 based on the ratio of the value to the max possible value of the matrix
     */
    private int calculateIntensity(int value){
        double ratio = (double) value/maxValue;
        int intensity = (int)Math.ceil(ratio*255); //Round up to the nearest integer (from 0 to 255)
        return intensity;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //the size of a cell will be either 1 or 1000/grid.length, only works for square matricies, should be improved for matricies of different side lengths
        int cellSize = Math.max(1, 1000 / grid.length);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int colorIntensity = calculateIntensity(grid[i][j]);
                g.setColor(new Color(colorIntensity, colorIntensity, colorIntensity));//set the greyscale value
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void visualise(int[][] grid, int maxValue) {
        JFrame frame = new JFrame();
        frame.setSize(1000, 1000); //Set the dimension of the window to be 1000*1000 pixels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Visualiser(grid, maxValue));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
