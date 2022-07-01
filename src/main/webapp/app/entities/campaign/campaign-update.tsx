import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICampaign } from 'app/shared/model/campaign.model';
import { ActorType } from 'app/shared/model/enumerations/actor-type.model';
import { EventType } from 'app/shared/model/enumerations/event-type.model';
import { ChannelType } from 'app/shared/model/enumerations/channel-type.model';
import { getEntity, updateEntity, createEntity, reset } from './campaign.reducer';

export const CampaignUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const campaignEntity = useAppSelector(state => state.campaign.entity);
  const loading = useAppSelector(state => state.campaign.loading);
  const updating = useAppSelector(state => state.campaign.updating);
  const updateSuccess = useAppSelector(state => state.campaign.updateSuccess);
  const actorTypeValues = Object.keys(ActorType);
  const eventTypeValues = Object.keys(EventType);
  const channelTypeValues = Object.keys(ChannelType);
  const handleClose = () => {
    props.history.push('/campaign');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...campaignEntity,
      ...values,
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
          actor: 'Customer',
          eventType: 'Payment',
          channel: 'SMS',
          ...campaignEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="npsSurveyApp.campaign.home.createOrEditLabel" data-cy="CampaignCreateUpdateHeading">
            <Translate contentKey="npsSurveyApp.campaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
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
                  id="campaign-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('npsSurveyApp.campaign.name')}
                id="campaign-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.isActive')}
                id="campaign-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.createdBy')}
                id="campaign-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.startDate')}
                id="campaign-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.endDate')}
                id="campaign-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.createdAt')}
                id="campaign-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.updatedAt')}
                id="campaign-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                label={translate('npsSurveyApp.campaign.actor')}
                id="campaign-actor"
                name="actor"
                data-cy="actor"
                type="select"
              >
                {actorTypeValues.map(actorType => (
                  <option value={actorType} key={actorType}>
                    {translate('npsSurveyApp.ActorType.' + actorType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('npsSurveyApp.campaign.eventType')}
                id="campaign-eventType"
                name="eventType"
                data-cy="eventType"
                type="select"
              >
                {eventTypeValues.map(eventType => (
                  <option value={eventType} key={eventType}>
                    {translate('npsSurveyApp.EventType.' + eventType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('npsSurveyApp.campaign.channel')}
                id="campaign-channel"
                name="channel"
                data-cy="channel"
                type="select"
              >
                {channelTypeValues.map(channelType => (
                  <option value={channelType} key={channelType}>
                    {translate('npsSurveyApp.ChannelType.' + channelType)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/campaign" replace color="info">
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

export default CampaignUpdate;
