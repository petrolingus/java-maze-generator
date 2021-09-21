import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

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

        BufferedImage bi = (BufferedImage) getImageFromArray(pixels, size, size);
        File outfile = new File("saved.jpg");
        ImageIO.write(bi, "jpg", outfile);

        long stop = System.nanoTime() - start;
        System.out.println((stop / 1_000_000) + " ms");
    }

    public static Image getImageFromArray(int[] pixels, int w, int h) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0, 0, w, h, pixels);
        image.setData(raster);
        return image;
    }
}
