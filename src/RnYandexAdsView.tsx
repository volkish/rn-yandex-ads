import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import type { RnYandexAdsViewRef, RnYandexAdsViewProps } from './RnYandexAds.types';

const NativeView = requireNativeViewManager('RnYandexAds');

export { RnYandexAdsViewRef }
export default React.forwardRef(function (props: RnYandexAdsViewProps, ref?: React.ForwardedRef<RnYandexAdsViewRef>) {
  return <NativeView
    ref={ref}
    style={{
      background: '#ccc',
      width: props.width,
      height: props.maxHeight
    }}

    {...props}
  />;
})
