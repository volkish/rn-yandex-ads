import ExpoModulesCore
import YandexMobileAds

public class RnYandexAdsModule: Module {
    internal var isInitialized = false
    internal lazy var interstitialManager = InterstitialManager.shared
    
    public func definition() -> ModuleDefinition {
        Name("RnYandexAds")
        
        AsyncFunction("initialize") { (options: InitializationOptions, promise: Promise) in
            if (options.enableLogging) {
                MobileAds.enableLogging();
            }
            
            if (options.enableDebugErrorIndicator) {
                MobileAds.enableVisibilityErrorIndicator(for: .simulator)
            }
            
            MobileAds.setUserConsent(options.userConsent)
            MobileAds.setLocationTrackingEnabled(options.locationConsent)
            
            if (isInitialized) {
                promise.resolve("[RnYandexAdsModule] updated \(options)")
            } else {
                MobileAds.initializeSDK(completionHandler: { [weak self] in
                    self?.isInitialized = true
                    promise.resolve("[RnYandexAdsModule] initialized \(options)")
                })
            }
        }
        
        Property("SDKVersion")
            .get({ return MobileAds.sdkVersion() })
        
        Function("setUserConsent") { (state: Bool) in
            MobileAds.setUserConsent(state)
            print("UserConsent: \(state)")
        }
        
        Function("setLocationTrackingEnabled") { (state: Bool) in
            MobileAds.setLocationTrackingEnabled(state)
            print("LocationTrackingEnabled: \(state)")
        }
        
        AsyncFunction("showInterstitial") { (adUnitId: String, promise: Promise) in
            if (isInitialized) {
                interstitialManager.showAd(adUnitId, withPromise: promise)
            } else {
                promise.reject(InitializationRequiredException())
            }
        }
        
        View(AdaptiveInlineBannerView.self) {
            Events(
                "onAdViewDidLoad",
                "onAdViewDidClick",
                "onAdView",
                "onAdViewDidFailLoading",
                "onAdViewWillLeaveApplication"
            )
            
            Prop("width") { (view: AdaptiveInlineBannerView, prop: Double) in
                view.width = CGFloat(prop)
                view.showAd()
            }
            
            Prop("maxHeight") { (view: AdaptiveInlineBannerView, prop: Double) in
                view.maxHeight = CGFloat(prop)
                view.showAd()
            }
            
            Prop("adUnitId") { (view: AdaptiveInlineBannerView, prop: String) in
                view.adUnitId = prop
                view.showAd()
            }
            
            AsyncFunction("showAd") { (view: AdaptiveInlineBannerView) in
                view.showAd()
            }
        }
    }
}

