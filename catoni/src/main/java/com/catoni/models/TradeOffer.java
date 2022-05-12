package com.catoni.models;

import com.catoni.models.enums.ResourceTypes;

import java.util.List;
import java.util.Map;

public class TradeOffer {

    /*
        resources that are in the offer

        offer: {
            ROCK: 1,
            WOOD: 1
        }
        receive: {
            GRAIN: 2
        }

    */
    private Map<ResourceTypes, Integer> offer;
    private Map<ResourceTypes, Integer> receive;

    public TradeOffer() {}

    public TradeOffer(Map<ResourceTypes, Integer> offer, Map<ResourceTypes, Integer> receive) {
        this.offer = offer;
        this.receive = receive;
    }

    public Map<ResourceTypes, Integer> getOffer() {
        return offer;
    }

    public void setOffer(Map<ResourceTypes, Integer> offer) {
        this.offer = offer;
    }

    public Map<ResourceTypes, Integer> getReceive() {
        return receive;
    }

    public void setReceive(Map<ResourceTypes, Integer> receive) {
        this.receive = receive;
    }
}
