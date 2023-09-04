import { useCallback, useRef, useState } from 'react';
import { StyleSheet, Text, View, Button, ScrollView, SafeAreaView } from 'react-native';

import * as RnYandexAds from 'rn-yandex-ads';

export default function App() {
  const [initialized, setInitialized] = useState(false)
  const [bannerState, setBannerState] = useState<Record<string, any>>()
  const [interstitalResponse, setInterstitalResponse] = useState<Record<string, any>>()

  const onPress = useCallback(async () => {
    console.log(await RnYandexAds.initialize({
      enableLogging: true,
      enableDebugErrorIndicator: true
    }))

    setInitialized(true)
  }, [])

  const onShow = useCallback(async () => {
    if (initialized) {
      setInterstitalResponse(await RnYandexAds.showInterstitial('demo-interstitial-yandex'))
    }
  }, [initialized])

  const onSetUserConsent = useCallback(() => { RnYandexAds.setUserConsent(true) }, [])
  const onLocationTracking = useCallback(() => { RnYandexAds.setLocationTrackingEnabled(true) }, [])

  const ref = useRef<RnYandexAds.RnYandexAdsViewRef>(null);

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView style={styles.scrollView} contentContainerStyle={styles.container}>
        {initialized && (
          <>
            <RnYandexAds.RnYandexAdsView
              adUnitId='demo-banner-yandex'
              width={320}
              maxHeight={320}
              ref={ref}
              onAdViewDidLoad={() => setBannerState(state => ({ ...state, onAdViewDidLoad: true }))}
              onAdViewDidClick={() => setBannerState(state => ({ ...state, onAdViewDidClick: true }))}
              onAdView={() => setBannerState(state => ({ ...state, onAdView: true }))}
              onAdViewDidFailLoading={() => setBannerState(state => ({ ...state, onAdViewDidFailLoading: true }))}
              onAdViewWillLeaveApplication={() => setBannerState(state => ({ ...state, onAdViewWillLeaveApplication: true }))}
            />

            {bannerState && (
              <Text>{JSON.stringify(bannerState, null, " ")}</Text>
            )}
          </>
        )}

        <Button title='Initialize And Show Banner' onPress={onPress} />

        {initialized && (
          <>
            <Button title='Show interstital' onPress={onShow} />
            {interstitalResponse && (
              <Text>{JSON.stringify(interstitalResponse, null, " ")}</Text>
            )}
          </>
        )}


        <Button title='Set UserConsent' onPress={onSetUserConsent} />
        <Button title='Set LocationTracking' onPress={onLocationTracking} />

        <Text>{RnYandexAds.SDKVersion}</Text>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
  },
  scrollView: {
    backgroundColor: '#fff',
  },
  container: {
    alignItems: 'center',
    backgroundColor: '#fff',
    rowGap: 20
  },
});
