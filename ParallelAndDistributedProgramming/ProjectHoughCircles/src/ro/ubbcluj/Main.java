package ro.ubbcluj;

import javax.imageio.ImageIO;
import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();
        HoughTransformation houghTransformation = new HoughTransformation();
        //for smaller run with 5/15 as radiuses and standard with 20-60
        houghTransformation.applyHoughTransformation(ImageIO.read(new File("smaller.png")),5,15,100,0.2);
        Instant end = Instant.now();
        System.out.println(Duration.between(start,end).toSeconds());
    }
}
