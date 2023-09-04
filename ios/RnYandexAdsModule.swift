import ExpoModulesCore
import YandexMobileAds

public class RnYandexAdsModule: Module {
    internal var initied = false
    internal lazy var interstitialManager = InterstitialManager.shared
    
    public func definition() -> ModuleDefinition {
        Name("RnYandexAds")
        
        AsyncFunction("initialize") { (options: InitializeOptions, promise: Promise) in
            if (options.enableLogging) {
                YMAMobileAds.enableLogging();
            }
            
            if (options.enableDebugErrorIndicator) {
                YMAMobileAds.enableVisibilityErrorIndicator(for: .simulator)
            }
            
            YMAMobileAds.setUserConsent(options.userConsent)
            YMAMobileAds.setLocationTrackingEnabled(options.locationConsent)
            
            if (initied) {
                promise.resolve("[RnYandexAdsModule] updated \(options)")
            } else {
                YMAMobileAds.initializeSDK(completionHandler: { [weak self] in
                    self?.initied = true
                    promise.resolve("[RnYandexAdsModule] initialized \(options)")
                })
            }
        }
        
        AsyncFunction("showInterstitial") { (adUnitId: String, promise: Promise) in
            if (initied) {
                interstitialManager.showAd(
                    adUnitId,
                    withResolver: promise.resolver,
                    withRejecter: promise.rejecter
                )
            } else {
                promise.rejecter(InitializationRequiredException())
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

internal class InitializationRequiredException: Exception {
    override var reason: String {
        "Initialization is required before use"
    }
}
