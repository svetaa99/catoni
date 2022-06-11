package com.catoni.models;

import com.catoni.models.enums.TradeStatus;

import java.util.ArrayList;
import java.util.List;

public class Trade {

    private TradeOffer tradeOffer = new TradeOffer();
    // players that accepted the trade
    private List<String> acceptedTrade = new ArrayList<>();

    private TradeStatus status;

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

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeOffer=" + tradeOffer +
                ", acceptedTrade=" + acceptedTrade +
                ", status=" + status +
                '}';
    }
}
