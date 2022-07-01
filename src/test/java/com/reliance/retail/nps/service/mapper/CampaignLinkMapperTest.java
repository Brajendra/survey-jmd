package com.reliance.retail.nps.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CampaignLinkMapperTest {

    private CampaignLinkMapper campaignLinkMapper;

    @BeforeEach
    public void setUp() {
        campaignLinkMapper = new CampaignLinkMapperImpl();
    }
}
