package com.company.cardGame.blackjack;

import com.company.cardGame.actor.Dealer;
import com.company.cardGame.actor.Glados;
import com.company.cardGame.actor.Player;
import com.company.cardGame.deck.Deck;
import com.company.cardGame.deck.StandardDeck;
import com.company.utils.Console;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Hand> hands = new ArrayList<>();
    private List<Actor> players = new ArrayList<>();
    private Hand dealer = new Hand(new Dealer());
    private Deck deck;
    private int playerCount = 0;
    public static final int BUST_VALUE = 21;

    public Table() {
        int playerCount = Console.getInt(1,
                6,
                "How many players are playing?",
                "Invalid input");
        String AI = Console.getString("Would you like to add GLaDOS to your game? (Y/N)", false);
        for (int i = 0; i < playerCount; i++) {
            Player newPlayer = new Player("Player ", (i + 1));
            players.add(newPlayer);
            hands.add(new Hand(newPlayer));
        }
        //adding AI
        if(AI.equalsIgnoreCase("Y")) {
            Glados glados = new Glados();
            players.add(glados);
            hands.add(new Hand(glados));
        }
    }

    public void playGame() {
        while(true) {
            playRound();
        }
    }

    public void playRound() {
        deck = new StandardDeck();
//        deck = new RiggedDeck();
        deck.shuffle();
        getBets();
        deal();
        displayTable();
        playerTurns();
        while (turn(dealer));
        displayTable();
        endRound();
    }

    private void getBets() {
        for (Hand player : hands) {
            player.placeBet();
        }
    }

    private void playerTurns() {
        for (int count = 0; count < hands.size(); count++) {
            Hand player = hands.get(count);
            while (true) {
                if (!turn(player)) break;
            }
            System.out.println(player.displayHand());
            Console.getString("Enter to start next turn", false);
        }
    }

    public void endRound() {
        for (Hand player : hands) {
            determineWinner(player);
            System.out.println(player.getBalance());
        }
        while ( hands.size() > playerCount) {
            hands.remove(hands.size() - 1);
        }
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

    private void determineWinner(Hand player) {
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
            case Actor.QUIT -> quit();
            case Actor.HIT -> hit(activeHand);
            case Actor.STAND -> stand(activeHand);
            case Actor.DOUBLE -> doubleDown(activeHand);
            case Actor.SPLIT -> split(activeHand);
            default -> false;
        };
    }

    private boolean quit() {
        System.exit(0);
        return false;
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
        activeHand.doubleBet();
        Hand newHand = activeHand.splitHand();
        activeHand.addCard(deck.draw());
        newHand.addCard(deck.draw());
        hands.add(newHand);

        return true;
    }
}
