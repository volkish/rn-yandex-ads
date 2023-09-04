import type { NativeSyntheticEvent } from "react-native";

export type ChangeEventPayload = {
  value: string;
};

export type RnYandexAdsViewRef = {
  showAd(): Promise<void>;
}

export type RnYandexAdsViewProps = {
  width: number,
  maxHeight: number,
  adUnitId: string,
  onAdViewDidLoad?: (event: NativeSyntheticEvent<undefined>) => void,
  onAdViewDidClick?: (event: NativeSyntheticEvent<undefined>) => void,
  onAdView?: (event: NativeSyntheticEvent<undefined>) => void,
  onAdViewDidFailLoading?: (event: NativeSyntheticEvent<any>) => void,
  onAdViewWillLeaveApplication?: (event: NativeSyntheticEvent<undefined>) => void
};
