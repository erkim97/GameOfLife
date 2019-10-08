/**
 * Program to implement Conway's Game of Life
 * 
 * @author Eric Kim
 * @version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Life implements MouseListener, ActionListener, Runnable{
    Life[][] model = new Life[50][50];

    //Hard fixed to 30x30 grid array
    boolean[][] cells = new boolean[50][50];
    
    //Variables and Objects
    JFrame frame = new JFrame("Game Of Life Simulation");
    LifePanel panel = new LifePanel(cells);
    
    //South Container and Buttons
    Container south = new Container();
    JButton step = new JButton("Step");
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    JButton reset = new JButton("Reset");
    
    //Start button not pressed, not running
    boolean running = false;
    
    /*
     * Constructor
     */
    public Life() {
        frame.setSize(500, 500);
        //Put life panel in center and put in buttons
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);
        
        //South Container
        south.setLayout(new GridLayout(1,3));
        south.add(step);
        step.addActionListener(this);
        
        south.add(start);
        start.addActionListener(this);
        
        south.add(stop);
        stop.addActionListener(this);
        
        south.add(reset);
        reset.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Life();
        
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {        
    }

    @Override
    public void mousePressed(MouseEvent e) {       
        System.out.println(e.getX() + "," + e.getY());
        double width = (double)panel.getWidth() / cells[0].length;
        double height = (double)panel.getHeight() / cells.length; 
        int column = Math.min(cells[0].length - 1, (int)(e.getX() / width));
        int row = Math.min(cells.length - 1, (int)(e.getY() / height));
        System.out.println(column + "," + row);
        
        //Check if cell is alive or dead, assign opposite
        cells[row][column] = !cells[row][column];
        //Redraw itself, calls paintComponent
        frame.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        mousePressed(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {   
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(step)) {
            System.out.println("1 Step");
            step();
        }
        if (e.getSource().equals(start)) {            
            System.out.println("Started");
            //Need separate runnable thread or else stuck in infinite loop
            if (running == false) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }
        if (e.getSource().equals(stop)) {
            System.out.println("Stopped");
            running = false;
        }
        
        if (e.getSource().equals(reset)) {
            System.out.println("Reset");

//            frame.dispose();
//            frame.removeAll();
//            frame.revalidate();
//            frame.repaint();
//            frame.setVisible(true);
            new Life();
        }
        
    }
    
    @Override
    public void run() {
        while(running == true) {
            step();
            //Adjust speed
            try {
                Thread.sleep(200);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /*
     *                    ROWS -- COLUMNS |
     * row-1, column-1      row-1, column       row-1, column+1
     * row, column-1        row, column         row,column+1
     * row+1, column-1      row+1, column       row+1, column+1
     */
    
    //Step method with Conway's Game of Life rules
    public void step() {
        boolean[][] nextCells = new boolean[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                int neighborCount = 0;
                
                //top left
                if (row > 0 && column > 0 && cells[row-1][column-1] == true) {
                    neighborCount++;
                }
                //top middle
                if (row > 0 && cells[row-1][column] == true) {
                    neighborCount++;
                }
                //top right
                if (row > 0 && column < cells[0].length-1 && cells[row-1][column+1] == true) {
                    neighborCount++;
                }
                //left
                if (column > 0 && cells[row][column-1] == true) {
                    neighborCount++;
                }
                //right
                if (column < cells[0].length-1 && cells[row][column+1] == true) {
                    neighborCount++;
                }
                //bottom left
                if (row < cells.length-1 && column > 0 && cells[row+1][column-1] == true) {
                    neighborCount++;
                }
                //bottom middle
                if (row < cells.length-1 && cells[row+1][column] == true) {
                    neighborCount++;
                }
                //bottom right
                if (row < cells.length-1 && column < cells[0].length-1 && cells[row+1][column+1] == true) {
                    neighborCount++;
                }
                //Rules of Game of Life
                //For Currently Alive Cell
                if (cells[row][column] == true) {
                    //Cell has 2 or 3 Neighboring cells, keep alive else die
                    if(neighborCount == 2 || neighborCount ==3) {
                        nextCells[row][column] = true;
                    }
                    else {
                        nextCells[row][column] = false;
                    }   
                }
                //For Currently Dead Cell
                else {
                    //Cell has 3 neighbors, can come back to life
                    if (neighborCount == 3) {
                        nextCells[row][column] = true;
                    }
                    else {
                        nextCells[row][column] = false;
                    }
                }
            }
        }
        //Update to next state, repaint
        cells = nextCells;
        panel.setCells(nextCells);
        frame.repaint();
    }
    
}
