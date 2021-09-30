package com.company.blackjack;

import com.company.actor.Dealer;
import com.company.actor.Player;
import com.company.deck.Deck;
import com.company.deck.StandardDeck;
import com.company.utils.Console;

import java.util.ArrayList;
import java.util.List;

public class Table {
    // TODO: remove this item.
    Hand player = new Hand(new Player("player"));
    // TODO: try to implement multiple hands.
    List<Hand> hands = new ArrayList<>();
    // TODO: more comfortable -> try to accomplish without the players list.
    List<Actor> players = new ArrayList<>();
    Hand dealer = new Hand(new Dealer());
    Deck deck;
    int BUST_VALUE = 21;

    public void playRound() {
        deck = new StandardDeck();
        deck.shuffle();

        int playerCount = Console.getInt(1, 5, "How many players are playing?", "Invalid input");

        while(playerCount > hands.size()) {
            Hand player = new Hand(new Player(Console.getString("Please enter player name", true)));
            hands.add(player);
            System.out.println(player.getName());
        }

        for(Hand player : hands) {
            player.placeBet();
            deal();
            displayTable();

        }
//        player.placeBet();
//        deal();
//        displayTable();
//        while(turn(player)) {}
//        System.out.println(player.displayHand());
//        while (turn(dealer));
//        displayTable();
//        determineWinner();
//        System.out.println(player.getBalance());
    }

    public void displayTable() {
        StringBuilder output = new StringBuilder();
        output.append(dealer.getName() + " ").append(dealer.displayHand()).append("\n");
        output.append(player.getName() + " ").append(player.displayHand());
        System.out.println(output);
    }

    private void deal() {
        for(int count = 1; count < 2; count ++) {
            //list of hands
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
        }
    }

    private void determineWinner() {
        if (player.getValue() > BUST_VALUE) {
            System.out.println("Player Busted");
            return;
        }
        if (player.getValue() > dealer.getValue() || dealer.getValue() > BUST_VALUE) {
            System.out.println("Player Wins");
            player.payout(Hand.NORMALPAY);
            return;
        }
        if (player.getValue() == dealer.getValue()) {
            System.out.println("Push");
            player.payout(Hand.PUSHPAY);
            return;
        }
        System.out.println("Dealer Wins");
    }

    private boolean turn(Hand activeHand) {
        System.out.println(dealer.getName() + " " + dealer.displayHand());
        byte action = activeHand.getAction();
        return switch (action) {
            case Actor.QUIT -> stand(activeHand);
            case Actor.HIT -> hit(activeHand);
            case Actor.STAND -> stand(activeHand);
            case Actor.DOUBLE -> doubleDown(activeHand);
            case Actor.SPLIT -> split(activeHand);
            default -> false;
        };
    }

    private boolean hit(Hand activeHand) {
        activeHand.addCard(deck.draw());
        System.out.println("Hit");
        if (activeHand.getValue() > BUST_VALUE){
            System.out.println("Busted");
            return false;
        }
        return true;
    }

    private boolean stand(Hand activeHand) {
        System.out.println("Stand");
        return false;
    }

    private boolean doubleDown(Hand activeHand) {
        activeHand.doubleBet();
        System.out.println("Bet Doubled");
        hit(activeHand);
        return false;
    }

    private boolean split(Hand activeHand) {
        return doubleDown(activeHand);
    }
}
