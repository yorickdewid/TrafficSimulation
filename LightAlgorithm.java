import javax.swing.*;

/***************************************************
 * Main Algorithm
 * 
 * - Initialize variables for algorithm
 * - Set singly linked list
 * - Return next light on the list
 * - Move pointer up the linked list
 * 
 * @author (Yorick) 
 * @version (0.141592654)
 ***************************************************/

public class LightAlgorithm  
{
    /**************************************************
     * Main class for LightAlgorithm is defined here
     * 
     * - Variable definition and initialization -
     **************************************************/

    public boolean lights[];
    public boolean methods[];

    private boolean active = false;
    private int currentLight;
    private boolean RED = false;
    private boolean GREEN = true;

    /* Constructor for objects of class LightAlgorithm */
    public LightAlgorithm(int directions, int lanes)
    {
        lights = new boolean[(directions * lanes)];
        methods = new boolean[directions];
        currentLight = 0;

        for(int i = 0; i < lights.length; i++) 
        {
            setLightStatus(i, RED);
        }
    }

    private void setLightStatus(int iterator, boolean status)
    {
        lights[iterator] = status;
    }

    private boolean getLightStatus(int iterator)
    {
        return lights[iterator];
    }

    public void activate()
    {
        active = true;
    }

    public void deactivate()
    {
        active = false;
    }

    public boolean isActivated()
    {
        return active;
    }

    public void resetLight(int light)
    {
        if(active)
        {
            setLightStatus(light, false);
        }
    }

    public int getLight()
    {
        if(!active)
        {
            return -1;
        }

        if(getLightStatus(currentLight) == RED)
        {
            setLightStatus(currentLight, GREEN);
        }

        return currentLight;
    }

    public void nextLight()
    {

        setLightStatus(currentLight, RED);

        if(currentLight == (lights.length-1))
        {
            resetLight();
        }
        else
        {
            currentLight += 2;
        }

    }

    public void resetLight()
    {
        currentLight = 0;
    }
}
