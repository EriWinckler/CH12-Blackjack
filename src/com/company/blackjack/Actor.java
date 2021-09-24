package com.company.blackjack;

import com.company.blackjack.Hand;

public interface Actor {
    byte HIT = 1;
    byte STAND = 2;
    byte DOUBLE = 3;
    byte SPLIT = 4;
    byte QUIT  = 0;

    String getName();
    int getBalance();
    int getBet();
    byte getAction(Hand hand);
    //byte getAction(Hand hand, Lists<Cards> cards) -> card counter version
}
