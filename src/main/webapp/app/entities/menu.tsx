import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/campaign">
        <Translate contentKey="global.menu.entities.campaign" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/campaign-link">
        <Translate contentKey="global.menu.entities.campaignLink" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/question">
        <Translate contentKey="global.menu.entities.question" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/answer">
        <Translate contentKey="global.menu.entities.answer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-answers">
        <Translate contentKey="global.menu.entities.userAnswers" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/analytics">
        <Translate contentKey="global.menu.entities.analytics" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
