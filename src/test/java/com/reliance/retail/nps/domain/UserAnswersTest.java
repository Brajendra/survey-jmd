package com.reliance.retail.nps.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.retail.nps.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAnswersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAnswers.class);
        UserAnswers userAnswers1 = new UserAnswers();
        userAnswers1.setId(1L);
        UserAnswers userAnswers2 = new UserAnswers();
        userAnswers2.setId(userAnswers1.getId());
        assertThat(userAnswers1).isEqualTo(userAnswers2);
        userAnswers2.setId(2L);
        assertThat(userAnswers1).isNotEqualTo(userAnswers2);
        userAnswers1.setId(null);
        assertThat(userAnswers1).isNotEqualTo(userAnswers2);
    }
}
