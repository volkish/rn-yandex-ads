package expo.modules.rnyandexads

import androidx.preference.PreferenceManager
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.instream.MobileInstreamAds
import android.util.Log

class RnYandexAdsModule : Module() {

    // Each module class must implement the definition function. The definition consists of components
    // that describes the module's functionality and behavior.
    // See https://docs.expo.dev/modules/module-api for more details about available components.
    override fun definition() = ModuleDefinition {
        // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
        // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
        // The module will be accessible from `requireNativeModule('ExpoYandexAds')` in JavaScript.
        Name("RnYandexAds")
        
        // Defines a JavaScript synchronous function that runs the native code on the JavaScript thread.
        AsyncFunction("initialize") { options: InitializeOptions ->
            
            val preferences = PreferenceManager.getDefaultSharedPreferences(Common.appContext)
            preferences.run {
                MobileAds.setUserConsent(options.userConsent)
                MobileAds.setLocationConsent(options.locationConsent)
                MobileAds.setAgeRestrictedUser(false)
            }

            MobileInstreamAds.setAdGroupPreloading(INSTREAM_AD_GROUP_PRELOADING_ENABLED)
            MobileAds.initialize(Common.appContext) {
                Log.d("EYA", "Mobile Ads init done")
            }

            MobileAds.enableLogging(true)
        }

        Function("setUserConsent") { state: Boolean ->
            MobileAds.setUserConsent(state)
            Log.d("EYA", "UserConsent: $state")
        }

        Function("setLocationTrackingEnabled") { state: Boolean ->
            MobileAds.setLocationConsent(state)
            Log.d("EYA", "LocationTrackingEnabled: $state")
        }

        AsyncFunction("showInterstitial") { adUnitId: String  ->
            Log.d("EYA", "Interstitial trying to load")
            val interstitialAd = InterstitialAdManager(adUnitId, appContext.currentActivity)
        }


        // Enables the module to be used as a native view. Definition components that are accepted as part of
        // the view definition: Prop, Events.
        View(RnYandexAdsView::class) {
            Events(
                "onAdViewDidLoad",
                "onAdViewDidClick",
                "onAdView",
                "onAdViewDidFailLoading",
                "onAdViewWillLeaveApplication"
            )

            // Defines a setter for the `name` prop.
            Prop("width") { view: RnYandexAdsView, prop: Double ->
                view.updateMaxWidth(prop.toInt())
                view.showAd()
            }

            Prop("maxHeight") { view: RnYandexAdsView, prop: Double ->
                view.updateMaxHeight(prop.toInt())
                view.showAd()
            }

            Prop("adUnitId") { view: RnYandexAdsView, prop: String ->
                view.updateAdUnitId(prop)
                view.showAd()
            }

            AsyncFunction("showAd") { view: RnYandexAdsView ->
                Log.d("EYA", "Show ad func called")
                view.showAd()
            }

        }
    }

    private companion object {
        private const val INSTREAM_AD_GROUP_PRELOADING_ENABLED = true
    }
}
