import ExpoModulesCore
import YandexMobileAds

class AdaptiveInlineBannerView: ExpoView {
    
    private var _adView: AdView?
    
    var adUnitId: String?
    var width: CGFloat?
    var maxHeight: CGFloat?
    
    let onAdViewDidLoad = EventDispatcher("onAdViewDidLoad")
    let onAdViewDidClick = EventDispatcher("onAdViewDidClick")
    let onAdView = EventDispatcher("onAdView")
    let onAdViewDidFailLoading = EventDispatcher("onAdViewDidFailLoading")
    let onAdViewWillLeaveApplication = EventDispatcher("onAdViewWillLeaveApplication")
    
    override func layoutSubviews() {
      _adView?.frame = bounds
    }
    
    required init(appContext: AppContext? = nil) {
        super.init(appContext: appContext)

        clipsToBounds = true
    }
    
    func showAd() {
        cleanUp()
        
        if (adUnitId == nil || width == nil || maxHeight == nil) {
            return
        }
        
        let adSize = BannerAdSize.inlineSize(withWidth: width!, maxHeight: maxHeight!)
        let adView = AdView(adUnitID: adUnitId!, adSize: adSize)
        
        _adView = adView
        
        adView.delegate = self
        adView.translatesAutoresizingMaskIntoConstraints = false
        
        addSubview(adView)
        
        NSLayoutConstraint.activate([
            adView.leadingAnchor.constraint(equalTo: self.leadingAnchor),
            adView.trailingAnchor.constraint(equalTo: self.trailingAnchor),
            adView.topAnchor.constraint(equalTo: self.topAnchor),
            adView.bottomAnchor.constraint(equalTo: self.bottomAnchor)
        ])
        
        adView.loadAd()
    }

    private func cleanUp() {
        if (_adView != nil) {
            _adView!.removeFromSuperview()
            _adView = nil
        }
    }

    deinit {
        cleanUp()
    }
}

extension AdaptiveInlineBannerView: AdViewDelegate {
    func adViewDidLoad(_ adView: AdView) {
        onAdViewDidLoad()
    }
    
    func adViewDidClick(_ adView: AdView) {
        onAdViewDidClick()
    }
    
    func adView(_ adView: AdView, didTrackImpression impressionData: ImpressionData?) {
        onAdView([
            "impressionData": convertImpressionData(impressionData)
        ])
    }
    
    func adViewDidFailLoading(_ adView: AdView, error: Error) {
        onAdViewDidFailLoading([
            "error": error
        ])
    }
    
    func adViewWillLeaveApplication(_ adView: AdView) {
        onAdViewWillLeaveApplication()
    }
}
