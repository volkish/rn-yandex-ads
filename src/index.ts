import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to RnYandexAds.web.ts
// and on native platforms to RnYandexAds.ts
import RnYandexAdsModule from './RnYandexAdsModule';
import RnYandexAdsView from './RnYandexAdsView';
import { ChangeEventPayload, RnYandexAdsViewProps } from './RnYandexAds.types';

// Get the native constant value.
export const PI = RnYandexAdsModule.PI;

export function hello(): string {
  return RnYandexAdsModule.hello();
}

export async function setValueAsync(value: string) {
  return await RnYandexAdsModule.setValueAsync(value);
}

const emitter = new EventEmitter(RnYandexAdsModule ?? NativeModulesProxy.RnYandexAds);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { RnYandexAdsView, RnYandexAdsViewProps, ChangeEventPayload };
