import { requireNativeModule } from "expo-modules-core";

import { ImpressionData, InitializeOptions } from "./RnYandexAds.types";

type ModuleDefinition = {
  SDKVersion: string;
  showInterstitial(adUnitId: string): Promise<ImpressionData>;
  setLocationTrackingEnabled(state: boolean): void;
  setUserConsent(state: boolean): void;
  initialize(options: InitializeOptions): Promise<string>;
};

// It loads the native module object from the JSI or falls back to
// the bridge module (from NativeModulesProxy) if the remote debugger is on.
export default requireNativeModule<ModuleDefinition>("RnYandexAds");
