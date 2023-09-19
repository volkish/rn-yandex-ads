package expo.modules.rnyandexads

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
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
import androidx.core.view.isVisible
import kotlin.math.roundToInt


class RnYandexAdsView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

    private val eventLogger = BannerAdEventLogger()

    var maxWidth: Int = 0;
    var maxHeight: Int = 0;
    var adUnitId: String = "";

    private var bannerAdSize: BannerAdSize? = null
    private var mBannerAdView: BannerAdView? = null;

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

    fun showAd() {
        setBackgroundColor(Color.BLUE)

        if (maxWidth > 0 && maxHeight > 0 && adUnitId.isNotEmpty()) {
            if (mBannerAdView != null) {
                removeView(mBannerAdView)
            }

            mBannerAdView = BannerAdView(context).apply {
                setAdUnitId(adUnitId)
                setAdSize(BannerAdSize.inlineSize(context, 240, 200))
                setBannerAdEventListener(eventLogger)
            }
            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT,
            ).apply {
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }

            addView(mBannerAdView, params)

            Log.d("EYA", "Banner added to view")

            val adRequest = AdRequest.Builder().build()

            mBannerAdView?.loadAd(adRequest)

        }
    }

    private inner class BannerAdEventLogger : BannerAdEventListener {

        override fun onAdLoaded() {
            Log.d("EYA", "Banner ad loaded")
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
