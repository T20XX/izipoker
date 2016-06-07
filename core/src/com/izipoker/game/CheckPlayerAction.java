package com.izipoker.game;

public class CheckPlayerAction implements Runnable {
    private Player player;
    private volatile boolean isRunning = true;

    public CheckPlayerAction(Player p) {
        player = p;

    }

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

    public void kill() {
        isRunning = false;
    }
}
