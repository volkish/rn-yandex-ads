Yandex Ads Integration based on Expo Modules (https://docs.expo.dev/modules/overview/)

# Installation

```
npm install rn-yandex-ads
```

# Usage

```tsx
import { FC, useState, useEffect } from 'react'
import { Text, TouchableOpacity } from 'react-native';
import * as YandexAds from 'rn-yandex-ads'

const YANDEX_INTERSTITIAL_ID = "R-M-2085336-2"
const YANDEX_BANNER_ID = "R-M-2085336-1"

interface Props {
  width: number,
  height: number
}

const SmallBanner: FC<Props> = ({ width, height }) => {
  const onPress = () => {
    YandexAds.showInterstitial(YANDEX_INTERSTITIAL_ID)
  }

  return (
    <>
      <TouchableOpacity onPress={onPress}>
        <Text>Show Interstitial</Text>
      </TouchableOpacity>

      <YandexAds.RnYandexAdsView
        adUnitId={YANDEX_BANNER_ID}
        width={width}
        maxHeight={height}
        onAdViewDidFailLoading={event => console.log(event)}
      />
    </>
  )
}

export default function App() {
  const [ready, setReady] = useState(false)

  useEffect(() => {
    YandexAds.initialize({
      userConsent: false,
      locationConsent: false
    }).then(() => setReady(true))
  }, [])

  if (! ready) {
    return null
  }

  return (
    <SmallBanner width={310} height={310} />
  )
}


```


# Contributing

Contributions are very welcome! Please refer to guidelines described in the [contributing guide]( https://github.com/expo/expo#contributing).
