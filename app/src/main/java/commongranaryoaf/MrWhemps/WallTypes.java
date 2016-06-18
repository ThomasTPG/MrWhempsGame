package commongranaryoaf.MrWhemps;

/**
 * Created by Thomas on 23/02/2016.
 */
public class WallTypes {
    // A list of all the types of wall
    //Normal walls. You can jump through the bottom
    public static final int NORMAL = 1;
    //Stretches the whole length of the screen, and a section of which you can't jump through
    public static final int HARDCOMPLETE = 2;
    //Normal walls that don't stretch the whole screen
    public static final int NORMALPARTIAL= 3;
    //Hard walls that don't stretch the whole screen
    public static final int HARDPARTIAL = 4;
    //Normal walls that move
    public static final int MOVINGNORMAL = 5;
    //Hard walls that move
    public static final int MOVINGHARD = 6;
    //Half the platform increases the wall speed multiplier, the other half slows it
    public static final int STOPANDGOSTATIONARY = 7;
    //A moving stop and go platform
    public static final int STOPANDGOMOVING= 8;

    //A list of all the types of wall speed
    //Default cube root speed
    public static final int CUBEROOTSPEED = 1;
    //Sinusoidal speed
    public static final int SINUSOIDAL = 2;
    //Square root speed
    public static final int SQUAREROOTSPEED = 3;

    //A list of effects on the platforms by sprite interactio
    //Stop and go with up on left hand side
    public static final int STOPANDGOLEFTUP = 0;
    //Stop and go with down on left
    public static final int STOPANDGOLEFTDOWN = 1;
    //Stop and go platforms increasing the speed
    public static final int MULTIPLIERINCREASE = 2;
    //Stop and go platforms decreasing the speed
    public static final int MULTIPLIERDECREASE = 3;
    //Stop and go platforms do nothing
    public static final int DONOTHING = 4;

}


