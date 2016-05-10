package com.izipoker.test;

import com.izipoker.cardGame.Card;
import com.izipoker.cardGame.Card.rankType;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 * Created by Telmo on 10/05/2016.
 */
public class CardTest {
    @Test
    public void testCardbyRank(){
        Card c = new Card(rankType.ACE, Card.suitType.SPADES);
        assertEquals(Card.rankType.ACE, c.getRank());
        assertEquals(Card.suitType.SPADES, c.getSuit());
        assertEquals(13, c.getValue());
    }
}
