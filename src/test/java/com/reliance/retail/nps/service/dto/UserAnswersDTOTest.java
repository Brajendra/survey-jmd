package com.reliance.retail.nps.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.retail.nps.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAnswersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAnswersDTO.class);
        UserAnswersDTO userAnswersDTO1 = new UserAnswersDTO();
        userAnswersDTO1.setId(1L);
        UserAnswersDTO userAnswersDTO2 = new UserAnswersDTO();
        assertThat(userAnswersDTO1).isNotEqualTo(userAnswersDTO2);
        userAnswersDTO2.setId(userAnswersDTO1.getId());
        assertThat(userAnswersDTO1).isEqualTo(userAnswersDTO2);
        userAnswersDTO2.setId(2L);
        assertThat(userAnswersDTO1).isNotEqualTo(userAnswersDTO2);
        userAnswersDTO1.setId(null);
        assertThat(userAnswersDTO1).isNotEqualTo(userAnswersDTO2);
    }
}
