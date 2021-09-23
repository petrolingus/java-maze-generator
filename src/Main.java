import image.BMP;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.random.RandomGenerator;

public class Main {

    public static void main(String[] args) {

        long start = System.nanoTime();

        RandomGenerator randomGenerate = RandomGenerator.of("SplittableRandom");

        int size = 50;

        int[][] maze = new int[size][size];
        int[][] virtualMaze = new int[size][size];

        int[] vd = {1, 2, 4, 8};
        int[] ivd = {2, 1, 8, 4};

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[0][i] = 1;
                maze[size - 1][i] = 1;
                maze[i][size - 1] = 1;
                maze[i][0] = 1;
            }
        }

        int[] dx = {0, 0, 1, -1};
        int[] dy = {-1, 1, 0, 0};

        int direction = 0;

        int cx = 1;
        int cy = 1;

        int[] pathX = new int[size * size];
        int[] pathY = new int[size * size];
        int pathPointer = 0;

        boolean isPath = true;

        while (true) {

            int up = maze[cy - 1][cx];
            int down = maze[cy + 1][cx];
            int right = maze[cy][cx + 1];
            int left = maze[cy][cx - 1];

            if ((up + down + right + left) == 4) {
//                virtualMaze[cy][cx] &= 15;
                maze[cy][cx] = 1;
                pathPointer--;
                cx = pathX[pathPointer];
                cy = pathY[pathPointer];
                if (pathPointer == 1) {
                    break;
                }
                continue;
            }

            pathX[pathPointer] = cx;
            pathY[pathPointer] = cy;
            pathPointer++;

            int[] neighbours = {up, down, right, left};

            direction = randomGenerate.nextInt(4);
            int neighbour = neighbours[direction];
            int counter = 1;
            while (neighbour == 1) {
                direction = (direction + counter) % 4;
                counter++;
                neighbour = neighbours[direction];
            }

//            System.out.print(direction + " ");

            maze[cy][cx] = 1;

            if (cx == size - 2 && cy == size - 2) {
                isPath = false;
            }

            virtualMaze[cy][cx] |= vd[direction] | ((isPath) ? 16 : 0);
            cx = cx + dx[direction];
            cy = cy + dy[direction];
            virtualMaze[cy][cx] |= ivd[direction] | ((isPath) ? 16 : 0);

        }

//        System.out.println();
//
//        for (int i = 0; i < size; i++) {
//            System.out.println(Arrays.toString(virtualMaze[i]));
//        }
//
//        System.out.println("Path: ");
//        for (int x : pathX) {
//            System.out.print(x + " ");
//        }
//        System.out.println();
//        for (int y : pathY) {
//            System.out.print(y + " ");
//        }

        long stop = System.nanoTime();
        System.out.println(Duration.ofNanos(stop - start).toMillis() + " ms");

        generateImage(virtualMaze, "image");

    }

    private static void generateImage(int[][] maze, String name) {

        int cellSize = 4;
        int cellSizeMinusOne = cellSize - 1;

        int size = maze.length;
        int fixedSize = size - 2;
        int imageSize = fixedSize * cellSize;
        int[][] rgbValues = new int[imageSize + 2][imageSize + 2];

        int red = 255;
        int white = 33554431;

        for (int i = 0; i < fixedSize; i++) {
            for (int j = 0; j < fixedSize; j++) {

                int code = maze[i + 1][j + 1];
                int up = code & 1;
                int bot = code & 2;
                int right = code & 4;
                int left = code & 8;
                int path = code & 16;
                int x = cellSize * j;
                int y = cellSize * i;
                int color = (path != 0) ? red : white;
                for (int k = 1; k < cellSizeMinusOne; k++) {
                    for (int l = 1; l < cellSizeMinusOne; l++) {
                        rgbValues[x + l + 1][y + k + 1] = color;
                    }
                }
                if (up != 0) {
                    for (int k = 1; k < cellSizeMinusOne; k++) {
//                        rgbValues[y][x + k] = color;
                        rgbValues[x + k + 1][y + 1] = color;
                    }
                }
                if (bot != 0) {
                    for (int k = 1; k < cellSizeMinusOne; k++) {
//                        rgbValues[y + cellSizeMinusOne][x + k] = color;
                        rgbValues[x + k + 1][y + cellSizeMinusOne + 1] = color;
                    }
                }
                if (right != 0) {
                    for (int k = 1; k < cellSizeMinusOne; k++) {
//                        rgbValues[y + k + 1][x + cellSizeMinusOne + 1] = color;
                        rgbValues[x + cellSizeMinusOne + 1][y + k + 1] = color;
                    }
                }
                if (left != 0) {
                    for (int k = 1; k < cellSizeMinusOne; k++) {
//                        rgbValues[y + k][x] = color;
                        rgbValues[x + 1][y + k + 1] = color;
                    }
                }
            }
        }


        BMP bmp = new BMP();
        try {
            bmp.saveBMP(name + ".bmp", rgbValues);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
