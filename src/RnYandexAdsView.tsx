import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { RnYandexAdsViewProps } from './RnYandexAds.types';

const NativeView: React.ComponentType<RnYandexAdsViewProps> =
  requireNativeViewManager('RnYandexAds');

export default function RnYandexAdsView(props: RnYandexAdsViewProps) {
  return <NativeView {...props} />;
}
