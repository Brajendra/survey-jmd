import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './question.reducer';

export const QuestionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const questionEntity = useAppSelector(state => state.question.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionDetailsHeading">
          <Translate contentKey="npsSurveyApp.question.detail.title">Question</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{questionEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="npsSurveyApp.question.title">Title</Translate>
            </span>
          </dt>
          <dd>{questionEntity.title}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="npsSurveyApp.question.type">Type</Translate>
            </span>
          </dt>
          <dd>{questionEntity.type}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="npsSurveyApp.question.order">Order</Translate>
            </span>
          </dt>
          <dd>{questionEntity.order}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="npsSurveyApp.question.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {questionEntity.createdAt ? <TextFormat value={questionEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="npsSurveyApp.question.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {questionEntity.updatedAt ? <TextFormat value={questionEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="npsSurveyApp.question.campaign">Campaign</Translate>
          </dt>
          <dd>{questionEntity.campaign ? questionEntity.campaign.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/question" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/question/${questionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionDetail;
