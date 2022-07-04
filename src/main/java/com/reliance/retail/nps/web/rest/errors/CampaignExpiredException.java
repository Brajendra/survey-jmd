package com.reliance.retail.nps.web.rest.errors;

public class CampaignExpiredException extends BadRequestAlertException{

    private static final long serialVersionUID = 1L;

    public CampaignExpiredException() {
        super(ErrorConstants.DEFAULT_TYPE, "Campaign is expired or not active at this moment", "campaignExpired", "campaignExpired");
    }
}
