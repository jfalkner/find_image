package falkner.jayson.findimage.exact;

import falkner.jayson.findimage.FindImage;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * Exact match tests based on known images located in this package. The tests
 * ensure that the full image is checked, including extreme near and far edges.
 * A test case for true negatives is included too. It confirms that the code 
 * correctly returns no match, if no match exists.
 *
 * @author Jayson Falkner - jfalkner@gmail.com
 */
public class ExactMatchTest {

    public ExactMatchTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Helper method to load images that are included with the test case.
     *
     * @param image Name of the image to load. e.g. "thumb.png"
     * @return A BufferedImage instance or an Exception, if no image is found.
     * @throws IOException
     */
    private BufferedImage load_image(String image) throws IOException {
        return ImageIO.read(ClassLoader.getSystemResource("falkner/jayson/findimage/exact/" + image));
    }

    /**
     * Asserts an image at the extreme NW coordinate is matched. Useful to
     * ensure that the code correctly checks the initial edges of the image.
     */
    @Test
    public void match_nw() throws Exception {
        match("white_thumb.png", "white_thumb_NW.png", 0, 0);
    }

    /**
     * Asserts an image at the extreme SE coordinate is matched. Useful to
     * ensure that the code correctly checks the far edges of the image.
     */
    @Test
    public void match_se() throws Exception {
        match("white_thumb.png", "white_thumb_SE.png", 84, 84);
    }

    /**
     * Asserts images in the middle are matched. No extreme edge.
     */
    @Test
    public void match_middle() throws Exception {
        match("white_thumb.png", "white_thumb_middle.png", 42, 42);
    }

    /**
     * Helper method for the common test of finding a sub-image in an image at a
     * known location.
     */
    public void match(String subimage_name, String image_name, int x, int y) throws Exception {
        // the thumb (no background)
        BufferedImage thumb = load_image(subimage_name);
        // thumb in NW quadrant
        BufferedImage image = load_image(image_name);
        // find the exact match for the thumb in the greater image.
        Point match = FindImage.match(thumb, image);
        assertNotNull(match);
        assertEquals(match.x, x);
        assertEquals(match.y, y);
    }

    /**
     * Asserts that cases expecting no match fail to find a match.
     */
    @Test
    public void nomatch() throws Exception {
        // the thumb (no background)
        BufferedImage thumb = load_image("white_thumb.png");
        // thumb in NW quadrant
        BufferedImage image = load_image("white_100x100.png");
        // find the exact match for the thumb in the greater image.
        Point match = FindImage.match(thumb, image);
        assertNull(match);
    }
}
