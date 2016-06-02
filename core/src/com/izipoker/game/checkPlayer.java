package com.izipoker.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class CheckPlayer implements Runnable {
    private Player player;

    public CheckPlayer(Player p) {
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
