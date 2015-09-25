# Find Image
A Java-based visual grep for finding 2D images in a larger image. This code was originally designed to automate locating elements in screenshots. For example, finding where a button is rendered. It is also useful for code that is automating interactions with 2D games such as poker or [Star Realms](http://www.starrealms.com). The matching can tell where game elements are on the screen, which then allows for inferring game state and where to simulate clicks.

The code is generally helpful for matching any 2D rastered image against a larger image that contains it.

### Command Line Use

Below is an example of command line use. Pass in the file location of the sub-image and image, respectively.

```bash
java -cp FindImage.jar falkner.jayson.findimage.FindImage /tmp/sub_image.png /tmp/image.png
```

The output will be the coordinate of the match (e.g. `0,0`) or a blank line if no match is found.

Note that the command-line use is relatively slow compared to the API use. Launching and shutting down the JVM is typically more expensive than computing a match. If you are matching many images, use the API.

### API Usage

The code is designed to be used with `BufferedImage` instances. Pass the sub-image and image as parameters to the `FindImage.match` method.

```java
// Load the sub-image you want to find that appears in the image, respectively.
BufferedImage subimage = ImageIO.read(new File("/tmp/sub_image.png"));
BufferedImage image = ImageIO.read(new File("/tmp/image.png"));

// Attempt to find the match.
Point match = FindImage.match(subimage, image);

// If null, no match. Otherwise, show where the match occurred.
if (match == null) {
  System.out.println("No match found.");
}
else {
  System.out.println("Found image at " + match.x + "," + match.y);
}
```

### Testing

Unit tests are included in order to assert that this code works for known use cases. These tests are based on <a href="http://junit.org/">JUnit</a> and can be run as follows.

```java
java -cp .:junit-4.XX.jar org.junit.runner.JUnitCore ExactMatchTest
```

Alternatively, your IDE can probably automatically calculate the classpath and run all the tests.
