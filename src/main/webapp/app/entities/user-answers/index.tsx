import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserAnswers from './user-answers';
import UserAnswersDetail from './user-answers-detail';
import UserAnswersUpdate from './user-answers-update';
import UserAnswersDeleteDialog from './user-answers-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserAnswersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserAnswersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserAnswersDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserAnswers} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserAnswersDeleteDialog} />
  </>
);

export default Routes;
