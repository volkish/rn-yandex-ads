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


class RnYandexAdsView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

    private val eventLogger = BannerAdEventLogger()

    private var maxWidth: Int = 0
    private var maxHeight: Int = 0
    private var adUnitId: String = ""

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

    override fun requestLayout() {
        super.requestLayout()

        // This view relies on a measure + layout pass happening after it calls requestLayout().
        // https://github.com/facebook/react-native/issues/4990#issuecomment-180415510
        // https://stackoverflow.com/questions/39836356/react-native-resize-custom-ui-component
        post(measureAndLayout)
    }

    private val measureAndLayout = Runnable {
        measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY))
        layout(left, top, right, bottom)
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
