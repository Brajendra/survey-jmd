package com.reliance.retail.nps.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.retail.nps.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampaignLinkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignLink.class);
        CampaignLink campaignLink1 = new CampaignLink();
        campaignLink1.setId(1L);
        CampaignLink campaignLink2 = new CampaignLink();
        campaignLink2.setId(campaignLink1.getId());
        assertThat(campaignLink1).isEqualTo(campaignLink2);
        campaignLink2.setId(2L);
        assertThat(campaignLink1).isNotEqualTo(campaignLink2);
        campaignLink1.setId(null);
        assertThat(campaignLink1).isNotEqualTo(campaignLink2);
    }
}
