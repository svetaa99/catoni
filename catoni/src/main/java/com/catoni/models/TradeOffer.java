package com.catoni.models;

import com.catoni.models.enums.ResourceTypes;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Role(Role.Type.EVENT)
@Timestamp("offeredAt")
@Expires("1m")
public class TradeOffer {

    private Map<ResourceTypes, Integer> offer;

    private Map<ResourceTypes, Integer> receive;

    private Date offeredAt;

    public TradeOffer() {
        this.offeredAt = new Date();
    }

    public TradeOffer(Map<ResourceTypes, Integer> offer, Map<ResourceTypes, Integer> receive) {
        this.offer = offer;
        this.receive = receive;
        this.offeredAt = new Date();
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

    public Date getOfferedAt() {
        return offeredAt;
    }

    public void setOfferedAt(Date offeredAt) {
        this.offeredAt = offeredAt;
    }

    @Override
    public String toString() {
        return "TradeOffer{" +
                "offer=" + offer +
                ", receive=" + receive +
                ", offeredAt=" + offeredAt +
                '}';
    }
}
