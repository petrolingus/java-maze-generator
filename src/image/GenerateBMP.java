package image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class GenerateBMP {

    public static void main(String[] args) throws IOException {

        int size = 4096;
        long start = System.nanoTime();


        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        File outfile = new File("saved.bmp");
        ImageIO.write(bufferedImage, "bmp", outfile);

        long stop = System.nanoTime();
        System.out.println(Duration.ofNanos(stop - start).toMillis() + " ms");

//        generateImage();
//        generateImageFromArray();
    }

    public static void generateImage() throws IOException {
        int size = 4096;
        long start = System.nanoTime();
        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        File outfile = new File("GenerateBMP.bmp");
        WritableRaster raster = (WritableRaster) bufferedImage.getData();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                raster.setPixels(j, i, 1, 1, new int[]{255, 0, 0});
            }
        }
        bufferedImage.setData(raster);
        ImageIO.write(bufferedImage, "bmp", outfile);
        long stop = System.nanoTime();
        System.out.println(Duration.ofNanos(stop - start).toMillis() + " ms");
    }

    private static void generateImageFromArray() throws IOException {

        byte[] bytes = Files.readAllBytes(Path.of("test.bmp"));

        long start = System.nanoTime();

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        ImageIO.write(image, "BMP", new File("filename.bmp"));

        long stop = System.nanoTime();
        System.out.println(Duration.ofNanos(stop - start).toMillis() + " ms");
    }
}
