package com.company.cardGame.blackjack;

import com.company.cardGame.actor.Dealer;
import com.company.cardGame.actor.Player;
import com.company.cardGame.deck.Deck;
import com.company.cardGame.deck.StandardDeck;
import com.company.utils.Console;

import java.util.ArrayList;
import java.util.List;

public class Table {
    // TODO: remove this item.
    private Hand player = new Hand(new Player("player"));
    // TODO: try to implement multiple hands.
    private List<Hand> hands = new ArrayList<>();
    // TODO: more comfortable -> try to accomplish without the players list.
    private List<Actor> players = new ArrayList<>();
    private Hand dealer = new Hand(new Dealer());
    private Deck deck;
    public static final int BUST_VALUE = 21;

    public Table() {
        int playerCount = Console.getInt(1,
                6,
                "How many players are playing?",
                "Invalid input");
        for (int i = 0; i < playerCount; i++) {
            Player newPlayer = new Player("Player ", (i + 1));
            players.add(newPlayer);
            hands.add(new Hand(newPlayer));
        }
    }

    public void playRound() {
        deck = new StandardDeck();
        deck.shuffle();
        for(Hand player : hands) {
            player.placeBet();
        }
        deal();
        displayTable();
        for(Hand player : hands) {
            while(true) {
                if(turn(player)) break;
            }
            System.out.println(player.displayHand());
            Console.getString("Enter to start the next turn", false);
        }


//        int playerCount = Console.getInt(1, 5, "How many players are playing?", "Invalid input");
//
//        while(playerCount > hands.size()) {
//            Hand player = new Hand(new Player(Console.getString("Please enter player name", true)));
//            hands.add(player);
//        }
//
//        for(Hand player : hands) {
//            player.placeBet();
//            deal();
//            displayTable();
//            while(turn(player)) {}
//            System.out.println(player.displayHand());
//            while (turn(dealer));
//            displayTable();
//            determineWinner();
//            System.out.println(player.getBalance());
//        }
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
        for(Hand player : hands) {
            output.append(player.getName() + " ").append(player.displayHand()).append(" | ");
        }
        System.out.println(output);
    }

    private void deal() {
        for(int count = 1; count < 2; count ++) {
            //list of hands
            for (Hand player : hands) {
                player.addCard(deck.draw());
            }
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
