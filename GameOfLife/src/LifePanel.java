/**
 * Panel for Conway's Game of Life Simulation, Draws the grid.
 * 
 * @author Eric Kim
 * @version 1.0
 * 
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LifePanel extends JPanel{
    
    //This array of cells in LifePanel points to null
    boolean[][] cells;
    double width;
    double height;
    
    public LifePanel(boolean[][] in) {
        //Points to cells array in Life
        cells = in;
    }
    
    
    public void paintComponent(Graphics g) {
        //Use Jpanel's paint component and overrides it, clears screen
        super.paintComponent(g);
        width = (double)this.getWidth() / cells[0].length;
        height = (double)this.getHeight() / cells.length;
        
        //Draws the cells based on if cell is alive or dead, repaint method
        g.setColor(Color.BLACK);
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                if (cells[row][column] == true) {
                    g.fillRect((int)Math.round(column*width), 
                               (int)Math.round(row*height), 
                               (int)width + 1, 
                               (int)height + 1);
                }
            }
        }
        g.setColor(Color.BLACK);
        //Draw Grid based on adjustable Jpanel width columns x
        for(int x = 0; x < cells[0].length + 1; x++) {
            g.drawLine((int)Math.round(x*width), 0, (int)Math.round(x*width), this.getHeight());
            
        }
        //Draw Grid based on adjustable Jpanel width columns y
        for(int y = 0; y < cells[0].length + 1; y++) {
            g.drawLine(0, (int)Math.round(y*height), this.getWidth(), (int)Math.round(y*height));
            
        }           
    }
    
    // Set cells to new position on Panel
    public void setCells(boolean[][] nextCells) {
        cells = nextCells;
    }
    
    
}
