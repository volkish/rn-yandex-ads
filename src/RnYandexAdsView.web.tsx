import * as React from 'react';

import { RnYandexAdsViewProps } from './RnYandexAds.types';

export default function RnYandexAdsView(props: RnYandexAdsViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
