import java.time.Duration;
import java.util.Arrays;
import java.util.random.RandomGenerator;

public class MazeAlgorithm {

    public static void main(String[] args) {

//        RandomGenerator randomGenerator = RandomGenerator.of("L32X64MixRandom");
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
                int bit0 = lock & 1;
                int bit1 = (lock & 2) >> 1;
                int bit2 = (lock & 4) >> 2;
                int bit3 = (lock & 8) >> 3;
                int free = bit0 + bit1 + bit2 + bit3; // occupied bytes
                int direction;
                if (free == 3) {
//                    System.out.println("BITS:");
//                    System.out.println(bit0);
//                    System.out.println(bit1);
//                    System.out.println(bit2);
//                    System.out.println(bit3);
//                    System.out.println("######");
                    if (bit0 == 0) {
                        direction = 0;
                    } else if (bit1 == 0) {
                        direction = 1;
                    } else if (bit2 == 0) {
                        direction = 2;
                    } else {
                        direction = 3;
                    }
                } else {
                    direction = randomGenerator.nextInt(4);
                    randoms++;
                }
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

        System.out.println("random calls:" + randoms);
        // 16777216
        // 178460734
        // 178466550
        // 178487416
        // 178483899
        // 106591713

//        System.out.println();
//        for (int i = 0; i < size; i++) {
//            System.out.println(Arrays.toString(maze[i]));
//        }

    }

}
