import { useCallback, useRef, useState } from 'react';
import { StyleSheet, Text, View, Button } from 'react-native';

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

  const ref = useRef<RnYandexAds.RnYandexAdsViewRef>(null);

  return (
    <View style={styles.container}>
      {initialized && (
        <>
          <RnYandexAds.RnYandexAdsView
            adUnitId='demo-banner-yandex'
            width={320}
            maxHeight={320}
            ref={ref}
            onAdViewDidLoad={() => setBannerState(state => ({...state, onAdViewDidLoad: true }))}
            onAdViewDidClick={() => setBannerState(state => ({...state, onAdViewDidClick: true }))}
            onAdView={() => setBannerState(state => ({...state, onAdView: true }))}
            onAdViewDidFailLoading={() => setBannerState(state => ({...state, onAdViewDidFailLoading: true }))}
            onAdViewWillLeaveApplication={() => setBannerState(state => ({...state, onAdViewWillLeaveApplication: true }))}
          />

          {bannerState && (
            <Text>{JSON.stringify(bannerState, null, "\n")}</Text>
          )}
        </>
      )}

      <Button title='Initialize And Show Banner' onPress={onPress} />

      {initialized && (
        <>
          <Button title='Show interstital' onPress={onShow} />
          {interstitalResponse && (
            <View>
              <Text>CLICKED: {String(interstitalResponse.didClick)}{"\n"}</Text>
              <Text>TRACKED: {String(interstitalResponse.trackImpression)}</Text>
            </View>
          )}
        </>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
    rowGap: 20
  },
});
