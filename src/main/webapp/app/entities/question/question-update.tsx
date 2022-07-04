import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionType } from 'app/shared/model/enumerations/question-type.model';
import { getEntity, updateEntity, createEntity, reset } from './question.reducer';

export const QuestionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const campaigns = useAppSelector(state => state.campaign.entities);
  const questionEntity = useAppSelector(state => state.question.entity);
  const loading = useAppSelector(state => state.question.loading);
  const updating = useAppSelector(state => state.question.updating);
  const updateSuccess = useAppSelector(state => state.question.updateSuccess);
  const questionTypeValues = Object.keys(QuestionType);
  const handleClose = () => {
    props.history.push('/question');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCampaigns({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...questionEntity,
      ...values,
      campaign: campaigns.find(it => it.id.toString() === values.campaign.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'Rating',
          ...questionEntity,
          campaign: questionEntity?.campaign?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="npsSurveyApp.question.home.createOrEditLabel" data-cy="QuestionCreateUpdateHeading">
            <Translate contentKey="npsSurveyApp.question.home.createOrEditLabel">Create or edit a Question</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="question-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('npsSurveyApp.question.title')}
                id="question-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('npsSurveyApp.question.type')} id="question-type" name="type" data-cy="type" type="select">
                {questionTypeValues.map(questionType => (
                  <option value={questionType} key={questionType}>
                    {translate('npsSurveyApp.QuestionType.' + questionType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('npsSurveyApp.question.order')}
                id="question-order"
                name="order"
                data-cy="order"
                type="text"
                hidden={true}
              />
              <ValidatedField
                label={translate('npsSurveyApp.question.createdAt')}
                id="question-createdAt"
                name="createdAt"
                data-cy="createdAt"
                hidden={true}
                type="date"
              />
              <ValidatedField
                label={translate('npsSurveyApp.question.updatedAt')}
                id="question-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                hidden={true}
                type="date"
              />
              <ValidatedField
                id="question-campaign"
                name="campaign"
                data-cy="campaign"
                label={translate('npsSurveyApp.question.campaign')}
                type="select"
              >
                <option value="" key="0" />
                {campaigns
                  ? campaigns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/question" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default QuestionUpdate;
