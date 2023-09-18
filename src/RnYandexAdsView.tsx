import { requireNativeViewManager } from "expo-modules-core";
import * as React from "react";

import type {
  RnYandexAdsViewRef,
  RnYandexAdsViewProps,
} from "./RnYandexAds.types";

const NativeView = requireNativeViewManager("RnYandexAds");

export { RnYandexAdsViewRef };
export default React.forwardRef(function (
  { width, maxHeight, ...props }: RnYandexAdsViewProps,
  ref?: React.ForwardedRef<RnYandexAdsViewRef>,
) {
  return (
    <NativeView
      ref={ref}
      {...props}
      width={width}
      maxHeight={maxHeight}
      style={{ width, height: maxHeight }}
    />
  );
});
