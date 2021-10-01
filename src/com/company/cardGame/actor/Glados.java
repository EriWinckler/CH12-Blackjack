package com.company.cardGame.actor;

import com.company.cardGame.blackjack.Actor;
import com.company.cardGame.blackjack.Hand;

public class Glados implements Actor {
    private final String name = "GLaDOS";
    private int balance = 1000;

    @Override
    public String getName() { return name; }

    @Override
    public int getBalance() { return balance; }

    @Override
    public int placeBet() {
        int bet = 0;
        if(balance < 700) {
            bet = 300;
        }
        if(balance < 300) {
            bet = 100;
        }
        if(balance < 100) {
            bet = 50;
        }
        if(balance < 50) {
            bet = 10;
        }
        if(balance < 10) {
            bet = 1;
        }
        balance -= bet;
        return bet;
    }

    @Override
    public byte getAction(Hand hand, Hand dealer) {
        return 0;
    }

    @Override
    public void addBalance(double amt) {
        balance += amt;
    }
}
