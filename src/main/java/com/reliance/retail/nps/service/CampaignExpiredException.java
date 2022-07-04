package com.reliance.retail.nps.service;

public class CampaignExpiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CampaignExpiredException() {
        super("Campaign either expired or not active at this moment!");
    }
}
