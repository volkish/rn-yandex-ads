import { useCallback, useEffect, useRef, useState } from "react";
import {
  StyleSheet,
  Text,
  Button,
  ScrollView,
  SafeAreaView,
} from "react-native";
import * as RnYandexAds from "rn-yandex-ads";
import { ModuleVersion } from "rn-yandex-ads";

export default function App() {
  const [initialized, setInitialized] = useState(false);
  const [bannerState, setBannerState] = useState<Record<string, any>>();
  const [interstitialResponse, setInterstitialResponse] =
    useState<Record<string, any>>();
  const [random, setRandom] = useState(0);

  useEffect(() => {
    RnYandexAds.initialize({
      enableLogging: true,
      enableDebugErrorIndicator: true,
    }).then(() => {
      setInitialized(true);
    });
  }, []);

  const onPress = useCallback(async () => {
    console.log(
      await RnYandexAds.initialize({
        enableLogging: true,
        enableDebugErrorIndicator: true,
      }),
    );

    setInitialized(true);
  }, []);

  const onShow = useCallback(async () => {
    if (initialized) {
      setInterstitialResponse(
        await RnYandexAds.showInterstitial("demo-interstitial-yandex"),
      );
    }
  }, [initialized]);

  const onRefresh = useCallback(() => setRandom((state) => state + 1), []);
  const onSetUserConsent = useCallback(() => {
    RnYandexAds.setUserConsent(true);
  }, []);
  const onLocationTracking = useCallback(() => {
    RnYandexAds.setLocationTrackingEnabled(true);
  }, []);

  const ref = useRef<RnYandexAds.RnYandexAdsViewRef>(null);

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView
        style={styles.scrollView}
        contentContainerStyle={styles.container}
      >
        {initialized && (
          <>
            <RnYandexAds.RnYandexAdsView
              key={random}
              adUnitId="demo-banner-yandex"
              width={320}
              maxHeight={320}
              ref={ref}
              onAdViewDidLoad={() =>
                setBannerState((state) => ({ ...state, onAdViewDidLoad: true }))
              }
              onAdViewDidClick={() =>
                setBannerState((state) => ({
                  ...state,
                  onAdViewDidClick: true,
                }))
              }
              onAdView={() =>
                setBannerState((state) => ({ ...state, onAdView: true }))
              }
              onAdViewDidFailLoading={() =>
                setBannerState((state) => ({
                  ...state,
                  onAdViewDidFailLoading: true,
                }))
              }
              onAdViewWillLeaveApplication={() =>
                setBannerState((state) => ({
                  ...state,
                  onAdViewWillLeaveApplication: true,
                }))
              }
            />

            {bannerState && (
              <Text>{JSON.stringify(bannerState, null, " ")}</Text>
            )}

            <Button title="Refresh" onPress={onRefresh} />
          </>
        )}

        <Button title="Show Banner" onPress={onPress} />

        {initialized && (
          <>
            <Button title="Show interstital" onPress={onShow} />
            {interstitialResponse && (
              <Text>{JSON.stringify(interstitialResponse, null, " ")}</Text>
            )}
          </>
        )}

        <Button title="Set UserConsent" onPress={onSetUserConsent} />
        <Button title="Set LocationTracking" onPress={onLocationTracking} />

        <Text>{RnYandexAds.SDKVersion}</Text>
        <Text>{RnYandexAds.ModuleVersion}</Text>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
  },
  scrollView: {
    backgroundColor: "#fff",
  },
  container: {
    alignItems: "center",
    backgroundColor: "#fff",
    rowGap: 20,
  },
});
