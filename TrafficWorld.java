import greenfoot.*;
import javax.swing.*;
import java.util.List;
import java.awt.*;

/***************************************************
 * Main program
 * 
 * - Initialize variables for world
 * - Initialize alogithm
 * - Spawn stopblocks, destination blocks, cars and traffic lights
 * - Handle score board
 * - Handle lights once world is fulled, algorithm activated
 * 
 * @author (Yorick)
 * @version (1.5)
 ***************************************************/

public class TrafficWorld extends World
{
    /**************************************************
     * Main class for TrafficWorld is defined here
     * 
     * - Variable definition and initialization -
     **************************************************/

    public int directions = 4;
    public int lanes = 2;
    public int carSpawnPos[][][] = {{{-40, 480, 0}, {-40, 530, 0}}, {{480, 940, 270}, {530, 940, 270}}, {{940, 420, 180}, {940, 370, 180}}, {{420, -40, 90}, {370, -40, 90}}};

    public final int dbgVisible = 0;        // Range: ( 0 || 255 )
    public final int maxVehicle = 20;       // Range: ( 20 || 40 )
    public final int spawnRatio = 300;      // Range: ( 200 || 300 )

    private String carImage[] = {"car01.png", "car02.png", "car03.png", "car04.png", "sport01.png", "sport02.png", "sport03.png"};

    private int vehicleCount = 0;
    private int vehicleCountTotal = 0;
    private long lastAdded = System.currentTimeMillis();
    private boolean isGreen = false;
    private int currentLight = 1;

    private int LEFT = 1;
    private int STRAIGHT = 2;
    private int RIGHT = 3;

    /* Invoke external algorithm */
    LightAlgorithm interSection = new LightAlgorithm(directions, lanes);
    
    /* Create ScoreBoard */
    ScoreBoard carsTotal = new ScoreBoard();

    /* Preallocate stopblocks */
    StopBlock Line1bLeft = new Block_h();
    StopBlock Line2bLeft = new Block_h();
    StopBlock Line1bDown = new Block_v();
    StopBlock Line2bDown = new Block_v();
    StopBlock Line1bRight = new Block_h();
    StopBlock Line2bRight = new Block_h();
    StopBlock Line1bUp = new Block_v();
    StopBlock Line2bUp = new Block_v();

    /* Preallocate lights */
    TrafficLight Line1lLeft = new Light_l();
    TrafficLight Line2lLeft = new Light_l();
    TrafficLight Line1lDown = new Light_d();
    TrafficLight Line2lDown = new Light_d();
    TrafficLight Line1lRight = new Light_r();
    TrafficLight Line2lRight = new Light_r();
    TrafficLight Line1lUp = new Light_u();
    TrafficLight Line2lUp = new Light_u();   
      
    /* Constructor for objects of class TrafficWorld */
    public TrafficWorld()
    {   
        super(900, 900, 1, false);

        spawnStopBlock();
        spawnDestBlock();
        spawnLight();
        spawnScoreBoard();
        
        Greenfoot.start();
    }
    
    public void act() 
    {
        spawnRandVehicle();

        if(isFull())
        {
            interSection.activate();
        }

        scoreHandler();
        lightHandler();
    }
    
    public void scoreHandler()
    {
        carsTotal.setParams(vehicleCount, vehicleCountTotal);
    }

    /* Which light do we need to handle, and what status does it get? */
    public void lightHandler()
    {
        if(interSection.isActivated())
        {
            long curTime = System.currentTimeMillis();
            int getMethod = (interSection.getLight()/2)+1;

            if(curTime >= lastAdded + 4500)
            {               
                if(getMethod == 1)
                {
                    lightMethodD_1(false);
                }
                else if(getMethod == 2)
                {
                    lightMethodD_2(false);
                }
                else if(getMethod == 3)
                {
                    lightMethodD_3(false);
                }
                else if(getMethod == 4)
                {
                    lightMethodD_4(false);
                }
                lastAdded = curTime;
            }else if(curTime >= lastAdded + 3500)
            {
                if(getMethod == 1)
                {
                    lightMethodD_1(true);
                }
                else if(getMethod == 2)
                {
                    lightMethodD_2(true);
                }
                else if(getMethod == 3)
                {
                    lightMethodD_3(true);
                }
                else if(getMethod == 4)
                {
                    lightMethodD_4(true);
                }
            }
        }
    }

    /* Algorithm method 1 */
    public void lightMethodD_1(boolean status)
    {
        if(isGreen)
        {
            if(status)
            {
                orangeLight(2);
                orangeLight(6);
            }else{
                redLight(2);
                redLight(6);
                isGreen = false;
                interSection.nextLight();
            }
        }
        else
        {
            greenLight(2);
            greenLight(6);
            isGreen = true;
        }
    }

    /* Algorithm method 2 */
    public void lightMethodD_2(boolean status)
    {
        if(isGreen)
        {
            if(status)
            {
                orangeLight(4);
                orangeLight(8);
            }else{
                redLight(4);
                redLight(8);
                isGreen = false;
                interSection.nextLight();
            }
        }
        else
        {
            greenLight(4);
            greenLight(8);
            isGreen = true;
        }
    }

    /* Algorithm method 3 */
    public void lightMethodD_3(boolean status)
    {
        if(isGreen)
        {
            if(status)
            {
                orangeLight(1);
                orangeLight(5);
            }else{
                redLight(1);
                redLight(5);
                isGreen = false;
                interSection.nextLight();
            }
        }
        else
        {
            greenLight(1);
            greenLight(5);
            isGreen = true;
        }
    }

    /* Algorithm method 4 */
    public void lightMethodD_4(boolean status)
    {
        if(isGreen)
        {
            if(status)
            {
                orangeLight(3);
                orangeLight(7);
            }else{
                redLight(3);
                redLight(7);
                isGreen = false;
                interSection.resetLight();
            }
        }
        else
        {
            greenLight(3);
            greenLight(7);
            isGreen = true;
        }
    }

    /* Set given light to green */
    public void greenLight(int light)
    {
        switch(light)
        {
            case 1: 
            removeObject(Line1bLeft);
            Line1lLeft.setImage("light_green_l.png");
            break;
            case 2: 
            removeObject(Line2bLeft);
            Line2lLeft.setImage("light_green_l.png");
            break;
            case 3: 
            removeObject(Line1bDown);
            Line1lDown.setImage("light_green_d.png");
            break;
            case 4: 
            removeObject(Line2bDown);
            Line2lDown.setImage("light_green_d.png");
            break;
            case 5: 
            removeObject(Line1bRight);
            Line1lRight.setImage("light_green_r.png");
            break;
            case 6: 
            removeObject(Line2bRight);
            Line2lRight.setImage("light_green_r.png");
            break;
            case 7: 
            removeObject(Line1bUp);
            Line1lUp.setImage("light_green_u.png");
            break;
            case 8: 
            removeObject(Line2bUp);
            Line2lUp.setImage("light_green_u.png");
            break;
        }
    }

    /* Set given light to orange */
    public void orangeLight(int light)
    {
        switch(light)
        {
            case 1: 
            Line1lLeft.setImage("light_orange_l.png");
            break;
            case 2: 
            Line2lLeft.setImage("light_orange_l.png");
            break;
            case 3: 
            Line1lDown.setImage("light_orange_d.png");
            break;
            case 4: 
            Line2lDown.setImage("light_orange_d.png");
            break;
            case 5: 
            Line1lRight.setImage("light_orange_r.png");
            break;
            case 6: 
            Line2lRight.setImage("light_orange_r.png");
            break;
            case 7: 
            Line1lUp.setImage("light_orange_u.png");
            break;
            case 8: 
            Line2lUp.setImage("light_orange_u.png");
            break;
        }
    }

    /* Set given light to red */
    public void redLight(int light)
    {
        List objs;

        switch(light)
        {
            case 1:
            objs = getObjectsAt(345, 480, Block_h.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line1bLeft, 345, 480);
                Line1bLeft.getImage().setTransparency(dbgVisible);
                Line1lLeft.setImage("light_red_l.png");
            }
            break;
            case 2:
            objs = getObjectsAt(345, 530, Block_h.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line2bLeft, 345, 530);
                Line2bLeft.getImage().setTransparency(dbgVisible);
                Line2lLeft.setImage("light_red_l.png");
            }
            break;
            case 3:
            objs = getObjectsAt(481, 556, Block_v.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line1bDown, 481, 556);
                Line1bDown.getImage().setTransparency(dbgVisible);
                Line1lDown.setImage("light_red_d.png");
            }
            break;
            case 4:
            objs = getObjectsAt(531, 556, Block_v.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line2bDown, 531, 556);
                Line2bDown.getImage().setTransparency(dbgVisible);
                Line2lDown.setImage("light_red_d.png");
            }
            break;
            case 5:
            objs = getObjectsAt(557, 420, Block_h.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line1bRight, 557, 420);
                Line1bRight.getImage().setTransparency(dbgVisible);
                Line1lRight.setImage("light_red_r.png");
            }
            break;
            case 6:
            objs = getObjectsAt(557, 370, Block_h.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line2bRight, 557, 370);
                Line2bRight.getImage().setTransparency(dbgVisible);
                Line2lRight.setImage("light_red_r.png");
            }
            break;
            case 7:
            objs = getObjectsAt(421, 344, Block_v.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line1bUp, 421, 344);
                Line1bUp.getImage().setTransparency(dbgVisible);
                Line1lUp.setImage("light_red_u.png");
            }
            break;
            case 8:
            objs = getObjectsAt(371, 344, Block_v.class);
            if(objs == null || objs.size() == 0)
            {
                addObject(Line2bUp, 371, 344);
                Line2bUp.getImage().setTransparency(dbgVisible);
                Line2lUp.setImage("light_red_u.png");
            }
            break;
        }
    }

    /* Spawn random vehicles in different directions, lanes and destinations */
    public void spawnRandVehicle()
    {
        int chance = Greenfoot.getRandomNumber(spawnRatio);
        int dest = (Greenfoot.getRandomNumber(2)+2);

        if(chance <= 8)
        {
            switch(chance)
            {
                case 0: 
                spawnVehicle(carSpawnPos[0][0][0], carSpawnPos[0][0][1], carSpawnPos[0][0][2], LEFT);
                break;
                case 1: 
                spawnVehicle(carSpawnPos[1][0][0], carSpawnPos[1][0][1], carSpawnPos[1][0][2], LEFT);
                break;
                case 2: 
                spawnVehicle(carSpawnPos[2][0][0], carSpawnPos[2][0][1], carSpawnPos[2][0][2], LEFT);
                break;
                case 3: 
                spawnVehicle(carSpawnPos[3][0][0], carSpawnPos[3][0][1], carSpawnPos[3][0][2], LEFT);
                break;
                case 4: 
                spawnVehicle(carSpawnPos[0][1][0], carSpawnPos[0][1][1], carSpawnPos[0][1][2], dest);
                break;
                case 5: 
                spawnVehicle(carSpawnPos[1][1][0], carSpawnPos[1][1][1], carSpawnPos[1][1][2], dest);
                break;
                case 6: 
                spawnVehicle(carSpawnPos[2][1][0], carSpawnPos[2][1][1], carSpawnPos[2][1][2], dest);
                break;
                case 7: 
                spawnVehicle(carSpawnPos[3][1][0], carSpawnPos[3][1][1], carSpawnPos[3][1][2], dest);
                break;
            }
        }
    }

    /* Spawn blocks for car lines */
    public void spawnStopBlock()
    {              
        for(int i=0; i<=8; i++)
        {
            redLight(i);
        }
    }

    /* Spawn hit blocks for car lines */
    public void spawnDestBlock()
    {
        DestBlock dB_1 = new DestBlock();
        DestBlock dB_2 = new DestBlock();
        DestBlock dB_3 = new DestBlock();
        DestBlock dB_4 = new DestBlock();

        DestBlock dB_5 = new DestBlock();
        DestBlock dB_6 = new DestBlock();
        DestBlock dB_7 = new DestBlock();
        DestBlock dB_8 = new DestBlock();

        DestBlock dB_9 = new DestBlock();
        DestBlock dB_10 = new DestBlock();
        DestBlock dB_11 = new DestBlock();
        DestBlock dB_12 = new DestBlock();

        addObject(dB_1, 530, 530);
        dB_1.getImage().setTransparency(dbgVisible);

        addObject(dB_2, 370, 370);
        dB_2.getImage().setTransparency(dbgVisible);

        addObject(dB_3, 370, 530);
        dB_3.getImage().setTransparency(dbgVisible);

        addObject(dB_4, 530, 370);
        dB_4.getImage().setTransparency(dbgVisible);

        addObject(dB_5, 370, 409);
        dB_5.getImage().setTransparency(dbgVisible);

        addObject(dB_6, 415, 370);
        dB_6.getImage().setTransparency(dbgVisible);

        addObject(dB_7, 485, 530);
        dB_7.getImage().setTransparency(dbgVisible);

        addObject(dB_8, 530, 493);
        dB_8.getImage().setTransparency(dbgVisible);

        addObject(dB_9, 370, 485);
        dB_9.getImage().setTransparency(dbgVisible);

        addObject(dB_10, 409, 530);
        dB_10.getImage().setTransparency(dbgVisible);

        addObject(dB_11, 530, 415);
        dB_11.getImage().setTransparency(dbgVisible);

        addObject(dB_12, 491, 370);
        dB_12.getImage().setTransparency(dbgVisible);

    }

    /* Spawn traffic lights for car lines */
    public void spawnLight()
    {              
        addObject(Line1lLeft, 315 , 450);
        Line1lLeft.setImage("light_red_l.png");
        addObject(Line2lLeft, 315 , 565);
        Line2lLeft.setImage("light_red_l.png");

        addObject(Line1lDown, 451 , 585);
        Line1lDown.setImage("light_red_d.png");
        addObject(Line2lDown, 565 , 585);
        Line2lDown.setImage("light_red_d.png");

        addObject(Line1lRight, 587 , 450);
        Line1lRight.setImage("light_red_r.png");
        addObject(Line2lRight, 587 , 334);
        Line2lRight.setImage("light_red_r.png");

        addObject(Line1lUp, 451 , 314);
        Line1lUp.setImage("light_red_u.png");
        addObject(Line2lUp, 334 , 314);
        Line2lUp.setImage("light_red_u.png");
    }
    
    /* Spawn score board for statics */
    public void spawnScoreBoard()
    {              
        addObject(carsTotal, 160 , 170);
    }

    /* Spawn one car at given position in direction */
    public void spawnVehicle(int x, int y, int direction, int destination)
    {
        if(vehicleCount < maxVehicle)
        {
            int carImageNum = Greenfoot.getRandomNumber(carImage.length);

            if((direction == 0) || (direction == 180))
            {
                Car carNew = new Car_h();
                addObject(carNew, x , y);
                carNew.setImage(carImage[carImageNum]);
                carNew.setRotation(direction);
                ((Car_h)carNew).setDestination(destination);
            }else if((direction == 90) || (direction == 270))
            {
                Car carNew = new Car_v();
                addObject(carNew, x , y);
                carNew.setImage(carImage[carImageNum]);
                carNew.setRotation(direction);
                ((Car_v)carNew).setDestination(destination);
            }
            vehicleCount++;
            vehicleCountTotal++;
        }
    }

    /*********************
     * Counter functions
     * - Increase counter
     * - Decrease counter
     *********************/

    /* Increase vehicle counter */
    public void incCounter()
    {
        vehicleCount++;
    }

    /* Decrease vehicle counter */
    public void decCounter()
    {
        vehicleCount--;
    }

    /* Maximum vehicle counter reached? */
    public boolean isFull()
    {
        if(vehicleCount == maxVehicle)
        {
            return true;
        }
        return false;
    }
}
