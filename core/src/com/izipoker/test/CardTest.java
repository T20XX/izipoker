package com.izipoker.test;

import com.izipoker.cardGame.Card;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Telmo on 10/05/2016.
 */
public class CardTest {

    @Test
    public void testCardbyRank(){

        Card c1 = new Card(Card.rankType.ACE, Card.suitType.SPADES);
        assertEquals(Card.rankType.ACE, c1.getRank());
        assertEquals(Card.suitType.SPADES, c1.getSuit());
        assertEquals(13, c1.getValue());
    }

    @Test
    public void testCardbyValue(){

        Card c2 = new Card(1, Card.suitType.DIAMONDS);
        assertEquals(Card.rankType.TWO, c2.getRank());
        assertEquals(Card.suitType.DIAMONDS, c2.getSuit());
        assertEquals(1, c2.getValue());
    }

    @Test
    public void testFlipping(){
        Card c1 = new Card(Card.rankType.ACE, Card.suitType.SPADES);

        assertEquals(false, c1.isFlipped());
        c1.flip();
        assertEquals(true, c1.isFlipped());
        c1.setFlipped(false);
        assertEquals(false, c1.isFlipped());
    }
}
