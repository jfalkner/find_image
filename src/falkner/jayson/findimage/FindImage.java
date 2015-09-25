package falkner.jayson.findimage;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Utility class for image matching. This code is useful for cases where you are
 * trying to find where a sub-image appears in a larger image. For example,
 * where a button appears on a screen shot of a web page.
 *
 * @author Jayson Falkner - jfalkner@gmail.com
 */
public class FindImage {

    /**
     * Useful entry point for matching images via command-line. Simply run the
     * code with the sub-image and image passed as arguments.
     *
     * java FindImage sub_image.png image.png
     *
     * Note, if matching many images, use the `match` method as an API.
     * Launching the JVM every time you need to do a match is relatively slow!
     * This entry point mostly exists to formally capture the simple demo code.
     *
     * @param args The file location of the sub-image and image, respectively.
     * @throws Exception If either of the image files can't be found.
     */
    public static void main(String[] args) throws Exception {
        // Sanity check. Make sure the needed arguments exist.
        if (args.length != 2) {
            System.out.println("Must pass two filenames. The sub-image and image.");
            return;
        }

        // Load the sub-image you want to find that appears in the image, respectively.
        BufferedImage subimage = ImageIO.read(new File(args[0]));
        BufferedImage image = ImageIO.read(new File(args[1]));

        // Attempt to find the match.
        Point match = FindImage.match(subimage, image);

        // If null, no match. Otherwise, show where the match occurred.
        if (match == null) {
            System.out.println("");
        } else {
            System.out.println(match.x + "," + match.y);
        }
    }

    public static Point match(BufferedImage subimage, BufferedImage image) {
        // brute force N^2 check all places in the image
        for (int i = 0; i <= image.getWidth() - subimage.getWidth(); i++) {
            check_subimage:
            for (int j = 0; j <= image.getHeight() - subimage.getHeight(); j++) {
                for (int ii = 0; ii < subimage.getWidth(); ii++) {
                    for (int jj = 0; jj < subimage.getHeight(); jj++) {
                        if (subimage.getRGB(ii, jj) != image.getRGB(i + ii, j + jj)) {
                            continue check_subimage;
                        }
                    }
                }
                // if here, all pixels matched
                return new Point(i, j);
            }
        }
        return null;
    }

    public static BufferedImage screenshot() throws AWTException {
        Robot r = new Robot();
        // take a full screenshot
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return r.createScreenCapture(screenRect);
    }

}
