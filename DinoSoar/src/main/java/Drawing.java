import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class Drawing extends Canvas
{
    Car car;
    boolean isInitialized = false;
    String path = "images/";
    Random randGen = new Random();

    public Drawing(Car car)
    {
        this.car = car;
    }

    public void paint(Graphics g)
    {
        //initialize background image
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("images/", "background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(backgroundImage, 0,0,null);

        //initialize Dinosoar image
        BufferedImage dinosoar = null;
        try {
            dinosoar = ImageIO.read(new File("images/", "dino1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        g.drawImage(dinosoar, 0,0,null);
//        g.drawImage(dinosoar, 50,100,null);
        int dinosoarsKilled = (int) (car.getDistance() / 20000);
//

        for(int i = 0; i < dinosoarsKilled; i++)
        {
            int randX = randGen.nextInt(1100);
            int randY = randGen.nextInt(500);
            g.drawImage(dinosoar, randX, randY, null);
        }

        g.drawString("Car: " + car.getMake() + " " + car.getModel(), 0, 50);
        g.drawString("Year: " + car.getYear(), 0, 70);
        g.drawString("Mileage: " + car.getDistance(), 200, 50);
        g.drawString("Dinosaurs Killed: " + dinosoarsKilled, 400, 50);
    }

    public static void initialize(Graphics g)
    {

    }
}