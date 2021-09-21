import java.time.Duration;
import java.util.Arrays;
import java.util.random.RandomGenerator;

public class MazeAlgorithm {

    public static void main(String[] args) {

        RandomGenerator randomGenerator = RandomGenerator.of("SplittableRandom");
//        RandomGenerator randomGenerator = RandomGenerator.getDefault();

        int randoms = 0;

        int size = 4096;

        int[][] maze = new int[size][size];
        maze[0][0] = 0;

        int cx = 0;
        int cy = 0;

        // STACK
        int pointer = 0;
        int[] xs = new int[size * size];
        int[] ys = new int[size * size];

        long start = System.nanoTime();

        // Creation maze
        do {

            int lock = 0;

            while (true) {
                int direction = randomGenerator.nextInt(4);
                randoms++;
                if (direction == 0 && cy != 0 && maze[cy - 1][cx] == 0) {
                    maze[cy][cx] |= 1;
                    cy--;
                    maze[cy][cx] |= 2;
                    xs[pointer] = cx;
                    ys[pointer] = cy;
                    pointer++;
//                    System.out.print("dir=" + (direction) + "(top) ");
                    break;
                } else if (direction == 1 && (cy != size - 1) && maze[cy + 1][cx] == 0) {
                    maze[cy][cx] |= 2;
                    cy++;
                    maze[cy][cx] |= 1;
                    xs[pointer] = cx;
                    ys[pointer] = cy;
                    pointer++;
//                    System.out.print("dir=" + (direction) + "(bot) ");
                    break;
                } else if (direction == 2 && (cx != size - 1) && maze[cy][cx + 1] == 0) {
                    maze[cy][cx] |= 4;
                    cx++;
                    maze[cy][cx] |= 8;
                    xs[pointer] = cx;
                    ys[pointer] = cy;
                    pointer++;
//                    System.out.print("dir=" + (direction) + "(right) ");
                    break;
                } else if (direction == 3 && cx != 0 && maze[cy][cx - 1] == 0) {
                    maze[cy][cx] |= 8;
                    cx--;
                    maze[cy][cx] |= 4;
                    xs[pointer] = cx;
                    ys[pointer] = cy;
                    pointer++;
//                    System.out.print("dir=" + (direction) + "(left) ");
                    break;
                }
                lock |= 1 << direction;
                if (lock == 15) {
                    pointer--;
                    cx = xs[pointer];
                    cy = ys[pointer];
//                    System.out.print("break ");
                    break;
                }
            }

        } while (pointer != 0);

        long stop = System.nanoTime();
        System.out.println(Duration.ofNanos(stop - start).toMillis() + " ms");

        System.out.println("randoms:" + randoms);
        // 16777216
        // 178460734

//        System.out.println();
//        for (int i = 0; i < size; i++) {
//            System.out.println(Arrays.toString(maze[i]));
//        }

    }

}
