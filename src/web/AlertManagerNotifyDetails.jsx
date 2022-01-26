import * as React from 'react';
import PropTypes from 'prop-types';

import { ReadOnlyFormGroup } from 'components/common';

const AlertManagerNotifyDetails = ({ notification }) => {
  return (
    <>
      <ReadOnlyFormGroup label="API URL" value={notification.config.api_url} />
      <ReadOnlyFormGroup label="Alert Name" value={notification.config.alert_name} />
      <ReadOnlyFormGroup label="Labels" value={notification.config.labels} />
      <ReadOnlyFormGroup label="Annotations" value={notification.config.annotations} />
      <ReadOnlyFormGroup label="Grace period" value={notification.config.grace} />
    </>
  );
};

AlertManagerNotifyDetails.propTypes = {
  notification: PropTypes.object.isRequired,
};

export default AlertManagerNotifyDetails;
