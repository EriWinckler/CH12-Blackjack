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
            bet = (int) Math.floor(Math.random() * balance / 2) + 1;
        }
        balance -= bet;
        return bet;
    }

    /*
    dealer menor que 16 = hit
    dealer maior que 16 = stand

    hand menor que 16 = hit
    hand maior que 16 = stand

     */

    @Override
    public byte getAction(Hand hand, Hand dealer) {
        int handValue = hand.getValue();
        int dealerValue = dealer.getValue();

        if(dealerValue >= 16 && handValue <= 16) {
            return HIT;
        }

        if(handValue >= 13) {
            return STAND;
        }

        if(dealerValue < 16) {
            return HIT;
        }

        if(dealerValue > 12 && dealerValue < 16) {
            return DOUBLE;
        }

        if(hand.canSplit()) {
            return SPLIT;
        }

        return HIT;
    }

    @Override
    public void addBalance(double amt) {
        balance += amt;
    }
}
