package com.reliance.retail.nps.web.rest.errors;

public class CampaignCompletedException extends BadRequestAlertException{

    private static final long serialVersionUID = 1L;

    public CampaignCompletedException() {
        super(ErrorConstants.DEFAULT_TYPE, "Campaign Already completed", "campaignInfo", "campaignCompleted");
    }
}
