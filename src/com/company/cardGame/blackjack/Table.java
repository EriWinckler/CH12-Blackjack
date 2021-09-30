package com.company.cardGame.blackjack;

import com.company.cardGame.actor.Dealer;
import com.company.cardGame.actor.Player;
import com.company.cardGame.deck.Deck;
import com.company.cardGame.deck.StandardDeck;

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
        if(player.getValue() > 21) {
            System.out.println("Player Busted");
            return;
        }
        if(player.getValue() > dealer.getValue() || dealer.getValue() > 21) {
            System.out.println("Player Wins");
            return;
        }
        if(player.getValue() == dealer.getValue()) {
            System.out.println("Push");
            return;
        }
        System.out.println("Dealer Wins");
    }

    private boolean turn(Hand activeHand) {
            System.out.println("Dealer: " + dealer.displayHand());
            System.out.println("Player: " + activeHand.displayHand());
            byte action = activeHand.getAction();
            switch (action) {
                case Actor.QUIT -> stand(activeHand); // for now treated like a
                // stand
                case Actor.HIT -> hit(activeHand);
                case Actor.STAND -> stand(activeHand);
                case Actor.DOUBLE -> doubleDown(activeHand);
                case Actor.SPLIT -> split(activeHand);
                default -> {
                    System.out.println("Error bad action " + action);
                    return true;
                }
            }
            if(activeHand.getValue() > 21) {
                System.out.println("Busted");
                return true;
            }
            return false;
    }

    private void hit(Hand activeHand) {
        activeHand.addCard(deck.draw());
        System.out.println("Hit");
    }

    private void stand(Hand activehand) {
        System.out.println("Stand");
    }

    private void doubleDown(Hand activeHand) {
        activeHand.doubleBet();
        System.out.println("Bet Doubled");
        hit(activeHand);

    }

    private void split(Hand activeHand) {
        doubleDown(activeHand);
    }
}
