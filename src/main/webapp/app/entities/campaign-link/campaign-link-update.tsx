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
import { ICampaignLink } from 'app/shared/model/campaign-link.model';
import { getEntity, updateEntity, createEntity, reset } from './campaign-link.reducer';

export const CampaignLinkUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const campaigns = useAppSelector(state => state.campaign.entities);
  const campaignLinkEntity = useAppSelector(state => state.campaignLink.entity);
  const loading = useAppSelector(state => state.campaignLink.loading);
  const updating = useAppSelector(state => state.campaignLink.updating);
  const updateSuccess = useAppSelector(state => state.campaignLink.updateSuccess);
  const handleClose = () => {
    props.history.push('/campaign-link');
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
      ...campaignLinkEntity,
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
          ...campaignLinkEntity,
          campaign: campaignLinkEntity?.campaign?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="npsSurveyApp.campaignLink.home.createOrEditLabel" data-cy="CampaignLinkCreateUpdateHeading">
            <Translate contentKey="npsSurveyApp.campaignLink.home.createOrEditLabel">Create or edit a CampaignLink</Translate>
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
                  id="campaign-link-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.code')}
                id="campaign-link-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.attemptQuestionCount')}
                id="campaign-link-attemptQuestionCount"
                name="attemptQuestionCount"
                data-cy="attemptQuestionCount"
                type="text"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.eventType')}
                id="campaign-link-eventType"
                name="eventType"
                data-cy="eventType"
                type="text"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.eventId')}
                id="campaign-link-eventId"
                name="eventId"
                data-cy="eventId"
                type="text"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.userInfo')}
                id="campaign-link-userInfo"
                name="userInfo"
                data-cy="userInfo"
                type="text"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.createdAt')}
                id="campaign-link-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaignLink.updatedAt')}
                id="campaign-link-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="campaign-link-campaign"
                name="campaign"
                data-cy="campaign"
                label={translate('npsSurveyApp.campaignLink.campaign')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/campaign-link" replace color="info">
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

export default CampaignLinkUpdate;
