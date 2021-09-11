package com.company;

import com.company.cards.Deck;

public class Main {

    public static void main(String[] args) {

        Deck deck = new Deck();

        deck.shuffle();
        deck.printDeck();
    }
}
