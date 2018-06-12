/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FixedMaze;

import java.util.List;

/**
 *
 * @author ceto
 */
public class DepthFirst {
    
    public static boolean searchPath(int[][] maze, int x, int y, List<Integer> path){

        // Checking if target node was reached
        if (maze[y][x] == 9){
            return true;
        }

        // If current position (x, y) is non-visited node,
        // let's mark it as visited ie '2'
        if(maze[y][x] == 0) {
            maze[y][x] = 2;

            // Visiting all neighbor nodes recursively if path found, filling the path list

            // left block
            int dx = -1;
            int dy = 0;
            if(searchPath(maze, x + dx, y + dy, path)){
                return true;
            }else{
                path.add(x);
                path.add(y);
            }

            // right block
            dx = 1;
            dy = 0;
            if(searchPath(maze, x + dx, y + dy, path)){
                return true;
            }else{
                path.add(x);
                path.add(y);
            }

            // top block
            dx = 0;
            dy = -1;
            if(searchPath(maze, x + dx, y + dy, path)){
                return true;
            }else{
                path.add(x);
                path.add(y);
            }

            // bottom block
            dx = 0;
            dy = 1;
            if(searchPath(maze, x + dx, y + dy, path)){
                return true;
            }else{
                path.add(x);
                path.add(y);
            }
        }

        return false;
    }
    
}
