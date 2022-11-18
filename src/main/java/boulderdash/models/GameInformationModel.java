package boulderdash.models;

import boulderdash.utils.Observable;

/**
 * GameInformationModel will contain all the data which will
 * go to the InformationPanel.
 */
public class GameInformationModel extends Observable<String> {
    private int score;
    private int remainingsDiamonds;
    private int timer;

    public GameInformationModel(int remainingsDiamonds) {
        score = 0;
        this.remainingsDiamonds = remainingsDiamonds;
        timer = 0;
    }

    /**
     * Returns the actual score
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score
     *
     * @param score Score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the actual number of remaining diamonds
     *
     * @return Remaining diamonds
     */
    public int getRemainingsDiamonds() {
        return remainingsDiamonds;
    }

    /**
     * Sets the number of remainingDiamonds
     *
     * @param remainingDiamonds Remaining diamonds
     */
    public void setRemainingsDiamonds(int remainingDiamonds) {
        remainingsDiamonds = remainingDiamonds;
    }

    /**
     * Gets the timer
     *
     * @return Timer
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Sets the timer
     *
     * @param timer Timer
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }

    /**
     * Increments the score & notify observers
     */
    public void incrementScore() {
        score += 1;
        myNotify();
    }

    /**
     * Generic function which will notify the observers.
     */
    private void myNotify() {
        notifyObservers("");
    }

    /**
     * Decrement of one the number total of remaining diamonds.
     */
    public void decrementRemainingsDiamonds() {
        if (remainingsDiamonds > 0) {
            remainingsDiamonds -= 1;
            myNotify();
        }
    }

    /**
     * Reset details about object
     */
    public void resetInformations() {
        score = 0;
        timer = 0;
    }

}
