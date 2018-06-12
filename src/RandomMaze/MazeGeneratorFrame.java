/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RandomMaze;

import FixedMaze.DepthFirst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author ceto
 */
public class MazeGeneratorFrame  extends JFrame {

    private static int numRow = 17, numCol = 17;

    private static int CELL_SIZE = 50;

    private static int HOR_WIDTH = numRow * CELL_SIZE;
    private static int VER_HEIGHT = numCol * CELL_SIZE;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame obj = new MazeGeneratorFrame();
                obj.setSize(HOR_WIDTH, VER_HEIGHT);
                obj.setTitle("Maze Generator");
                obj.setLayout(new BorderLayout());
                obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                obj.setLocationRelativeTo(null);
                obj.setVisible(true);
            }
        });
    }

    public MazeGeneratorFrame(){
        mazeTemp = new int[numCol+2][];
        maze = new int[numCol][numRow];

        generate();

        try {
            runnerImg = ImageIO.read(new File(absoluteFilePath + "runner.png"));
            targetImg = ImageIO.read(new File(absoluteFilePath + "target.jpg"));
            brickImg = ImageIO.read(new File(absoluteFilePath + "brick.jpg"));
            finalImg = ImageIO.read(new File(absoluteFilePath + "final.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DepthFirst.searchPath(maze, startX, startY, path);
    }

    private static final int FREE = 0;
    private static final int WALL = 1;
    private static final int START = 4;
    private static final int END = 9;

    private int[][] mazeTemp;
    private static int[][] maze;
    private java.util.Random rand = new java.util.Random();

    // to handle OS differences for file path
    private String resourcesFolder = "res" + File.separator;
    private String workingDirectory = System.getProperty("user.dir");
    private String absoluteFilePath = workingDirectory + File.separator + resourcesFolder;

    private Image runnerImg;
    private Image targetImg;
    private Image finalImg;
    private Image brickImg;

    private int flag = 0;

    private int runnerXPos = 0;
    private int runnerYPos = 0;
    private int startX = 0;
    private int startY = 0;

    private java.util.List<Integer> path = new ArrayList<Integer>();

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        createMaze(g2d);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

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
                    // starting square
                    if(col == startX && row == startY){
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(x, y, side, side);
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

    public void generate() {
        boolean start = false;
        boolean end = false;

        for(int x = 0; x < numCol+2; x++) {
            mazeTemp[x] = new int[numRow+2];
            for(int y = 0; y < numRow+2; y++) {
                mazeTemp[x][y] = WALL;
            }
        }
        for(int x = 0; x < numCol+2; x++) {
            mazeTemp[x][0] = FREE;
            mazeTemp[x][numRow+2 - 1] = FREE;
        }
        for(int y = 0; y < numRow+2; y++) {
            mazeTemp[0][y] = FREE;
            mazeTemp[numCol+2 - 1][y] = FREE;
        }

        mazeTemp[2][2] = FREE;
        carve(2, 2);

        // generating random start and end positions
        int randCol = 0;
        int randRow = 0;

        while(true){
            if(!start) {
                randCol = ThreadLocalRandom.current().nextInt(1, numCol+2-1);
                randRow = ThreadLocalRandom.current().nextInt(1, numRow+2-1);
                if (mazeTemp[randCol][randRow] == FREE) {
                    mazeTemp[randCol][randRow] = START;
                    start = true;
                }
            }

            if(!end) {
                randCol = ThreadLocalRandom.current().nextInt(1, numCol+2-1);
                randRow = ThreadLocalRandom.current().nextInt(1, numRow+2-1);
                if (mazeTemp[randCol][randRow] == FREE) {
                    mazeTemp[randCol][randRow] = END;
                    end = true;
                }
            }

            if(start && end)
                break;
        }

        getFinalMaze();
    }

    private void carve(int x, int y) {

        final int[] upx = { 1, -1, 0, 0 };
        final int[] upy = { 0, 0, 1, -1 };

        int dir = rand.nextInt(4);
        int count = 0;
        while(count < 4) {
            final int x1 = x + upx[dir];
            final int y1 = y + upy[dir];
            final int x2 = x1 + upx[dir];
            final int y2 = y1 + upy[dir];
            if(mazeTemp[x1][y1] == WALL && mazeTemp[x2][y2] == WALL) {
                mazeTemp[x1][y1] = FREE;
                mazeTemp[x2][y2] = FREE;
                carve(x2, y2);
            } else {
                dir = (dir + 1) % 4;
                count += 1;
            }
        }
    }

    public void getFinalMaze(){
        for (int y = 0; y < mazeTemp.length; y++){
            for (int x = 0; x < mazeTemp[0].length; x++){
                if((y != 0 && y != mazeTemp.length-1) && (x != 0 && x != mazeTemp[0].length-1)){
                    if (mazeTemp[y][x] == START){
                        runnerXPos = x-1;
                        runnerYPos = y-1;
                        startX = runnerXPos;
                        startY = runnerYPos;
                        maze[y-1][x-1] = 0;
                    } else {
                        maze[y-1][x-1] = mazeTemp[y][x];
                    }
                }
            }
        }
    }
}
