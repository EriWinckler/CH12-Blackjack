package com.company.cardGame.blackjack;

import com.company.cardGame.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards = new ArrayList<>();
    private int bet = 0;
    private Actor holder;

    public static final byte PUSHPAY = 0;
    public static final byte NORMALPAY = 1;
    public static final byte BLACKJACKPAY = 2;

    public Hand(Actor holder) {
        this.holder = holder;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public String displayHand() {
        StringBuilder output = new StringBuilder();
        for (Card card : cards) {
            output.append(card.display()).append(" ");
        }
        return output.toString().trim();
    }

    public int getValue() {
        int score = 0;
        boolean haveAce11 = false;
        for (Card card : cards) {
            int value = card.getRank();
            switch (value) {
                case 1 -> {
                    value = score + 11 > 21 ? 1 : 11;
                    if (value == 1) {
                        haveAce11 = true;
                    }
                }
                case 11, 12, 13 -> score += 10;
                default -> score += value;
            }
            if (score > 21 && haveAce11) {
                score -= 10;
                haveAce11 = false;
            }
        }
        return score;
    }

    public byte getAction(int dealer) {
        return holder.getAction(this, dealer);
    }

    //pass through method
    public int size() { return cards.size(); };

    public int getBet() { return bet; }

    public void placeBet() {
        bet = holder.placeBet();
    }

    public int getBalance() { return holder.getBalance(); }

    public String getName() { return holder.getName(); }

    public boolean canSplit() {
        return cards.get(0).getRank() == cards.get(1).getRank();
    }

    public void doubleBet() {
        holder.addBalance(-bet);
        bet += 2;
    }

    public void payout(byte type) {
        switch (type) {
            case PUSHPAY -> holder.addBalance(bet);
            case NORMALPAY -> holder.addBalance(bet * 2);
            case BLACKJACKPAY -> holder.addBalance(bet * 2.5);
        }
    }

    public Hand splitHand() {
        // double bet
        bet = bet / 2;
        Hand hand = new Hand(holder);
        hand.addCard(cards.remove(1));
        hand.bet = bet;
        return hand;
    }

    public void revealHand() {
        for (Card card : cards) {
            if (card.getIsFaceDown()) {
                card.flip();
            }
        }
    }

    public void discardHand() {
        cards.clear();
    }

    public int getShownRank() {
        return cards.get(1).getRank();
    }
}
