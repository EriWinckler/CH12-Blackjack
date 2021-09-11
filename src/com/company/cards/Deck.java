package com.company.cards;

public class Deck extends Card{
    int size = SUITS.length * RANKS.length;
    String[] deck = new String[size];

    public Deck() {
        for (int i = 0; i < RANKS.length; i++) {
            for (int j = 0; j < SUITS.length; j++) {
                deck[SUITS.length*i + j] = RANKS[i] + " of " + SUITS[j];
            }
        }
    }

    public void shuffle(){
        for (int i = 0; i < size; i++) {
            int r = i + (int) (Math.random() * (size - i));
            String temp = deck[r];
            deck[r] = deck[i];
            deck[i] = temp;
        }
    }

    public void printDeck() {
        for (int i = 0; i < size; i++) {
            System.out.println(deck[i]);
        }
    }

}
