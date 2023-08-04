package com.ua.money24.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(SubscriberCurrencyId.class)
public class SubscriberCurrency {
    @Id
    private Integer subscriberId;
    private Integer currencyId;
    private Boolean enabled;

    public SubscriberCurrency() {
    }

    public SubscriberCurrency(Integer subscriberId, Integer currencyId, Boolean enabled) {
        this.subscriberId = subscriberId;
        this.currencyId = currencyId;
        this.enabled = enabled;
    }

    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
