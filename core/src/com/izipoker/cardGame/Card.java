package com.izipoker.cardGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by up201405840 on 12-04-2016.
 */
public class Card extends Actor{

    private static Texture cardsTex =  new Texture("cards.png");

    private static Texture backTex = new Texture("backCard.png");

    private TextureRegion frontTex;

    /*int main(){
        Card c1 = new Card(1,suitType.DIAMONDS);
        System.out.println(c1.getValue());
        System.out.println(c1.getRank().toString());

        Card c2 = new Card(rankType.ACE,suitType.DIAMONDS);
        System.out.println(c2.getValue());
        System.out.println(c2.getRank().toString());

        return 0;
    }*/

    /**
     * Rank of cards
     */
    public enum rankType{
        /**
         * Card 2 (value: 1)
         */
        TWO,
        /**
         * Card 3 (value: 2)
         */
        THREE,
        /**
         * Card 4 (value: 3)
         */
        FOUR,
        /**
         * Card 5 (value: 4)
         */
        FIVE,
        /**
         * Card 6 (value: 5)
         */
        SIX,
        /**
         * Card 7 (value: 6)
         */
        SEVEN,
        /**
         * Card 8 (value: 7)
         */
        EIGHT,
        /**
         * Card 9 (value: 8)
         */
        NINE,
        /**
         * Card 10 (value: 9)
         */
        TEN,
        /**
         * Card J (value: 10)
         */
        JACK,
        /**
         * Card Q (value: 11)
         */
        QUEEN,
        /**
         * Card K (value: 12)
         */
        KING,
        /**
         * Card A (value: 13)
         */
        ACE
    }

    /**
     * Suit of cards
     */
    public enum suitType{
        /**
         * Hearts suit
         */
        HEARTS,
        /**
         * Diamonds suit
         */
        DIAMONDS,
        /**
         * Clubs suit
         */
        CLUBS,
        /**
         * Spades suit
         */
        SPADES
    }

    /**
     * Rank of the card
     */
    private rankType rank;

    /**
     * Suit of the card
     */
    private suitType suit;

    private boolean flipped = false;

    /*private static final Map<rankType, Integer> valueMap;
    static
    {
        valueMap = new HashMap<rankType, Integer>();
        valueMap.put(rankType.TWO, 1);
        valueMap.put(rankType.THREE, 2);
        valueMap.put(rankType.FOUR, 3);
        valueMap.put(rankType.FIVE, 4);
        valueMap.put(rankType.SIX, 5);
        valueMap.put(rankType.SEVEN, 6);
        valueMap.put(rankType.EIGHT, 7);
        valueMap.put(rankType.NINE, 8);
        valueMap.put(rankType.TEN, 9);
        valueMap.put(rankType.JACK, 10);
        valueMap.put(rankType.QUEEN, 11);
        valueMap.put(rankType.KING, 12);
        valueMap.put(rankType.ACE, 13);
    }*/

    /**
     * Creates a card by giving a rank and a suit
     * @param r Rank of the card
     * @param s Suit of the card
     */
    public Card(rankType r, suitType s){
        rank = r;
        suit = s;
        getFrontTexFromCards();
    }

    /**
     * Creates a card by giving a value and a suit
     * @param value Value of the card
     * @param s Suit of the card
     * @see rankType To check rank and value match
     */
    public Card(int value, suitType s){
        suit = s;
        rank = rankType.values()[value - 1];
        getFrontTexFromCards();
    }

    private void getFrontTexFromCards() {
        frontTex = new TextureRegion(cardsTex,
                cardsTex.getWidth()/13 * (rankType.valueOf(this.rank.toString()).ordinal()),
                cardsTex.getHeight()/4 * suitType.valueOf(this.suit.toString()).ordinal(),
                cardsTex.getWidth()/13,
                cardsTex.getHeight()/4);
    }

    /**
     * @return Rank of the card
     */
    public rankType getRank() {
        return rank;
    }

    /**
     * Sets the rank of the card
     * @param rank New rank to set
     */
    public void setRank(rankType rank) {
        this.rank = rank;
    }

    /**
     * @return Value of the card from 1 to 13
     * @see rankType To check rank and value match
     */
    public int getValue(){
        return rankType.valueOf(rank.toString()).ordinal() + 1;
    }

    /**
     * @return Suit of the card
     */
    public suitType getSuit() {
        return suit;
    }

    /**
     * Sets the suit of the card
     * @param suit New suit to set
     */
    public void setSuit(suitType suit) {
        this.suit = suit;
    }


    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void flip() {
        this.flipped = !this.flipped;
    }

    /**
     * Used for debugging purposes
     * @return A string composed by the rank followed by its suit
     */
    @Override
    public String toString(){
        return this.rank.toString() + " of " + this.suit.toString();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isFlipped()){
            batch.draw(frontTex, super.getX(), super.getY(), super.getWidth(), super.getHeight());
        } else {
            batch.draw(backTex, super.getX(), super.getY(), super.getWidth(), super.getHeight());
        }
    }

    public static Texture getCardsTex() {
        return cardsTex;
    }

    public static void setCardsTex(Texture cardsTex) {
        Card.cardsTex = cardsTex;
    }
}
