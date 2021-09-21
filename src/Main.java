import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int size = 5;

        int[][] maze = new int[size][size];

        long start = System.nanoTime();


        // Set bounds
        maze[0][0] = 6 << 4;
        maze[0][size - 1] = 10 << 4;
        maze[size - 1][size - 1] = 9 << 4;
        maze[size - 1][0] = 5 << 4;
        for (int i = 1; i < size - 1; i++) {
            maze[i][0] = 7 << 4; // left
            maze[i][size - 1] = 11 << 4; // right
            maze[0][i] = 14 << 4; //top
            maze[size - 1][i] = 13 << 4; // bottom
        }

        for (int i = 0; i < 10; i++) {
            
        }

        System.out.println(maze[0][0] & 15);

        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }

        long stop = System.nanoTime() - start;
        System.out.println((stop / 1_000_000) + " ms");

    }
}
