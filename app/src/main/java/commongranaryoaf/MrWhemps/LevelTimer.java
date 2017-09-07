package commongranaryoaf.MrWhemps;

/**
 * Created by Thomas on 15/02/2016.
 */
public class LevelTimer {
    float startTime;
    float timeElapsed;
    float timeLimit;
    int level;
    boolean running = true;

    public LevelTimer(int level){
        this.level = level;
        getLevelTimeLimit();
        StartTimer();
    }

    public void StartTimer(){
        startTime = System.nanoTime();
    }

    public void pause()
    {
        running = false;
    }

    public double getTimeRemaining(){
        if (running)
        {
            timeElapsed = (System.nanoTime() - startTime)/1000000000;
        }
        else
        {
            timeElapsed = Math.max(timeElapsed,0);
        }
        double timeRemaining = timeLimit - timeElapsed;
        timeRemaining = Math.round(timeRemaining*10) / 10.0;
        return (timeRemaining);
    }

    public boolean checkTimeUp(){
        if (timeElapsed<timeLimit){
            return false;
        }
        else{
            return true;
        }
    }

    public void getLevelTimeLimit(){
        switch (level){
            case(1):
                timeLimit = 15;
                break;
            case(2):
                timeLimit = 15;
                break;
            case(7):
                timeLimit = 30;
                break;
            case(9):
                timeLimit = 40;
                break;
            case(10):
                timeLimit = 40;
                break;
            case(13):
                timeLimit = 30;
                break;
            case(14):
                timeLimit = 40;
                break;
            case(15):
                timeLimit = 30;
                break;
            case(16):
                timeLimit = 40;
                break;
            case(17):
                timeLimit = 40;
                break;
            case(18):
                timeLimit = 40;
                break;
            case(19):
                timeLimit = 40;
                break;
            case(20):
                timeLimit = 40;
                break;
            case(21):
                timeLimit = 40;
                break;
            case(24):
                timeLimit = 40;
                break;
            case(25):
                timeLimit = 40;
                break;
            case(26):
                timeLimit = 40;
                break;
            case(27):
                timeLimit = 40;
                break;
            case(29):
                timeLimit = 40;
                break;
            
            default:
                timeLimit = 20;
                break;
        }
    }

    public int getScoreLimit(){
        switch (level){
            case(8):
                return 400;
            case(11):
                return 300;
            case(17):
                return 400;
            case(23):
                return 400;
            case(28):
                return 400;
            case(30):
                return 400;
            default:
                return 0;

        }
    }
}
