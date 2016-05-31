package com.izipoker.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class checkPlayer {
    private Player player;

    public checkPlayer(Player p) {
        player = p;
        javax.swing.Timer timer = new javax.swing.Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //if(!player.WAITING)
                //timer.cancel;
            }
        });
    }
}
