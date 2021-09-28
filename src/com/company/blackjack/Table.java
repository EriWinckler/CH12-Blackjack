package com.company.blackjack;

import com.company.actor.Dealer;
import com.company.actor.Player;
import com.company.deck.Deck;
import com.company.deck.StandardDeck;

public class Table {
    Hand player = new Hand(new Player("Player"));
    Hand dealer = new Hand(new Dealer());
    Deck deck;

    public void playRound() {
        deck = new StandardDeck();
        deck.shuffle();

        /*
        0. take bets
        1. deal cards
        2. see who won
         */

        deal();
        displayTable();
        determineWinner();
    }

    public String displayTable() {
        StringBuilder output = new StringBuilder();
        output.append("Dealer ").append(dealer.displayHand()).append("\n");
        output.append("Player ").append(player.displayHand());
        return output.toString();
    }

    private void deal() {
        for(int count = 1; count < 2; count ++) {
            //list of hands
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
        }
    }

    private void determineWinner() {
        if(player.getValue() > dealer.getValue()) {
            System.out.println("Player Wins");
            return;
        }
        if(player.getValue() == dealer.getValue()) {
            System.out.println("Push");
            return;
        }
        System.out.println("Dealer Wins");
    }

    private void turn(Hand activeHand) {
        System.out.println("Dealer: " + dealer.displayHand());
        System.out.println("Player: " + activeHand.displayHand());
        byte action = activeHand.getAction();
        switch (action) {
            case 0 -> System.out.println("Quitting");
            case 1 -> System.out.println("Hit me");
            case 2 -> System.out.println("Waves Hand");
            case 3 -> System.out.println("Double Down");
            case 4 -> System.out.println("Two Hands");
            default -> System.out.println("Error bad action " + action);
        }
    }

    private void hit(Hand activeHand) {}

    private void stand(Hand activehand) {}

    private void doubleDown(Hand activeHand) {}

    private void split(Hand activeHand) {
        doubleDown(activeHand);
    }
}
