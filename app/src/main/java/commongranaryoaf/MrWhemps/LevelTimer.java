package commongranaryoaf.MrWhemps;

/**
 * Created by Thomas on 15/02/2016.
 */
public class LevelTimer {
    float startTime;
    float timeElapsed;
    float timeLimit;
    int level;

    public LevelTimer(int level){
        this.level = level;
        getLevelTimeLimit();
        StartTimer();
    }

    public void StartTimer(){
        startTime = System.nanoTime();
    }

    public float getTimeRemaining(){
        timeElapsed = (System.nanoTime() - startTime)/1000000000;
        return (timeLimit - timeElapsed);
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
            default:
                return 0;

        }
    }
}
