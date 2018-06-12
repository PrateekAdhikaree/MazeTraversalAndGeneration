/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FixedMaze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author ceto
 */
public class MazeTraverse extends JFrame {

    private static final int HOR_WIDTH = 850;
    private static final int VER_HEIGHT = 850;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MazeTraverse obj = new MazeTraverse();
                obj.setSize(HOR_WIDTH, VER_HEIGHT);
                obj.setTitle("Maze Traverse");
                obj.setLayout(new BorderLayout());
                obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                obj.setLocationRelativeTo(null);
                obj.setVisible(true);
            }
        });
    }

    // to handle OS differences for file path
    private String resourcesFolder = "res" + File.separator;
    private String workingDirectory = System.getProperty("user.dir");
    private String absoluteFilePath = workingDirectory + File.separator + resourcesFolder;

    private Image runnerImg;
    private Image targetImg;
    private Image finalImg;
    private Image brickImg;

    private int flag = 0;

    private int runnerXPos = 1;
    private int runnerYPos = 1;

    /*
    * target = 9
    * wall   = 1
    * free   = 0
    */
    private int [][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 9, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private final java.util.List<Integer> path = new ArrayList<Integer>();
    private ArrayList xPath;
    private ArrayList yPath;

    public MazeTraverse(){
        try {
            runnerImg = ImageIO.read(new File(absoluteFilePath + "runner.png"));
            targetImg = ImageIO.read(new File(absoluteFilePath + "target.jpg"));
            brickImg = ImageIO.read(new File(absoluteFilePath + "brick.jpg"));
            finalImg = ImageIO.read(new File(absoluteFilePath + "final.jpg"));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        DepthFirst.searchPath(maze, runnerXPos, runnerYPos, path);
        xPath = new ArrayList();
        yPath = new ArrayList();
    }

    @Override
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        for (int p = 0; p < path.size() ; p += 2){
            runnerXPos = path.get(p);
            runnerYPos = path.get(p + 1);

            createMaze(g2d);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }

        flag = 1;
        createMaze(g2d);

    }

    private void createMaze(Graphics2D g2d){
        int x = 0;
        int y = 0;
        int side = 50;
        for (int row = 0; row < maze.length; row++){
            for(int col = 0; col < maze[row].length; col++){
                if(maze[row][col] == 1){
                    g2d.drawImage(brickImg, x, y, 50, 50, this);
                }else if(maze[row][col] == 0 || maze[row][col] == 9 || maze[row][col] == 2){

                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x, y, side, side);

                    int xRunner = 0, yRunner = 0;
                    // drawing the robot
                    if(row == runnerYPos && col == runnerXPos){
                        xRunner = runnerXPos * side;
                        yRunner = runnerYPos * side;
                        if (flag == 0){
                            xPath.add(runnerXPos);
                            yPath.add(runnerYPos);
                            g2d.drawImage(runnerImg, xRunner + 9, yRunner, 32, 50, this);
                        }
                    }

                    int xEnd = 0, yEnd = 0;
                    // drawing the target image
                    if(maze[row][col] == 9){
                        xEnd = col * side;
                        yEnd = row * side;
                        if(flag == 0){
                            // drawing target image only if it has not been reached
                            g2d.drawImage(targetImg, xEnd + 12, yEnd, 26, 50, this);
                        }else if(flag == 1){
                            // draw stuff after target reached
                            g2d.drawImage(finalImg, xEnd, yEnd, 43, 50, this);
                        }
                    }
                }
                x += side;
            }
            // reset x to the beginning
            x = 0;

            // start a new row by increasing y
            y += side;
        }

        if (flag == 1) {
            JOptionPane.showMessageDialog(null, "Hurrayy!! Mario found the princess!!");

            x = 0;
            y = 0;

            // drawing the path of travel
            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[row].length; col++) {

                    if (maze[row][col] != 1){
                        for(int i = 0; i < xPath.size(); i++)
                            if(col == (int)xPath.get(i) && row == (int)yPath.get(i)) {
                                g2d.setColor(Color.GREEN);
                                g2d.fillRect(x, y, side, side);
                            }

                        // starting square
                        if(col == 1 && row == 1){
                            g2d.setColor(Color.BLACK);
                            g2d.fillRect(x, y, side, side);
                        }
                    }
                    x += side;
                }
                x = 0;
                y += side;
            }

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Jokerman", Font.PLAIN, 40));
            g2d.drawString("Hurrayy!! Mario found the princess!!", 100, VER_HEIGHT / 2);
        }
    }
}
