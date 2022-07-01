import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './answer.reducer';

export const AnswerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const answerEntity = useAppSelector(state => state.answer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="answerDetailsHeading">
          <Translate contentKey="npsSurveyApp.answer.detail.title">Answer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{answerEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="npsSurveyApp.answer.title">Title</Translate>
            </span>
          </dt>
          <dd>{answerEntity.title}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="npsSurveyApp.answer.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {answerEntity.createdAt ? <TextFormat value={answerEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="npsSurveyApp.answer.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {answerEntity.updatedAt ? <TextFormat value={answerEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="npsSurveyApp.answer.question">Question</Translate>
          </dt>
          <dd>{answerEntity.question ? answerEntity.question.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer/${answerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnswerDetail;
