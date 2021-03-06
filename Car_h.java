import greenfoot.*;

/**
 * Car driving horizontal
 */
public class Car_h extends Car
{
    /**
     * Act - do whatever the Car_h wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int stopOffset = 0;

    public int dest;
    public int maxDest;
    public int initDest = -1;

    public void act() 
    {
        /* Make sure we keep a distance between cars */
        if(initDest == -1)
        {
            if(getRotation() == 0)
            {
                stopOffset = defStopOffset;
            }
            else if(getRotation() == 180)
            {
                stopOffset = -defStopOffset;
            }
            initDest = getRotation();
        }

        if((getOneObjectAtOffset(stopOffset, 0, StopBlock.class) != null) || (getOneObjectAtOffset(stopOffset, 0, Car.class) != null))
        {
            move(0);
        }
        else
        {
            move(5);
        }

        /* Left turn */
        if(dest == 1)
        {
            if(getOneIntersectingObject(DestBlock.class) != null)
            {
                setRotation(getRotation()-3);
            }
        }
        
        /* Right turn */
        if(dest == 3)
        {
            if(getOneIntersectingObject(DestBlock.class) != null)
            {
                setRotation(getRotation()+7);
            }
        }
        
        /* If we are to far away from the world destroy ourselves */
        if((getX() > 1100) || (getX() < -100))
        {
            ((TrafficWorld)getWorld()).decCounter();
            getWorld().removeObject(Car_h.this);
        }
        else if((getY() > 1100) || (getY() < -100))
        {
            ((TrafficWorld)getWorld()).decCounter();
            getWorld().removeObject(Car_h.this);
        }
    }

    /* Where are we going to? */
    public void setDestination(int ds)
    {
        dest = ds;

        if(dest == 1)
        {
            maxDest = (getRotation()-90);
        }

        if(dest == 3)
        {
            maxDest = (getRotation()+90);
        }
    }
}
