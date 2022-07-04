import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-answers.reducer';

export const UserAnswersDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userAnswersEntity = useAppSelector(state => state.userAnswers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAnswersDetailsHeading">
          <Translate contentKey="npsSurveyApp.userAnswers.detail.title">UserAnswers</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userAnswersEntity.id}</dd>
          <dt>
            <span id="campaignLinkId">
              <Translate contentKey="npsSurveyApp.userAnswers.campaignLinkId">Campaign Link Id</Translate>
            </span>
          </dt>
          <dd>{userAnswersEntity.campaignLinkId}</dd>
          <dt>
            <span id="answers">
              <Translate contentKey="npsSurveyApp.userAnswers.answers">Answers</Translate>
            </span>
          </dt>
          <dd>{userAnswersEntity.answers}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="npsSurveyApp.userAnswers.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {userAnswersEntity.createdAt ? (
              <TextFormat value={userAnswersEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="npsSurveyApp.userAnswers.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {userAnswersEntity.updatedAt ? (
              <TextFormat value={userAnswersEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-answers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        {/* <Button tag={Link} to={`/user-answers/${userAnswersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button> */}
      </Col>
    </Row>
  );
};

export default UserAnswersDetail;
