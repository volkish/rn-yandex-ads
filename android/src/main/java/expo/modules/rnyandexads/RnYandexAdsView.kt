package expo.modules.rnyandexads

import android.content.Context
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView
import kotlin.math.roundToInt


class RnYandexAdsView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

    private val eventLogger = BannerAdEventLogger()

    private var maxWidth: Int = 0
    private var maxHeight: Int = 0
    private var adUnitId: String = ""

    private var adsViewState: Int = 0

    private var mBannerAdView: BannerAdView? = null

    fun updateAdUnitId(newValue: String) {
        adUnitId = newValue
    }

    fun updateMaxWidth(newValue: Int) {
        maxWidth = newValue
    }

    fun updateMaxHeight(newValue: Int) {
        maxHeight = newValue
    }

    fun updateState() {
        Log.d("EYA", "Banner state updated")

        adsViewState++

        measure(0, 0)
        layout(x.roundToInt(), y.roundToInt(),
                (maxWidth * resources.displayMetrics.density).roundToInt(),
                (maxHeight * resources.displayMetrics.density).roundToInt())
    }

    private val onAdViewDidLoad by EventDispatcher()
    private val onAdViewDidClick by EventDispatcher()
    private val onAdView by EventDispatcher()
    private val onAdViewDidFailLoading by EventDispatcher()
    private val onAdViewWillLeaveApplication by EventDispatcher()

    fun showAd() {
        if (maxWidth > 0 && maxHeight > 0 && adUnitId.isNotEmpty()) {
            mBannerAdView = BannerAdView(context).also {
                it.setAdUnitId(adUnitId)
                it.setAdSize(BannerAdSize.inlineSize(context, maxWidth, maxHeight))
                it.setBannerAdEventListener(eventLogger)

                val params = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                ).apply {
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                addView(it, params)
                Log.d("EYA", "Banner added to view")

                val adRequest = AdRequest.Builder().build()
                it.loadAd(adRequest)

            }

        }
    }

    private inner class BannerAdEventLogger : BannerAdEventListener {

        override fun onAdLoaded() {
            Log.d("EYA", "Banner ad loaded")

            if (mBannerAdView?.parent is RnYandexAdsView) {
                (mBannerAdView?.parent as RnYandexAdsView).updateState()
            } else {
                Log.d("EYA", "Parent is not view")
            }
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
