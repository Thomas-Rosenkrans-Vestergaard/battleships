package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

/* 4 Matrix

  for (int j = 0; j < 10; j++) //j svarer til sizeY
        {
            for (int i = 19; i >= 0; i -= 4) //i svarer til 2'sizeX
            {
                if ((i - j) >= 0 && (i - j) < 10)
                {
                    System.out.println(i - j + ", " + j);
                }
            }

        }
        
   3 Matrix 

        for (int j = 0; j < 10; j++) //j svarer til sizeY
        {
            
            for (int i = 19; i >= 0; i -= 3) //i svarer til 2'sizeX
            {
                if ((i - j) >= 0 && (i - j) < 10)
                {
                    counter++;
                    System.out.println(counter+ " " +(i - j) + ", " + j);
                }
            }

        }

   2 Matrix

             for (int j = 0; j < 10; j++) //j svarer til sizeY
        {
            for (int i = 19; i >= 0; i -= 2) //i svarer til 2'sizeX
            {
                if ((i - j) >= 0 && (i - j) < 10)
                {
                    System.out.println(i - j + ", " + j);
                }
            }

        }


 */
public interface Shooter
{

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds The number of rounds played in a match.
     * @param ships The ships in each round.
     * @param sizeX The horizontal size of the board.
     * @param sizeY The vertical size of the board.
     */
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY);

    /**
     * Called by the Game application to get the Position of your shot.
     * hitFeedBack(...) is called right after this method.
     *
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet
     * supplied in the hitFeedBack(...) method to see if you have sunk any
     * ships.
     *
     * @return Position of you next shot.
     */
    public Position getFireCoordinates(Fleet enemyShips);

    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not.
     *
     * Compare the number of ships in the enemyShips with that given in
     * getFireCoordinates in order to see if you sunk a ship.
     *
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    public void hitFeedBack(boolean hit, Fleet enemyShips);

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    public void startRound(int round);

    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     *
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink
     * all of the enemy's ships.
     *
     * @param enemyPoints int enemy's points this round.
     */
    public void endRound(int round, int points, int enemyPoints);

    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you
     * know how many losses, victories and draws you scored.
     *
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    public void endMatch(int won, int lost, int draw);
}
