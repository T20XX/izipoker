package com.izipoker.game;

import com.izipoker.cardGame.Card;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import javafx.util.Pair;

/**
 * Represents a hand in a poker game (2 cards)
 */
public class Hand implements Serializable {

    /**
     * Represents the possible ranks of a hand in the final of a round
     */
    public enum handRank {
        /**
         * Highest card
         */
        HIGH_CARD,
        /**
         * Two cards of the same rank
         */
        PAIR,
        /**
         * Two Pairs
         */
        TWO_PAIR,
        /**
         * Three cards of the same rank
         */
        THREE_OF_A_KIND,
        /**
         * Five cards of sequential rank and different suit
         */
        STRAIGHT,
        /**
         * Five cards of the same suit
         */
        FLUSH,
        /**
         * Three of a kind plus a a pair
         */
        FULL_HOUSE,
        /**
         * Four cards of the same rank
         */
        FOUR_OF_A_KIND,
        /**
         * Straight of the same suit
         */
        STRAIGHT_FLUSH,
        /**
         * Straight of the same suit with A, K, J, Q, 10
         */
        ROYAL_FLUSH
        }

    /**
     * Number of cards in a hand
     */
    public final static int HAND_SIZE = 2;

    // need to indicate the this on both sides (server and client) to make sure objects are compatible.
    private static final long serialVersionUID = 1L;
    private Card cards[];

    /**
     * Hand constructor, creates new Hand given two cards
     *
     * @param c1 First card of hand
     * @param c2 Second card of hand
     */
    public Hand(Card c1, Card c2) {
        cards = new Card[2];
        cards[0] = c1;
        cards[1] = c2;
    }

    /**
     * Hand constructor, creates new Hand given an array of size 2
     *
     * @param cards Array of size 2 containing the two cards of hand
     */
    public Hand(Card cards[]) {
        if (cards.length != HAND_SIZE) {
            this.cards = cards;
        } else {
            throw new IllegalArgumentException("Array passed by argument must be of size 2");
        }
    }

    /**
     * Checks handRank given the 5 cards on the table
     * @param cardsOnTable 5 Cards from the flop, turn and river
     * @return A pair containing the handRank and the highest card related to the handRank
     */
    public Pair<handRank, Card.rankType> checkHandRank(Card[] cardsOnTable) {
        /*List<Card> list = new ArrayList<Card>(Arrays.asList(cards));
        list.addAll(Arrays.asList(cardsOnTable));
        Object[] totalCards =  list.toArray();
        */
        Card[] totalCards = new Card[cards.length + cardsOnTable.length];
        System.arraycopy(cards, 0, totalCards, 0, cards.length);
        System.arraycopy(cardsOnTable, 0, totalCards, cards.length, cardsOnTable.length);

        Card.rankType rank;


        if (containsRoyalFlush(totalCards)) {
            return new Pair(handRank.ROYAL_FLUSH, Card.rankType.ACE);
        } else if ((rank = containsStraightFlush(totalCards)) != null) {
            return new Pair(handRank.STRAIGHT_FLUSH, rank);
        } else if ((rank = containsFourOfAKind(totalCards)) != null) {
            return new Pair(handRank.FOUR_OF_A_KIND, rank);
        } else if ((rank = containsFullHouse(totalCards)) != null) {
            return new Pair(handRank.FULL_HOUSE, rank);
        } else if ((rank = containsFlush(totalCards)) != null) {
            return new Pair(handRank.FLUSH, rank);
        } else if ((rank = containsStraight(totalCards)) != null) {
            return new Pair(handRank.STRAIGHT, rank);
        } else if ((rank = containsThreeOfAKind(totalCards)) != null) {
            return new Pair(handRank.THREE_OF_A_KIND, rank);
        } else if ((rank = containsTwoPair(totalCards)) != null) {
            return new Pair(handRank.TWO_PAIR, rank);
        } else if ((rank = containsPair(totalCards)) != null) {
            return new Pair(handRank.PAIR, rank);
        } else {
            return new Pair(handRank.HIGH_CARD, getHighCard(totalCards));
        }
    }

    private boolean containsRoyalFlush(Card[] cards) {
        boolean contains = false;
        Arrays.sort(cards);
        for (int i = 0; i < cards.length - 4; i++) {
            if (cards[i].getRank() == Card.rankType.ACE) {
                if (cards[i + 4].getSuit() == cards[i].getSuit() && cards[i + 4].getRank() == Card.rankType.TEN) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    private Card.rankType containsStraightFlush(Card[] cards) {
        boolean contains = false;
        int i;
        Arrays.sort(cards);
        for (i = 0; i < cards.length - 4; i++) {
            if (cards[i + 4].getSuit() == cards[i].getSuit() && cards[i + 4].getValue() == cards[i].getValue() - 4) {
                contains = true;
                break;
            }
        }
        if (contains) {
            return cards[i].getRank();
        } else {
            return null;
        }
    }

    private Card.rankType containsFourOfAKind(Card[] cards) {
        HashMap<Card.rankType, Integer> counterMap = countEqualsCards(cards);

        if (counterMap.containsValue(4)) {
            for (Card.rankType rank : Card.rankType.values()) {
                if (counterMap.get(rank) == 4) {
                    return rank;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private Card.rankType containsFullHouse(Card[] cards) {
        Card.rankType highRank = null;
        HashMap<Card.rankType, Integer> counterMap = countEqualsCards(cards);

        if (counterMap.containsValue(3) && counterMap.containsValue(2)) {
            for (Card.rankType rank : Card.rankType.values()) {
                if (counterMap.containsKey(rank)) {
                    if (counterMap.get(rank) == 3) {
                        highRank = rank;
                    }
                }
            }
        }
        return highRank;
    }

    private Card.rankType containsFlush(Card[] cards) {
        boolean contains = false;
        int i;
        Arrays.sort(cards);
        for (i = 0; i < cards.length - 4; i++) {
            if (cards[i + 4].getSuit() == cards[i].getSuit()) {
                contains = true;
                break;
            }
        }
        if (contains) {
            return cards[i].getRank();
        } else {
            return null;
        }
    }

    private Card.rankType containsStraight(Card[] cards) {
        HashMap<Card.rankType, Integer> counterMap = countEqualsCards(cards);

        Card.rankType highRank = null;
        for (Card.rankType rank : Card.rankType.values()) {
            if (rank != Card.rankType.JACK && rank != Card.rankType.QUEEN && rank != Card.rankType.KING && rank != Card.rankType.ACE) {
                if (counterMap.containsKey(Card.rankType.values()[Card.rankType.valueOf(rank.toString()).ordinal()]) &&
                        counterMap.containsKey(Card.rankType.values()[Card.rankType.valueOf(rank.toString()).ordinal() + 1]) &&
                        counterMap.containsKey(Card.rankType.values()[Card.rankType.valueOf(rank.toString()).ordinal() + 2]) &&
                        counterMap.containsKey(Card.rankType.values()[Card.rankType.valueOf(rank.toString()).ordinal() + 3]) &&
                        counterMap.containsKey(Card.rankType.values()[Card.rankType.valueOf(rank.toString()).ordinal() + 4])) {
                    highRank = Card.rankType.values()[Card.rankType.valueOf(rank.toString()).ordinal() + 4];
                }
            }
        }
        return highRank;
    }


    private Card.rankType containsThreeOfAKind(Card[] cards) {
        Card.rankType highRank = null;
        HashMap<Card.rankType, Integer> counterMap = countEqualsCards(cards);

        if (counterMap.containsValue(3)) {
            for (Card.rankType rank : Card.rankType.values()) {
                if (counterMap.containsKey(rank)) {
                    if (counterMap.get(rank) == 3) {
                        highRank = rank;
                    }
                }
            }
        }
        return highRank;
    }

    private Card.rankType containsTwoPair(Card[] cards) {
        HashMap<Card.rankType, Integer> counterMap = countEqualsCards(cards);

        if (counterMap.containsValue(2)) {
            int pairCounter = 0;
            for (Card.rankType rank : Card.rankType.values()) {
                if (counterMap.containsKey(rank)) {
                    if (counterMap.get(rank) == 2) {
                        pairCounter++;
                        if (pairCounter == 2) {
                            return rank;
                        }
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private Card.rankType containsPair(Card[] cards) {
        Card.rankType highRank = null;
        HashMap<Card.rankType, Integer> counterMap = countEqualsCards(cards);

        if (counterMap.containsValue(2)) {
            for (Card.rankType rank : Card.rankType.values()) {
                if (counterMap.containsKey(rank)) {
                    if (counterMap.get(rank) == 2) {
                        highRank = rank;
                    }
                }
            }
        }
        return highRank;
    }

    private Card.rankType getHighCard(Card[] cards) {
        Card highCard = cards[0];
        Arrays.sort(cards);
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].getValue() > highCard.getValue()) {
                highCard = cards[i];
            }
        }

        return highCard.getRank();
    }

    private HashMap<Card.rankType, Integer> countEqualsCards(Card[] cards) {
        HashMap<Card.rankType, Integer> counterMap = new HashMap<Card.rankType, Integer>();
        for (int i = 0; i < cards.length; i++) {
            if (counterMap.containsKey(cards[i].getRank())) {
                counterMap.put(cards[i].getRank(), counterMap.get(cards[i].getRank()) + 1);
            } else {
                counterMap.put(cards[i].getRank(), 1);
            }
        }
        return counterMap;
    }

    /**
     * Get the two cards of an hand
     * @return An array of size 2 containing the cards
     */
    public Card[] getCards() {
        return cards;
    }
}
