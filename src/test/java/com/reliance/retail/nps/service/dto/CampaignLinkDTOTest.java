package com.reliance.retail.nps.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.retail.nps.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampaignLinkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignLinkDTO.class);
        CampaignLinkDTO campaignLinkDTO1 = new CampaignLinkDTO();
        campaignLinkDTO1.setId(1L);
        CampaignLinkDTO campaignLinkDTO2 = new CampaignLinkDTO();
        assertThat(campaignLinkDTO1).isNotEqualTo(campaignLinkDTO2);
        campaignLinkDTO2.setId(campaignLinkDTO1.getId());
        assertThat(campaignLinkDTO1).isEqualTo(campaignLinkDTO2);
        campaignLinkDTO2.setId(2L);
        assertThat(campaignLinkDTO1).isNotEqualTo(campaignLinkDTO2);
        campaignLinkDTO1.setId(null);
        assertThat(campaignLinkDTO1).isNotEqualTo(campaignLinkDTO2);
    }
}
