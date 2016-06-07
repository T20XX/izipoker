package com.izipoker.test;

import com.izipoker.cardGame.Card;
import com.izipoker.game.Hand;

import org.junit.Test;

import javafx.util.Pair;

import static org.junit.Assert.assertEquals;

public class HandRankTest {

    @Test
    public void testRoyalStraightFlush() {
        Hand h = new Hand(new Card(Card.rankType.ACE, Card.suitType.HEARTS), new Card(Card.rankType.QUEEN, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.TWO, Card.suitType.CLUBS),
                new Card(Card.rankType.KING, Card.suitType.HEARTS),
                new Card(Card.rankType.FOUR, Card.suitType.SPADES),
                new Card(Card.rankType.TEN, Card.suitType.HEARTS),
                new Card(Card.rankType.JACK, Card.suitType.HEARTS),
        };

        assertEquals(new Pair(Hand.handRank.ROYAL_FLUSH,Card.rankType.ACE), h.checkHandRank(cardsOnTable));

    }

    @Test
    public void testStraightFlush() {
        Hand h = new Hand(new Card(Card.rankType.FOUR, Card.suitType.SPADES), new Card(Card.rankType.FIVE, Card.suitType.SPADES));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.TWO, Card.suitType.HEARTS),
                new Card(Card.rankType.THREE, Card.suitType.SPADES),
                new Card(Card.rankType.TWO, Card.suitType.SPADES),
                new Card(Card.rankType.TEN, Card.suitType.HEARTS),
                new Card(Card.rankType.SIX, Card.suitType.SPADES),
        };

        assertEquals(new Pair(Hand.handRank.STRAIGHT_FLUSH,Card.rankType.SIX), h.checkHandRank(cardsOnTable));
    }

    @Test
    public void testFullHouse() {
        Hand h = new Hand(new Card(Card.rankType.ACE, Card.suitType.SPADES), new Card(Card.rankType.ACE, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.TWO, Card.suitType.HEARTS),
                new Card(Card.rankType.TEN, Card.suitType.SPADES),
                new Card(Card.rankType.TWO, Card.suitType.SPADES),
                new Card(Card.rankType.TEN, Card.suitType.HEARTS),
                new Card(Card.rankType.ACE, Card.suitType.CLUBS),
        };
        assertEquals(new Pair(Hand.handRank.FULL_HOUSE,Card.rankType.ACE), h.checkHandRank(cardsOnTable));
    }

    @Test
    public void testFlush() {
        Hand h = new Hand(new Card(Card.rankType.ACE, Card.suitType.SPADES), new Card(Card.rankType.FIVE, Card.suitType.SPADES));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.TWO, Card.suitType.HEARTS),
                new Card(Card.rankType.THREE, Card.suitType.SPADES),
                new Card(Card.rankType.TWO, Card.suitType.SPADES),
                new Card(Card.rankType.TEN, Card.suitType.HEARTS),
                new Card(Card.rankType.SIX, Card.suitType.SPADES),
        };
        assertEquals(new Pair(Hand.handRank.FLUSH,Card.rankType.ACE), h.checkHandRank(cardsOnTable));
    }

    @Test
    public void testStraight() {
        Hand h = new Hand(new Card(Card.rankType.NINE, Card.suitType.CLUBS), new Card(Card.rankType.TEN, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.SEVEN, Card.suitType.HEARTS),
                new Card(Card.rankType.EIGHT, Card.suitType.SPADES),
                new Card(Card.rankType.SIX, Card.suitType.SPADES),
                new Card(Card.rankType.TEN, Card.suitType.HEARTS),
                new Card(Card.rankType.SIX, Card.suitType.SPADES),
        };
        assertEquals(new Pair(Hand.handRank.STRAIGHT,Card.rankType.TEN), h.checkHandRank(cardsOnTable));
    }


    @Test
    public void testThreeOfAKind() {
        Hand h = new Hand(new Card(Card.rankType.THREE, Card.suitType.SPADES), new Card(Card.rankType.TEN, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.SEVEN, Card.suitType.HEARTS),
                new Card(Card.rankType.TEN, Card.suitType.SPADES),
                new Card(Card.rankType.TWO, Card.suitType.SPADES),
                new Card(Card.rankType.TEN, Card.suitType.HEARTS),
                new Card(Card.rankType.ACE, Card.suitType.CLUBS),
        };
        assertEquals(new Pair(Hand.handRank.THREE_OF_A_KIND,Card.rankType.TEN), h.checkHandRank(cardsOnTable));
    }

    @Test
    public void testTwoPair() {
        Hand h = new Hand(new Card(Card.rankType.THREE, Card.suitType.SPADES), new Card(Card.rankType.TEN, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.SEVEN, Card.suitType.HEARTS),
                new Card(Card.rankType.TEN, Card.suitType.SPADES),
                new Card(Card.rankType.TWO, Card.suitType.SPADES),
                new Card(Card.rankType.THREE, Card.suitType.HEARTS),
                new Card(Card.rankType.ACE, Card.suitType.CLUBS),
        };
        assertEquals(new Pair(Hand.handRank.TWO_PAIR,Card.rankType.TEN), h.checkHandRank(cardsOnTable));
    }

    @Test
    public void testPair() {
        Hand h = new Hand(new Card(Card.rankType.THREE, Card.suitType.SPADES), new Card(Card.rankType.TEN, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.FIVE, Card.suitType.HEARTS),
                new Card(Card.rankType.SEVEN, Card.suitType.SPADES),
                new Card(Card.rankType.FOUR, Card.suitType.SPADES),
                new Card(Card.rankType.THREE, Card.suitType.HEARTS),
                new Card(Card.rankType.ACE, Card.suitType.CLUBS),
        };
        assertEquals(new Pair(Hand.handRank.PAIR,Card.rankType.THREE), h.checkHandRank(cardsOnTable));
    }

    @Test
    public void testHighCard() {
        Hand h = new Hand(new Card(Card.rankType.JACK, Card.suitType.SPADES), new Card(Card.rankType.TEN, Card.suitType.HEARTS));
        Card[] cardsOnTable = new Card[]{new Card(Card.rankType.FIVE, Card.suitType.HEARTS),
                new Card(Card.rankType.SEVEN, Card.suitType.SPADES),
                new Card(Card.rankType.FOUR, Card.suitType.SPADES),
                new Card(Card.rankType.THREE, Card.suitType.HEARTS),
                new Card(Card.rankType.ACE, Card.suitType.CLUBS),
        };
        assertEquals(new Pair(Hand.handRank.HIGH_CARD,Card.rankType.ACE), h.checkHandRank(cardsOnTable));
    }

}
