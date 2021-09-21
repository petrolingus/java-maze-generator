import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ImagTest {

    public static void main(String[] args) throws IOException {

        int size = 4096;
        int[] pixels = new int[size * size * 3];

        for (int i = 0; i < pixels.length; i+=3) {
            pixels[i] = 255;
            pixels[i + 1] = 0;
            pixels[i + 2] = 0;
        }

        long start = System.nanoTime();

        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
//        WritableRaster raster = (WritableRaster) image.getData();
//        raster.setPixels(0, 0, size, size, pixels);
//        image.setData(raster);

        File outfile = new File("saved.bmp");
        ImageIO.write(image, "bmp", outfile);

        long stop = System.nanoTime();
        System.out.println(Duration.ofNanos(stop - start).toMillis() + " ms");
    }

}
