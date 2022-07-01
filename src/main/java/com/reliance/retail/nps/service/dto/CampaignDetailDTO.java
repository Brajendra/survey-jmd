package com.reliance.retail.nps.service.dto;

import java.util.List;

public class CampaignDetailDTO {

    CampaignDTO campaign;

    List<QuestionDTO> questions;


    public CampaignDTO getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignDTO campaign) {
        this.campaign = campaign;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
