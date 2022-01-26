import React from 'react';
import PropTypes from 'prop-types';

import CommonNotificationSummary from 'components/event-notifications/event-notification-types/CommonNotificationSummary';

class AlertManagerNotifySummary extends React.Component {
  static propTypes = {
    type: PropTypes.string.isRequired,
    notification: PropTypes.object,
    definitionNotification: PropTypes.object.isRequired,
  };

  static defaultProps = {
    notification: {},
  };

  render() {
    const { notification } = this.props;

    return (
      <CommonNotificationSummary {...this.props}>
        <>
          <tr>
            <td>API URL</td>
            <td><code>{notification.config.api_url}</code></td>
          </tr>
          <tr>
            <td>Alert Name</td>
            <td><code>{notification.config.alert_name}</code></td>
          </tr>
          <tr>
            <td>Labels</td>
            <td><code>{notification.config.labels}</code></td>
          </tr>
          <tr>
            <td>Annotations</td>
            <td><code>{notification.config.annotations}</code></td>
          </tr>
          <tr>
            <td>Grace period</td>
            <td><code>{notification.config.grace}</code></td>
          </tr>
        </>
      </CommonNotificationSummary>
    );
  }
}

export default AlertManagerNotifySummary;
