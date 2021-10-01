package com.company.cardGame.actor;

import com.company.cardGame.blackjack.Actor;
import com.company.cardGame.blackjack.Hand;
import com.company.utils.Console;

public class Player implements Actor {
    private final String name;
    private int balance = 1000;
    private int actionsCount;

    public Player(String name) {
        this.name = name;
    }

    //Overload
    public Player (String name, int startingBalance) {
        this.name = name;
        balance = startingBalance;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getBalance() {
        return 0;
    }

    @Override
    public int placeBet() {
        int bet = Console.getInt(
                1,
                balance,
                "Enter a bet value between 1 and " + balance,
                "Invalid bet"
        );
        balance -= bet;
        return bet;
    }

    private String getAvailableActions(Hand hand) {
        actionsCount = 2;
        StringBuilder output = new StringBuilder();
        output.append("0. Quit\n1. Hit\n2. Stand");
        if(hand.size() == 2 && balance >= hand.getBet()) {
            output.append("\n3. Double");
            actionsCount++;
            if(hand.canSplit()) {
                output.append("\n4. Split");
                actionsCount++;
            }
        }
        return output.toString();
    }

    @Override
    public byte getAction(Hand hand, Hand dealer) {
        System.out.println(hand.displayHand());
        System.out.println(hand.getValue());
        System.out.println("1 - hit \n2 - Stand \n3 - Quit");
        return (byte) Console.getInt(0,2, "", "Invalid action");
    }

    @Override
    public void addBalance(double amt) {
        balance += amt;
    }
}
