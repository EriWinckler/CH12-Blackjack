package com.company.actor;

import com.company.blackjack.Actor;
import com.company.blackjack.Hand;

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
    public void addBalance(int amt) { return; }

    @Override
    public byte getAction(Hand hand) {
        return hand.getValue() < STAND_VALUE ? HIT : STAND;
    }
}
