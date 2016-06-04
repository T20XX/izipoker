package com.izipoker.game;

public class CheckPlayerAction implements Runnable {
    private Player player;

    public CheckPlayerAction(Player p) {
        player = p;

    }

    @Override
    public void run() {
        while(!player.hasActed()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
