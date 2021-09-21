import java.util.Arrays;
import java.util.random.RandomGenerator;

public class MazeAlgorithm {

    public static void main(String[] args) {

        RandomGenerator randomGenerator = RandomGenerator.of("SplittableRandom");

        int size = 5;

        int[][] maze = new int[size][size];

        int[][] visited = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                visited[i][j] = 0;
            }
        }

        int current = 0;
        int[] xs = new int[size];
        int[] ys = new int[size];
        xs[current] = 0;
        ys[current] = 0;

        // Set bounds
        maze[0][0] = 9;
        maze[0][size - 1] = 5;
        maze[size - 1][size - 1] = 6;
        maze[size - 1][0] = 10;
        for (int i = 1; i < size - 1; i++) {
            maze[i][0] = 8; // left
            maze[i][size - 1] = 4; // right
            maze[0][i] = 1; //top
            maze[size - 1][i] = 2; // bottom
        }

        // Creation maze
        for (int i = 0; i < 10; i++) {

            int direction = 1 << randomGenerator.nextInt(4);
            int wall = maze[xs[current]][ys[current]];
            if (wall == 15) {
                current--;
                continue;
            }
            while ((direction & wall) == 1) {
                direction = 1 << randomGenerator.nextInt(4);
            }

            maze[xs[current]][ys[current]] |= direction;
            current++;

            if (direction == 1) {
                ys[current] = ys[current - 1] - 1;
                maze[xs[current]][ys[current]] |= direction << 1;
            } else if (direction == 2) {
                ys[current] = ys[current - 1] + 1;
                maze[xs[current]][ys[current]] |= direction >> 1;
            } else if (direction == 4) {
                xs[current] = xs[current - 1] + 1;
                maze[xs[current]][ys[current]] |= direction << 1;
            } else {
                xs[current] = xs[current - 1] - 1;
                maze[xs[current]][ys[current]] |= direction >> 1;
            }

        }

        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }

    }
}
