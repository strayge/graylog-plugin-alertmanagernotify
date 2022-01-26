import React from 'react';
import PropTypes from 'prop-types';
import lodash from 'lodash';

import { Input } from 'components/bootstrap';
import * as FormsUtils from 'util/FormsUtils';

class AlertManagerNotifyForm extends React.Component {
  static propTypes = {
    config: PropTypes.object.isRequired,
    validation: PropTypes.object.isRequired,
    onChange: PropTypes.func.isRequired,
  };

  propagateChange = (key, value) => {
    const { config, onChange } = this.props;
    const nextConfig = lodash.cloneDeep(config);

    nextConfig[key] = value;
    onChange(nextConfig);
  };

  handleChange = (event) => {
    const { name } = event.target;

    this.propagateChange(name, FormsUtils.getValueFromInput(event.target));
  };

  static defaultConfig = {
    api_url: 'http://alertmanager:9093/api/v1/alerts',
    alert_name: 'Alert Name',
    labels: '',
    annotations: '',
    grace: '',
  }

  render() {
    const { config, validation } = this.props;

    return (
      <>
        <Input id="notification-api_url"
               name="api_url"
               label="API URL"
               type="text"
               bsStyle={validation.errors.api_url ? 'error' : null}
               help={lodash.get(validation, 'errors.api_url[0]', 'AlertManager API URL')}
               value={config.api_url || ''}
               onChange={this.handleChange}
               required />
        <Input id="notification-alert_name"
               name="alert_name"
               label="Alert Name"
               type="text"
               bsStyle={validation.errors.alert_name ? 'error' : null}
               help={lodash.get(validation, 'errors.alert_name[0]', 'Alert Name')}
               value={config.alert_name || ''}
               onChange={this.handleChange}
               required />
        <Input id="notification-labels"
               name="labels"
               label="Labels"
               type="text"
               bsStyle={validation.errors.labels ? 'error' : null}
               help={lodash.get(validation, 'errors.labels[0]', 'Custom labels.')}
               value={config.labels || ''}
               onChange={this.handleChange} />
        <Input id="notification-annotations"
               name="annotations"
               label="Annotations"
               type="text"
               bsStyle={validation.errors.annotations ? 'error' : null}
               help={lodash.get(validation, 'errors.annotations[0]', 'Custom annotations.')}
               value={config.annotations || ''}
               onChange={this.handleChange} />
        <Input id="notification-grace"
               name="grace"
               label="Grace period"
               type="text"
               bsStyle={validation.errors.grace ? 'error' : null}
               help={lodash.get(validation, 'errors.grace[0]', 'Grace period (in minutes).')}
               value={config.grace || ''}
               onChange={this.handleChange} />
      </>
    );
  }
}

export default AlertManagerNotifyForm;
