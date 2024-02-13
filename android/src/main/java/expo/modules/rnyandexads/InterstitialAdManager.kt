package expo.modules.rnyandexads

import android.app.Activity
import android.util.Log
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import com.yandex.mobile.ads.common.AdRequestConfiguration

class InterstitialAdManager(adUnitId: String, mActivity: Activity?) {

    private var interstitialAdLoader: InterstitialAdLoader? = null
    private var interstitialAd: InterstitialAd? = null

    init {

        Log.d("EYA", "Interstitial init")
        interstitialAdLoader = InterstitialAdLoader(Common.appContext).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // now interstitialAd is ready to show
                    Log.d("EYA", "Interstitial loaded")
                    if (mActivity != null) {
                        Log.d("EYA", "Interstitial show")
                        interstitialAd.show(mActivity)
                    } // ??????????
                    else {
                        Log.d("EYA", "Interstitial activity null")
                    }
                }

                override fun onAdFailedToLoad(adRequestError: AdRequestError) {}
            })
        }
        interstitialAdLoader!!.loadAd(AdRequestConfiguration.Builder(adUnitId).build())
    }

    private fun destroyInterstitialAd() {
        // don't forget to clean up event listener to null?
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }

    private inner class InterstitialAdEventLogger : InterstitialAdEventListener {

        override fun onAdShown() {
            Log.d("EYA", "Interstitial ad shown")
        }

        override fun onAdFailedToShow(adError: AdError) {
            Log.d("EYA", "Interstitial ad show error: $adError")
        }

        override fun onAdDismissed() {
            Log.d("EYA", "Interstitial ad dismissed")
            destroyInterstitialAd()
        }

        override fun onAdClicked() {
            Log.d("EYA", "Interstitial ad clicked")
        }

        override fun onAdImpression(data: ImpressionData?) {
            Log.d("EYA", "Impression: ${data?.rawData}")
        }
    }
}