package com.izipoker.test;

import com.izipoker.game.Dealer;
import com.izipoker.game.Human;
import com.izipoker.game.Player;
import com.izipoker.game.Round;
import com.izipoker.game.Table;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jorge on 5/10/2016.
 */
public class DealerTest {
    /*Table t = new Table(8);
    Dealer d = t.getDealer();
    Player p = new Human(0,"Teste",1000);
    Player p1 = new Human(0,"Teste1",1000);
    {
        t.addPlayer(p);
        t.addPlayer(p1);
    }*/
    @Test
    public void createRound() {
        Table t = new Table("teste", 8);
        Dealer d = t.getDealer();
        Player p = new Human(0, "Teste", 1000);
        t.addPlayer(p);
        assertEquals(0, t.getRounds().size());
        assertFalse(d.createRound());
        p = new Human(0, "Teste", 1000);
        t.addPlayer(p);
        p = new Human(0, "Teste1", 1000);
        t.addPlayer(p);
        assertTrue(d.createRound());
        assertEquals(1, t.getRounds().size());
    }

    @Test
    public void setNewJoker() {
        Table t = new Table("teste", 8);
        Dealer d = t.getDealer();
        assertEquals(null, t.getJoker());
        Player p = new Human(0, "Teste", 1000);
        Player p1 = new Human(0, "Teste1", 1000);
        t.addPlayer(p);
        t.addPlayer(p1);
        assertEquals(p, t.getJoker());
        d.setNewJoker();
        assertEquals(p1, t.getJoker());
    }

    @Test
    public void giveHands() {
        Table t = new Table("teste", 8);
        Dealer d = t.getDealer();
        Player p = new Human(0, "Teste", 1000);
        Player p1 = new Human(0, "Teste1", 1000);
        t.addPlayer(p);
        t.addPlayer(p1);
        d.createRound();
        assertNull(p.getHand());
        assertNull(p1.getHand());
        d.giveHands();
        assertNotNull(p.getHand());
        assertNotNull(p1.getHand());
    }

    @Test
    public void showFlop() {
        Table t = new Table("teste", 8);
        Dealer d = t.getDealer();
        Player p = new Human(0, "Teste", 1000);
        Player p1 = new Human(0, "Teste1", 1000);
        t.addPlayer(p);
        t.addPlayer(p1);
        d.createRound();
        Round r = t.getTopRound();
        assertNull(r.getFlop());
        d.showFlop();
        assertNotNull(r.getFlop());
        assertEquals(3, r.getFlop().length);
    }

    @Test
    public void showTurn() {
        Table t = new Table("teste", 8);
        Dealer d = t.getDealer();
        Player p = new Human(0, "Teste", 1000);
        Player p1 = new Human(0, "Teste1", 1000);
        t.addPlayer(p);
        t.addPlayer(p1);
        d.createRound();
        Round r = t.getTopRound();
        assertNull(r.getTurn());
        d.showTurn();
        assertNotNull(r.getTurn());
    }

    @Test
    public void showRiver() {
        Table t = new Table("teste", 8);
        Dealer d = t.getDealer();
        Player p = new Human(0, "Teste", 1000);
        Player p1 = new Human(0, "Teste1", 1000);
        t.addPlayer(p);
        t.addPlayer(p1);
        d.createRound();
        Round r = t.getTopRound();
        assertNull(r.getRiver());
        d.showRiver();
        assertNotNull(r.getRiver());
    }
}
