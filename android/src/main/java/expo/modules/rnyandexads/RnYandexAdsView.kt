package expo.modules.rnyandexads

import android.content.Context
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

    private fun getBannerAdSize(): BannerAdSize? {

        val screenHeight = resources.displayMetrics.run { heightPixels / density }.roundToInt()
        // Calculate the width of the ad, taking into account the padding in the ad container.
        val adWidthPixels = 600;
        //val adWidthPixels = resources.displayMetrics.widthPixels;
        //val adWidthPixels = binding.coordinatorLayout.width
        val adWidth = (adWidthPixels / resources.displayMetrics.density).roundToInt()
        val maxAdHeight = screenHeight / 3
        bannerAdSize = BannerAdSize.inlineSize(context, adWidth, maxAdHeight)

        Log.d("EYA", "Screen height: $screenHeight")
        Log.d("EYA", "Ad width: $adWidth")

        return bannerAdSize;
    }

    fun initBanner() {

        if (maxWidth > 0 && maxHeight > 0 && adUnitId.isNotEmpty()) {

            mBannerAdView = BannerAdView(context).apply {
                setAdUnitId(adUnitId)
                getBannerAdSize()?.let { setAdSize(it) }
                setBannerAdEventListener(eventLogger)
            }
            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT,
            ).apply {
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }

            addView(mBannerAdView, params)

            Log.d("EYA", bannerAdSize.toString())

            val adRequest = AdRequest.Builder().build()

            mBannerAdView.loadAd(adRequest)

        }
    }

    init {

    }

    private inner class BannerAdEventLogger : BannerAdEventListener {

        override fun onAdLoaded() {
            Log.d("EYA", "Banner ad loaded!")
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
