import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CampaignLink from './campaign-link';
import CampaignLinkDetail from './campaign-link-detail';
import CampaignLinkUpdate from './campaign-link-update';
import CampaignLinkDeleteDialog from './campaign-link-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampaignLinkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampaignLinkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampaignLinkDetail} />
      <ErrorBoundaryRoute path={match.url} component={CampaignLink} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CampaignLinkDeleteDialog} />
  </>
);

export default Routes;
