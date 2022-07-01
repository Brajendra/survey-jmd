import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Campaign from './campaign';
import CampaignLink from './campaign-link';
import Question from './question';
import Answer from './answer';
import UserAnswers from './user-answers';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}campaign`} component={Campaign} />
        <ErrorBoundaryRoute path={`${match.url}campaign-link`} component={CampaignLink} />
        <ErrorBoundaryRoute path={`${match.url}question`} component={Question} />
        <ErrorBoundaryRoute path={`${match.url}answer`} component={Answer} />
        <ErrorBoundaryRoute path={`${match.url}user-answers`} component={UserAnswers} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
