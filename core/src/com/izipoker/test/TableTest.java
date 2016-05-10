package com.izipoker.test;

import com.izipoker.game.Human;
import com.izipoker.game.Player;
import com.izipoker.game.Table;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Telmo on 10/05/2016.
 */
public class TableTest {
    @Test
    public void addPlayertoTable(){
        Table t = new Table(8);
        Player p = new Human(0,"Teste",1000);

        assertNull(t.getSeats()[0]);
        t.addPlayer(p);
        assertEquals(p, t.getSeats()[0]);
    }
}