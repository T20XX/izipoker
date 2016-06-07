package com.izipoker.game;

/**
 * Runnable class that checks for an action from a player
 */
public class CheckPlayerAction implements Runnable {
    private Player player;
    private volatile boolean isRunning = true;

    /**
     * @param p Player to check action
     */
    public CheckPlayerAction(Player p) {
        player = p;

    }

    /**
     * Verifies player action 2 times in a second
     */
    @Override
    public void run() {
        while (!player.hasActed() && isRunning) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the running of the thread
     */
    public void kill() {
        isRunning = false;
    }
}
