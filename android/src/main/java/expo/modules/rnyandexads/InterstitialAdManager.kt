package expo.modules.rnyandexads

import android.util.Log
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener

class InterstitialAdManager(adUnitId: String) {

    /*
    var mInterstitialAd: InterstitialAd;
    private val eventLogger = InterstitialAdEventLogger()

    init {
        val adRequest = AdRequest.Builder().build();

        mInterstitialAd = InterstitialAd(Common.appContext);

        mInterstitialAd.setAdUnitId(adUnitId);
        mInterstitialAd.setInterstitialAdEventListener(eventLogger);

        mInterstitialAd.loadAd(adRequest);
    }

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