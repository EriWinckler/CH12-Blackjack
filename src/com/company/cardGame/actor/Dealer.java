package com.company.cardGame.actor;

import com.company.cardGame.blackjack.Actor;
import com.company.cardGame.blackjack.Hand;

public class Dealer implements Actor {
    int STAND_VALUE = 17;

    public String getName() {
        return "John Wick";
    }

    @Override
    public int getBalance() {
        return 0;
    }

    @Override
    public int placeBet() {
        return 0;
    }

    @Override
    public void addBalance(double amt) { return; }

    @Override
    public byte getAction(Hand hand, Hand dealer) {
        return hand.getValue() < STAND_VALUE ? HIT : DOUBLE;
    }
}
