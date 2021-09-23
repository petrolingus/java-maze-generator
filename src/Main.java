import image.BMP;

import java.io.IOException;
import java.util.Arrays;
import java.util.random.RandomGenerator;

public class Main {

    public static void main(String[] args) {

        RandomGenerator randomGenerate = RandomGenerator.of("SplittableRandom");

        int size = 5;

        int[][] maze = new int[size][size];

        for (int i = 0; i < size; i++) {
            maze[0][i] = 1;
            maze[size - 1][i] = 1;
            maze[i][0] = 1;
            maze[i][size - 1] = 1;
        }

        int cx = 1;
        int cy = 1;

        while (true) {

            int up = maze[cy - 1][cx];
            int down = maze[cy + 1][cx];
            int right = maze[cy][cx + 1];
            int left = maze[cy][cx - 1];

            if ((up + down + right + left) == 4) {
                break;
            }

            int[] neighbours = {up, down, right, left};

            int index = randomGenerate.nextInt(4);
            int direction = neighbours[index];
            while (direction == 1) {
                direction = randomGenerate.nextInt(4); // TODO: think about this, we can use increment
            }

            cx = cx + 1;
            cy = cy + 1;



        }

        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }

    }

    private static void generateImage(int[][] maze, String name) {

        int size = maze.length;
        int imageSize = size * 4;
        int[][] rgbValues = new int[imageSize][imageSize];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int code = maze[i][j];
                int up = code & 1;
                int bot = code & 2;
                int right = code & 4;
                int left = code & 8;
                int x = 4 * j;
                int y = 4 * i;
                rgbValues[y + 1][x + 1] = 33554431;
                rgbValues[y + 2][x + 2] = 33554431;
                rgbValues[y + 1][x + 2] = 33554431;
                rgbValues[y + 2][x + 1] = 33554431;
                if (up != 0) {
                    rgbValues[y][x + 1] = 33554431;
                    rgbValues[y][x + 2] = 33554431;
                }
                if (bot != 0) {
                    rgbValues[y + 3][x + 1] = 33554431;
                    rgbValues[y + 3][x + 2] = 33554431;
                }
                if (right != 0) {
                    rgbValues[y + 1][x + 3] = 33554431;
                    rgbValues[y + 2][x + 3] = 33554431;
                }
                if (left != 0) {
                    rgbValues[y + 1][x] = 33554431;
                    rgbValues[y + 2][x] = 33554431;
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
