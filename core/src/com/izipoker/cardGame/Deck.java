package com.izipoker.cardGame;

import java.util.Random;

/**
 * Represents a deck of 52 different cards, can be used in any card game implementation
 */
public class Deck {

    /**
     * Cards from the deck
     */
    private Card cards[] = new Card[52];
    private int index = 0;

    /**
     * Creates a deck with 52 cards, 13 from each suit
     */
    public Deck(){
        //intializes with 52 cards
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                cards[i*13 + j] = new Card(j+1, Card.suitType.values()[i]);
            }
        }
    }

    /**
     * Shuffles the deck by placing cards randomly in the stack
     * @param n Number of deck shuffling (simulating real life shuffling)
     */
    public void shuffle(int n){
        resetDeck();
        for (int i = 0; i < n; i++){
            Random rnd = new Random();
            for (int j = cards.length - 1; j > 0; j--)
            {
                int index = rnd.nextInt(j + 1);
                // Simple swap
                Card tmp = cards[index];
                cards[index] = cards[j];
                cards[j] = tmp;
            }
        }
    }

    /**
     * @return Vector of the 52 cards that compose a deck
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * Get the card on the top of the deck
     * @return Card on top of the deck
     */
    public Card getTopCard() {
        if (index < this.cards.length) {
            index++;
            return this.cards[index - 1];
        } else {
            return null;
        }
    }

    public void resetDeck() {
        for (int i = 0; i < index; i++)
        {
            this.cards[i].setFlipped(false);
        }
        index = 0;
    }

    /**
     * Used for debugging purposes
     * @return A string with all 52 cards, one per line
     */
    @Override
    public String toString(){
        String tmp = new String();
        for (int i = 0; i < cards.length; i++) {
            tmp += cards[i] + "\n";
        }
        return tmp;
    }
}
