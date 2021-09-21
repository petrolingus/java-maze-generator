import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int size = 5;

        int[][] maze = new int[size][size];

        int cx = 0;
        int cy = 0;

        Random random = new Random();

        long start = System.nanoTime();


        // Set valid bounds
//        maze[0][0] = 9 << 4;
//        maze[0][size - 1] = 5 << 4;
//        maze[size - 1][size - 1] = 6 << 4;
//        maze[size - 1][0] = 10 << 4;
//        for (int i = 1; i < size - 1; i++) {
//            maze[i][0] = 8 << 4; // left
//            maze[i][size - 1] = 4 << 4; // right
//            maze[0][i] = 1 << 4; //top
//            maze[size - 1][i] = 2 << 4; // bottom
//        }

        for (int i = 0; i < 1000; i++) {

            int dir = 1 << random.nextInt(4);
            int dx = 0;
            int dy = 0;

            if (dir == 1) {
                dy = -1;
            } else if (dir == 2) {
                dy = 1;
            } else if (dir == 4) {
                dx = 1;
            } else {
                dx = -1;
            }

            int nx = cx + dx;
            int ny = cy + dy;

            if ((nx >= 0 && nx < size) && (ny >= 0 && ny < size) && (maze[ny][nx] == 0) ) {
                maze[cy][cx] = dir;
                cx = nx;
                cy = ny;
            }


        }

        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }

        long stop = System.nanoTime() - start;
        System.out.println((stop / 1_000_000) + " ms");

    }
}
