import RnYandexAdsModule from "./RnYandexAdsModule";
import RnYandexAdsView, { RnYandexAdsViewRef } from "./RnYandexAdsView";

export const SDKVersion = RnYandexAdsModule.SDKVersion;

export const PackageVersion = "0.1.5";

export async function initialize(options: {
  userConsent?: boolean;
  locationConsent?: boolean;
  enableLogging?: boolean;
  enableDebugErrorIndicator?: boolean;
}): Promise<string> {
  return await RnYandexAdsModule.initialize(options);
}

export function setUserConsent(state: boolean) {
  RnYandexAdsModule.setUserConsent(state);
}

export function setLocationTrackingEnabled(state: boolean) {
  RnYandexAdsModule.setLocationTrackingEnabled(state);
}

export async function showInterstitial(adUnitId: string) {
  return await RnYandexAdsModule.showInterstitial(adUnitId);
}

export { RnYandexAdsView, RnYandexAdsViewRef };
