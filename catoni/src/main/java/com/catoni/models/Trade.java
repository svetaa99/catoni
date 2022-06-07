package com.catoni.models;

import java.util.ArrayList;
import java.util.List;

public class Trade {

    private TradeOffer tradeOffer = new TradeOffer();
    // players that accepted the trade
    private List<String> acceptedTrade = new ArrayList<>();

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
