import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './campaign-link.reducer';

export const CampaignLinkDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const campaignLinkEntity = useAppSelector(state => state.campaignLink.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="campaignLinkDetailsHeading">
          <Translate contentKey="npsSurveyApp.campaignLink.detail.title">CampaignLink</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{campaignLinkEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="npsSurveyApp.campaignLink.code">Code</Translate>
            </span>
          </dt>
          <dd>{campaignLinkEntity.code}</dd>
          <dt>
            <span id="attemptQuestionCount">
              <Translate contentKey="npsSurveyApp.campaignLink.attemptQuestionCount">Attempt Question Count</Translate>
            </span>
          </dt>
          <dd>{campaignLinkEntity.attemptQuestionCount}</dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="npsSurveyApp.campaignLink.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{campaignLinkEntity.eventType}</dd>
          <dt>
            <span id="eventId">
              <Translate contentKey="npsSurveyApp.campaignLink.eventId">Event Id</Translate>
            </span>
          </dt>
          <dd>{campaignLinkEntity.eventId}</dd>
          <dt>
            <span id="userInfo">
              <Translate contentKey="npsSurveyApp.campaignLink.userInfo">User Info</Translate>
            </span>
          </dt>
          <dd>{campaignLinkEntity.userInfo}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="npsSurveyApp.campaignLink.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {campaignLinkEntity.createdAt ? (
              <TextFormat value={campaignLinkEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="npsSurveyApp.campaignLink.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {campaignLinkEntity.updatedAt ? (
              <TextFormat value={campaignLinkEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="npsSurveyApp.campaignLink.campaign">Campaign</Translate>
          </dt>
          <dd>{campaignLinkEntity.campaign ? campaignLinkEntity.campaign.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/campaign-link" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/campaign-link/${campaignLinkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CampaignLinkDetail;
