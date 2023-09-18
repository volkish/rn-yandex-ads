import ExpoModulesCore
import YandexMobileAds

public class RnYandexAdsModule: Module {
    internal var initialed = false
    internal lazy var interstitialManager = InterstitialManager.shared
    
    public func definition() -> ModuleDefinition {
        Name("RnYandexAds")
        
        AsyncFunction("initialize") { (options: InitializationOptions, promise: Promise) in
            if (options.enableLogging) {
                YMAMobileAds.enableLogging();
            }
            
            if (options.enableDebugErrorIndicator) {
                YMAMobileAds.enableVisibilityErrorIndicator(for: .simulator)
            }
            
            YMAMobileAds.setUserConsent(options.userConsent)
            YMAMobileAds.setLocationTrackingEnabled(options.locationConsent)
            
            if (initialed) {
                promise.resolve("[RnYandexAdsModule] updated \(options)")
            } else {
                YMAMobileAds.initializeSDK(completionHandler: { [weak self] in
                    self?.initialed = true
                    promise.resolve("[RnYandexAdsModule] initialized \(options)")
                })
            }
        }
        
        Property("SDKVersion")
            .get({ return YMAMobileAds.sdkVersion() })
        
        Function("setUserConsent") { (state: Bool) in
            YMAMobileAds.setUserConsent(state)
            print("UserConsent: \(state)")
        }
        
        Function("setLocationTrackingEnabled") { (state: Bool) in
            YMAMobileAds.setLocationTrackingEnabled(state)
            print("LocationTrackingEnabled: \(state)")
        }
        
        AsyncFunction("showInterstitial") { (adUnitId: String, promise: Promise) in
            if (initialed) {
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

