package expo.modules.rnyandexads

import android.app.Activity
import android.util.Log
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import com.yandex.mobile.ads.common.AdRequestConfiguration

class InterstitialAdManager(adUnitId: String, mActivity: Activity?) {


   // var mInterstitialAd: InterstitialAd;
  //  private val eventLogger = InterstitialAdEventLogger()

    init {

        Log.d("EYA", "Interstitial init")
        val loader = InterstitialAdLoader(Common.appContext).apply {
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
        loader.loadAd(AdRequestConfiguration.Builder(adUnitId).build())
    }

    /*
    private inner class InterstitialAdEventLogger : InterstitialAdEventListener {

        override fun onAdLoaded() {
            Log.d("EYA", "Interstitial ad loaded")

            mInterstitialAd.show();
        }

        override fun onAdFailedToLoad(error: AdRequestError) {
            Log.d("EYA", "Interstitial ad failed to load with code ${error.code}: ${error.description}")
        }

        override fun onAdShown() {
            Log.d("EYA", "Interstitial ad shown")
        }

        override fun onAdDismissed() {
            Log.d("EYA", "Interstitial ad dismissed")
        }

        override fun onAdClicked() {
            Log.d("EYA", "Interstitial ad clicked")
        }

        override fun onLeftApplication() {
            Log.d("EYA", "Left application")
        }

        override fun onReturnedToApplication() {
            Log.d("EYA", "Returned to application")
        }

        override fun onImpression(data: ImpressionData?) {
            Log.d("EYA", "Impression: ${data?.rawData}")
        }
    }
    */

}