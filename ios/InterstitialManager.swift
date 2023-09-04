//
//  InterstitialWrapper.swift
//  RnYandexAds
//
//  Created by Artem Ivanov on 04.09.2023.
//

import Foundation
import ExpoModulesCore
import YandexMobileAds
import React

final class InterstitialManager: NSObject {
    
    static var shared = InterstitialManager()
    
    override private init() { }
    
    public typealias ResolveClosure = (Any?) -> Void
    public typealias RejectClosure = (Exception) -> Void
    
    private var interstitialAd: YMAInterstitialAd?
    private lazy var interstitialAdLoader: YMAInterstitialAdLoader = {
        let loader = YMAInterstitialAdLoader()
        loader.delegate = self
        return loader
    }()
    
    private var resolver: ResolveClosure?
    private var rejecter: RejectClosure?
    
    private var didClick = false
    private var trackImpression = false
    
    private func makeMessageDescription(_ interstitialAd: YMAInterstitialAd) -> String {
        "Interstitial Ad with Unit ID: \(String(describing: interstitialAd.adInfo?.adUnitId))"
    }
    
    func showAd(
        _ adUnitID: String,
        withResolver resolver: @escaping ResolveClosure,
        withRejecter rejecter: @escaping RejectClosure
    ) {
        self.resolver = resolver
        self.rejecter = rejecter
        
        interstitialAdLoader.loadAd(
            with: YMAAdRequestConfiguration(adUnitID: adUnitID)
        )
    }
    
    private func cleanUp () {
        rejecter = nil
        resolver = nil
        
        didClick = false
        trackImpression = false
    }
}


extension InterstitialManager: YMAInterstitialAdLoaderDelegate {
    func interstitialAdLoader(_ adLoader: YMAInterstitialAdLoader, didLoad interstitialAd: YMAInterstitialAd) {
        guard let viewController = RCTPresentedViewController() else {
            rejecter?(MissingCurrentViewControllerException())
            cleanUp()
            
            return;
        }
        
        self.interstitialAd = interstitialAd
        self.interstitialAd?.delegate = self
        self.interstitialAd?.show(from: viewController)
    }
    
    func interstitialAdLoader(_ adLoader: YMAInterstitialAdLoader, didFailToLoadWithError error: YMAAdRequestError) {
        rejecter?(FailedToLoadException(error.description))
        cleanUp()
    }
}


extension InterstitialManager: YMAInterstitialAdDelegate {
    func interstitialAd(_ interstitialAd: YMAInterstitialAd, didFailToShowWithError error: Error) {
        rejecter?(FailedToShowException(error.localizedDescription))
        cleanUp()
    }
    
    func interstitialAdDidDismiss(_ interstitialAd: YMAInterstitialAd) {
        resolver?([
            "didClick": didClick,
            "trackImpression": trackImpression,
        ])
        cleanUp()
    }
    
    func interstitialAdDidClick(_ interstitialAd: YMAInterstitialAd) {
        didClick = true
    }
    
    func interstitialAd(_ interstitialAd: YMAInterstitialAd, didTrackImpressionWith impressionData: YMAImpressionData?) {
        trackImpression = true
    }
}

internal class MissingCurrentViewControllerException: Exception {
    override var reason: String {
        "Cannot determine currently presented view controller"
    }
}

internal class FailedToShowException: GenericException<String?> {
    override var reason: String {
        "Failed to show interstitial: '\(param ?? "nil")'"
    }
}


internal class FailedToLoadException: GenericException<String?> {
    override var reason: String {
        "Failed to load interstitial: '\(param ?? "nil")'"
    }
}
