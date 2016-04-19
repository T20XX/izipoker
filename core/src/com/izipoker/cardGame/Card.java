package cardGame;

import java.util.*;

/**
 * Created by up201405840 on 12-04-2016.
 */
public class Card {

    int main(){
        Card c1 = new Card(1,suitType.DIAMONDS);
        System.out.println(c1.getValue());
        System.out.println(c1.getRank().toString());

        Card c2 = new Card(rankType.ACE,suitType.DIAMONDS);
        System.out.println(c2.getValue());
        System.out.println(c2.getRank().toString());

        return 0;
    }

    public enum rankType{
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    public enum suitType{
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }

    private rankType rank;

    private suitType suit;
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

    public Card(rankType r, suitType s){
        rank = r;
        suit = s;
    }

    public Card(int value, suitType s){
        suit = s;
        rank = rankType.values()[value - 1];
    }

    public rankType getRank() {
        return rank;
    }

    public void setRank(rankType rank) {
        this.rank = rank;
    }

    public int getValue(){
        return rankType.valueOf(rank.toString()).ordinal() + 1;
        //descricao
        //jogos parecidos
        //frameworks
        //complicometro
        //descricao do mvp ( minimo produto )
    }

    public suitType getSuit() {
        return suit;
    }

    public void setSuit(suitType suit) {
        this.suit = suit;
    }

}
