import ExpoModulesCore
import YandexMobileAds

class AdaptiveInlineBannerView: ExpoView {
    
    private var _adView: YMAAdView?
    
    var adUnitId: String?
    var width: CGFloat?
    var maxHeight: CGFloat?
    
    let onAdViewDidLoad = EventDispatcher("onAdViewDidLoad")
    let onAdViewDidClick = EventDispatcher("onAdViewDidClick")
    let onAdView = EventDispatcher("onAdView")
    let onAdViewDidFailLoading = EventDispatcher("onAdViewDidFailLoading")
    let onAdViewWillLeaveApplication = EventDispatcher("onAdViewWillLeaveApplication")
    
    func showAd() {
        if (_adView != nil) {
            _adView!.removeFromSuperview()
            _adView = nil
        }
        
        if (adUnitId == nil || width == nil || maxHeight == nil) {
            return
        }
        
        let adSize = YMABannerAdSize.inlineSize(withWidth: width!, maxHeight: maxHeight!)
        let adView = YMAAdView(adUnitID: adUnitId!, adSize: adSize)
        
        _adView = adView
        
        adView.delegate = self
        adView.translatesAutoresizingMaskIntoConstraints = false
        
        addSubview(adView)
        NSLayoutConstraint.activate([
            adView.topAnchor.constraint(equalTo: self.topAnchor),
            adView.centerXAnchor.constraint(equalTo: self.centerXAnchor)
        ])
        
        adView.loadAd()
    }
}

extension AdaptiveInlineBannerView: YMAAdViewDelegate {
    func adViewDidLoad(_ adView: YMAAdView) {
        onAdViewDidLoad()
    }
    
    func adViewDidClick(_ adView: YMAAdView) {
        onAdViewDidClick()
    }
    
    func adView(_ adView: YMAAdView, didTrackImpressionWith impressionData: YMAImpressionData?) {
        onAdView([
            "impressionData": convertImpressionData(impressionData)
        ])
    }
    
    func adViewDidFailLoading(_ adView: YMAAdView, error: Error) {
        onAdViewDidFailLoading([
            "error": error
        ])
    }
    
    func adViewWillLeaveApplication(_ adView: YMAAdView) {
        onAdViewWillLeaveApplication()
    }
}
