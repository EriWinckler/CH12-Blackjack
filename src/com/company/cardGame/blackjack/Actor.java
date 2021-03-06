package com.company.cardGame.blackjack;

public interface Actor {
    byte HIT = 1;
    byte STAND = 2;
    byte DOUBLE = 3;
    byte SPLIT = 4;
    byte QUIT  = 0;

    String getName();
    int getBalance();
    int placeBet();
    byte getAction(Hand hand, int dealerRank);
    void addBalance(double amt);
    //byte getAction(Hand hand, Lists<Cards> cards) -> card counter version
}
