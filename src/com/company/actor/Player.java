package com.company.actor;

import com.company.blackjack.Actor;
import com.company.blackjack.Hand;
import com.company.utils.Console;

public class Player implements Actor {
    private final String name;
    private int balance = 1000;

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
    public int getBet() {
        return Console.getInt(
                1,
                balance,
                "Enter a bet value between 1 and " + balance,
                "Invalid bet"
        );
    }

    private String getAvailableActions() {
        StringBuilder output = new StringBuilder();
        output.append("0. Quit\n1. Hit\n2. Stand");
        //TODO add logic for double
        //TODO 1 - confirm first turn
        //TODO 2 - confirm has enough funds
        //TODO 3 - add logic for split detect pair
        return output.toString();
    }

    @Override
    public byte getAction(Hand hand) {
        System.out.println(hand.displayHand());
        System.out.println(hand.getValue());
        System.out.println("1 - hit \n2 - Stand \n3 - Quit");
        return (byte) Console.getInt(0,2, "", "Invalid action");
    }
}
