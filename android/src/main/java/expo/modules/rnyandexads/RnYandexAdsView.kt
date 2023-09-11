package expo.modules.rnyandexads

import android.content.Context
import android.util.Log
import android.view.Gravity
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.kotlin.viewevent.EventDispatcher
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import androidx.constraintlayout.widget.ConstraintLayout


class RnYandexAdsView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

    private val eventLogger = BannerAdEventLogger()

    var maxWidth: Int = 0;
    var maxHeight: Int = 0;
    var adUnitId: String = "";
    lateinit var mBannerAdView: BannerAdView;

    fun updateAdUnitId(newValue: String) {
        adUnitId = newValue
    }

    fun updateMaxWidth(newValue: Int) {
        maxWidth = newValue
    }

    fun updateMaxHeight(newValue: Int) {
        maxHeight = newValue
    }

    private val onAdViewDidLoad by EventDispatcher()
    private val onAdViewDidClick by EventDispatcher()
    private val onAdView by EventDispatcher()
    private val onAdViewDidFailLoading by EventDispatcher()
    private val onAdViewWillLeaveApplication by EventDispatcher()

    fun initBanner() {

        Log.d("EYA", "Trying to init banner!")

        if (maxWidth > 0 && maxHeight > 0 && adUnitId.isNotEmpty()) {
            mBannerAdView = BannerAdView(context);
            mBannerAdView.setAdUnitId(adUnitId);

            Log.d("EYA", "Ad unit: $adUnitId")
            Log.d("EYA", "Size: $maxWidth x $maxHeight")

            mBannerAdView.setAdSize(BannerAdSize.inlineSize(Common.appContext, maxWidth, maxHeight));

            mBannerAdView.setBannerAdEventListener(eventLogger);

            val adRequest = AdRequest.Builder().build();
/*
            mBannerAdView.layoutParams = LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            )
            mBannerAdView.gravity = Gravity.CENTER
            */

            mBannerAdView.loadAd(adRequest);
        }
    }

    init {

    }

    private inner class BannerAdEventLogger : BannerAdEventListener {

        override fun onAdLoaded() {
            Log.d("EYA", "Banner ad loaded!!!")

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ).apply {
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }

            addView(mBannerAdView, params)
        }

        override fun onAdFailedToLoad(error: AdRequestError) {
            Log.d("EYA", "Banner ad failed to load with code ${error.code}: ${error.description}")
        }

        override fun onAdClicked() {
            Log.d("EYA", "Banner ad clicked")
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

}
