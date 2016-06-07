package com.izipoker.test;

import com.izipoker.game.Human;
import com.izipoker.game.Player;
import com.izipoker.game.Table;

import org.junit.Test;

import static org.junit.Assert.*;


public class TableTest {
    @Test
    public void testAddandRemovePlayer() {
        Table t = new Table("teste", 8, 10, 1000);
        Player p = new Human(0, "Teste", 1000);

        assertEquals(t.getSeats().length,0);
        t.addPlayer(p);
        assertEquals(p, t.getSeats()[0]);
        Player p1 = new Human(0, "Teste", 1000);
        t.addPlayer(p1);
        assertEquals(t.getSeats().length,2);
        t.removePlayer(p);
        assertEquals(t.getSeats().length,1);
        t.removePlayer(p1);
        assertEquals(t.getSeats().length,0);
    }

    @Test
    public void testNextJoker() {
        Table t = new Table("teste", 8, 10, 1000);
        Player p1 = new Human(0, "Teste", 1000);
        t.addPlayer(p1);
        Player p2 = new Human(0, "Teste", 1000);
        t.addPlayer(p2);

        assertEquals(t.getJoker(),p1);
        t.nextJoker();
        assertEquals(t.getJoker(),p2);
        t.nextJoker();
        assertEquals(t.getJoker(),p1);
    }

    @Test
    public void testActivePlayers(){
        Table t = new Table("teste", 8, 10, 1000);
        Player p1 = new Human(0, "Teste", 1000);
        t.addPlayer(p1);
        Player p2 = new Human(0, "Teste", 1000);
        t.addPlayer(p2);

        assertEquals(t.getActivePlayers().length, 2);
        p1.setActive(false);
        assertEquals(t.getActivePlayers().length, 1);
        p2.setActive(false);
        assertEquals(t.getActivePlayers().length, 0);
        p1.setActive(true);
        assertEquals(t.getActivePlayers().length, 1);

    }
}