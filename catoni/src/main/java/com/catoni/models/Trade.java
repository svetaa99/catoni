package com.catoni.models;

import java.util.List;

public class Trade {

    private TradeOffer tradeOffer;
    // players that accepted the trade
    private List<String> acceptedTrade;

    public Trade() {}

    public Trade(TradeOffer tradeOffer, List<String> acceptedTrade) {
        this.tradeOffer = tradeOffer;
        this.acceptedTrade = acceptedTrade;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    public void setTradeOffer(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

    public List<String> getAcceptedTrade() {
        return acceptedTrade;
    }

    public void setAcceptedTrade(List<String> acceptedTrade) {
        this.acceptedTrade = acceptedTrade;
    }
}
