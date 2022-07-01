import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICampaignLink } from 'app/shared/model/campaign-link.model';
import { getEntities, reset } from './campaign-link.reducer';

export const CampaignLink = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const campaignLinkList = useAppSelector(state => state.campaignLink.entities);
  const loading = useAppSelector(state => state.campaignLink.loading);
  const totalItems = useAppSelector(state => state.campaignLink.totalItems);
  const links = useAppSelector(state => state.campaignLink.links);
  const entity = useAppSelector(state => state.campaignLink.entity);
  const updateSuccess = useAppSelector(state => state.campaignLink.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="campaign-link-heading" data-cy="CampaignLinkHeading">
        <Translate contentKey="npsSurveyApp.campaignLink.home.title">Campaign Links</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="npsSurveyApp.campaignLink.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/campaign-link/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="npsSurveyApp.campaignLink.home.createLabel">Create new Campaign Link</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={campaignLinkList ? campaignLinkList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {campaignLinkList && campaignLinkList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('code')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('attemptQuestionCount')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.attemptQuestionCount">Attempt Question Count</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('eventType')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.eventType">Event Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('eventId')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.eventId">Event Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('userInfo')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.userInfo">User Info</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.createdAt">Created At</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    <Translate contentKey="npsSurveyApp.campaignLink.updatedAt">Updated At</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="npsSurveyApp.campaignLink.campaign">Campaign</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {campaignLinkList.map((campaignLink, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/campaign-link/${campaignLink.id}`} color="link" size="sm">
                        {campaignLink.id}
                      </Button>
                    </td>
                    <td>{campaignLink.code}</td>
                    <td>{campaignLink.attemptQuestionCount}</td>
                    <td>{campaignLink.eventType}</td>
                    <td>{campaignLink.eventId}</td>
                    <td>{campaignLink.userInfo}</td>
                    <td>
                      {campaignLink.createdAt ? (
                        <TextFormat type="date" value={campaignLink.createdAt} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {campaignLink.updatedAt ? (
                        <TextFormat type="date" value={campaignLink.updatedAt} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {campaignLink.campaign ? <Link to={`/campaign/${campaignLink.campaign.id}`}>{campaignLink.campaign.id}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/campaign-link/${campaignLink.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/campaign-link/${campaignLink.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/campaign-link/${campaignLink.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="npsSurveyApp.campaignLink.home.notFound">No Campaign Links found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default CampaignLink;
