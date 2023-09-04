import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to RnYandexAds.web.ts
// and on native platforms to RnYandexAds.ts
import RnYandexAdsModule from './RnYandexAdsModule';
import RnYandexAdsView, { RnYandexAdsViewRef } from './RnYandexAdsView';

export const SDKVersion = RnYandexAdsModule.SDKVersion

export async function initialize(options: {
  userConsent?: boolean,
  locationConsent?: boolean,
  enableLogging?: boolean,
  enableDebugErrorIndicator?: boolean,
}) : Promise<string> {
  return await RnYandexAdsModule.initialize(options);
}

export function setUserConsent(state: boolean) {
  RnYandexAdsModule.setUserConsent(state)
}

export function setLocationTrackingEnabled(state: boolean) {
  RnYandexAdsModule.setLocationTrackingEnabled(state)
}

export async function showInterstitial(adUnitId: string) {
  return await RnYandexAdsModule.showInterstitial(adUnitId);
}

export { RnYandexAdsView, RnYandexAdsViewRef };
