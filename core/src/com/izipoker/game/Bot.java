package com.izipoker.game;

/**
 * Represents a non human controlled player in poker (NOT USED)
 */
public class Bot extends Player {
    /**
     * Represents the difficulty of the bot
     */
    public enum botDifficulty {
        /**
         * Lowest difficulty level
         */
        EASY,
        /**
         * Intermediary difficulty level
         */
        MEDIUM,
        /**
         * Highest difficulty level
         */
        HARD
    }

    private botDifficulty difficulty;

    /**
     * Bot constructor (sets difficulty to MEDIUM(default))
     *
     * @param id   Bot ID
     * @param name Bot Name
     */
    Bot(int id, String name, int money) {
        super(id, name, money);
        difficulty = botDifficulty.MEDIUM;
    }

    /**
     * Bot constructor
     *
     * @param id         Bot ID
     * @param name       Bot Name
     * @param difficulty Bot difficulty
     * @see botDifficulty;
     */
    Bot(int id, String name, int money, botDifficulty difficulty) {
        super(id, name, money);
        this.difficulty = difficulty;
    }
}
