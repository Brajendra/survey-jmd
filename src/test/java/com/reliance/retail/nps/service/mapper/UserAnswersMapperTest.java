package com.reliance.retail.nps.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAnswersMapperTest {

    private UserAnswersMapper userAnswersMapper;

    @BeforeEach
    public void setUp() {
        userAnswersMapper = new UserAnswersMapperImpl();
    }
}
