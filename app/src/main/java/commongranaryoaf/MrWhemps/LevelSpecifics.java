package commongranaryoaf.MrWhemps;

/**
 * Created by Thomas on 19/02/2016.
 */
public class  LevelSpecifics {
    int level;

    public LevelSpecifics(int inputlevel){
        level = inputlevel;

    }

    public boolean isBonusLevel(){
        switch(level){
            case (1):
                return true;
            case (8):
                return true;
            case (11):
                return true;
            case (17):
                return true;
            case (23):
                return true;
            default:
                return false;
        }
    }

    public int challengeScoreEasy(){
        switch (level){
            case(1):
                return 160;
            case(2):
                return 400;
            case(3):
                return 90;
            case(4):
                return 120;
            case(5):
                return 200;
            case(6):
                return 200;
            case(7):
                return 100;
            case(8):
                return 200;
            case(9):
                return 350;
            case(10):
                return 220;
            case(11):
                return 450;
            case(12):
                return 300;
            case(13):
                return 200;
            case(14):
                return 200;
            case(15):
                return 100;
            case(16):
                return 200;
            case(17):
                return 500;
            case(18):
                return 200;
            case(19):
                return 100;
            case(20):
                return 200;
            case(21):
                return 150;
            case(22):
                return 100;
            case(23):
                return 500;
            default:
                return 100;
        }
    }

    public int challengeScoreMedium(){
        switch (level){
            case(1):
                return 170;
            case(2):
                return 800;
            case(3):
                return 120;
            case(4):
                return 250;
            case(5):
                return 400;
            case(6):
                return 400;
            case(7):
                return 200;
            case(8):
                return 400;
            case(9):
                return 750;
            case(10):
                return 450;
            case(11):
                return 950;
            case(12):
                return 600;
            case(13):
                return 400;
            case(14):
                return 400;
            case(15):
                return 200;
            case(16):
                return 400;
            case(17):
                return 1000;
            case(18):
                return 400;
            case(19):
                return 200;
            case(20):
                return 400;
            case(21):
                return 300;
            case(22):
                return 200;
            case(23):
                return 1000;
            default:
                return 100;
        }
    }

    public int challengeScoreHard(){
        switch (level){
            case(1):
                return 180;
            case(2):
                return 1400;
            case(3):
                return 170;
            case(4):
                return 400;
            case(5):
                return 650;
            case(6):
                return 800;
            case(7):
                return 400;
            case(8):
                return 800;
            case(9):
                return 1500;
            case(10):
                return 900;
            case(11):
                return 1900;
            case(12):
                return 1200;
            case(13):
                return 800;
            case(14):
                return 600;
            case(15):
                return 400;
            case(16):
                return 500;
            case(17):
                return 1600;
            case(18):
                return 600;
            case(19):
                return 300;
            case(20):
                return 600;
            case(21):
                return 400;
            case(22):
                return 300;
            case(23):
                return 1500;
            default:
                return 100;
        }
    }
}
