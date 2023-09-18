import type { NativeSyntheticEvent } from "react-native";

export type RnYandexAdsViewRef = {
  showAd(): Promise<void>;
};

export type InitializeOptions = {
  userConsent?: boolean;
  locationConsent?: boolean;
  enableLogging?: boolean;
  enableDebugErrorIndicator?: boolean;
};

export type ImpressionData = {
  currency: string;
  revenueUSD: string;
  precision: string;
  revenue: string;
  requestId: string;
  blockId: string;
  adType: string;
  ad_unit_id: string;
  network: {
    name: string;
    adapter: string;
    ad_unit_id: string;
  };
};

export type AdViewDidClick = NativeSyntheticEvent<undefined>;
export type AdViewDidLoadEvent = NativeSyntheticEvent<undefined>;
export type AdViewDidFailLoading = NativeSyntheticEvent<undefined>;
export type AdViewEvent = NativeSyntheticEvent<{
  impressionData: ImpressionData;
}>;

export type RnYandexAdsViewProps = {
  width: number;
  maxHeight: number;
  adUnitId: string;
  onAdViewDidLoad?: (event: AdViewDidLoadEvent) => void;
  onAdViewDidClick?: (event: AdViewDidClick) => void;
  onAdView?: (event: AdViewEvent) => void;
  onAdViewDidFailLoading?: (event: AdViewDidFailLoading) => void;
  onAdViewWillLeaveApplication?: (
    event: NativeSyntheticEvent<undefined>,
  ) => void;
};
