# Maze Traversal and Generation

## Summary

A simple program to illustrate maze traversal and random maze generation using Depth First Search algorithm

## Specifications

- JavaFX Application
- Netbeans IDE

## Installation

- Download the project and run on Netbeans or similar IDE.
- _src/FixedMaze_ contains logic for __maze traversal__
  * run main() in _src/FixedMaze/MazeTraverse.java_
- _src/RandomMaze_ has a file to __randomly generate maze__ on every execution
  * run main() in _src/RandomMaze/MazeGeneratorFrame.java_

## Description

### Maze Traversal

There is a simple algorithm for walking through a maze that guarantees finding the exit (assuming there is an exit). If there is no exit, you will arrive at the starting location again. Place your right hand on the wall to your right and begin walking forward. Never remove your hand from the wall. If the maze turns to the right, you follow the wall to the right. As long as you do not remove your hand from the wall, eventually you will arrive at the exit of the maze. There may be a shorter path than the one you have taken, but you are guaranteed to get out of the maze if you follow the algorithm.

Wrote a recursive method mazeTraverse (iterative method) to walk from the starting location of the maze. As mazeTraverse() attempts to locate the exit from the maze, it places Mario in each square in the path. The method displays the maze after each move so the user can watch as the maze is solved.

![screen1](https://github.com/PrateekAdhikaree/MazeTraversalAndGeneration/blob/master/screens/screen1.jpg "Start position")

In the end, when Mario finds the exit (Princess), a message is shown and the path traversed by him is shown in green.

![screen2](https://github.com/PrateekAdhikaree/MazeTraversalAndGeneration/blob/master/screens/screen2.jpg "Princess Found! :)")

![screen3](https://github.com/PrateekAdhikaree/MazeTraversalAndGeneration/blob/master/screens/screen3.jpg "Path traversed by Mario!")

### Maze generator

Redid the previous part but with randomly generated mazes. Wrote a method mazeGenerator() that takes as an argument a double-subscripted array and randomly produces a maze. The method also provides the starting and ending locations of the maze. 

![random1](https://github.com/PrateekAdhikaree/MazeTraversalAndGeneration/blob/master/screens/random_screen1.jpg "Random Maze Generation Example 1")

![random2](https://github.com/PrateekAdhikaree/MazeTraversalAndGeneration/blob/master/screens/random_screen2.jpg "Random Maze Generation Example 2")

![random3](https://github.com/PrateekAdhikaree/MazeTraversalAndGeneration/blob/master/screens/random_screen3.jpg "Random Maze Generation Example 3")

## Thanks!!