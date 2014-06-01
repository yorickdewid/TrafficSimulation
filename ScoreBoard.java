import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;

public class ScoreBoard extends Actor
{
    public static final float FONT_SIZE = 20;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    
    public void setParams(int cars, int total)
    {
        setImage("bord.png");
        GreenfootImage image = getImage();
        Font font = image.getFont();
        font = font.deriveFont(FONT_SIZE);
        image.setFont(font);
        image.setColor(Color.WHITE);
        image.drawString("Aantal auto's: " + cars, 30, 50);
        image.drawString("Totaal vandaag: " + total, 30, 100);
        setImage(image);
    }
}
