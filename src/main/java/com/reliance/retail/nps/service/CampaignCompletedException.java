package com.reliance.retail.nps.service;

import com.reliance.retail.nps.web.rest.errors.BadRequestAlertException;
import com.reliance.retail.nps.web.rest.errors.ErrorConstants;

public class CampaignCompletedException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public CampaignCompletedException() {
        super("Campaign already completed by user!");
    }
}
