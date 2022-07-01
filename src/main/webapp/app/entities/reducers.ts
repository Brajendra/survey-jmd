import campaign from 'app/entities/campaign/campaign.reducer';
import campaignLink from 'app/entities/campaign-link/campaign-link.reducer';
import question from 'app/entities/question/question.reducer';
import answer from 'app/entities/answer/answer.reducer';
import userAnswers from 'app/entities/user-answers/user-answers.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  campaign,
  campaignLink,
  question,
  answer,
  userAnswers,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
