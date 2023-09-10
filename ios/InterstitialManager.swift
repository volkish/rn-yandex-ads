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
    
    private var promise: Promise?
    private var didClick = false
    private var trackImpression = false
    
    func showAd(
        _ adUnitID: String,
        withPromise promise: Promise
    ) {
        self.promise = promise
        
        interstitialAdLoader.loadAd(
            with: YMAAdRequestConfiguration(adUnitID: adUnitID)
        )
    }
    
    private func cleanUp () {
        promise = nil
        didClick = false
        trackImpression = false
        interstitialAd = nil
    }
}


extension InterstitialManager: YMAInterstitialAdLoaderDelegate {
    func interstitialAdLoader(_ adLoader: YMAInterstitialAdLoader, didLoad interstitialAd: YMAInterstitialAd) {
        guard let viewController = RCTPresentedViewController() else {
            promise?.rejecter(MissingCurrentViewControllerException())
            cleanUp()
            
            return;
        }
        
        self.interstitialAd = interstitialAd
        self.interstitialAd!.delegate = self
        self.interstitialAd!.show(from: viewController)
    }
    
    func interstitialAdLoader(_ adLoader: YMAInterstitialAdLoader, didFailToLoadWithError error: YMAAdRequestError) {
        promise?.reject(FailedToLoadException(error.description))
        cleanUp()
    }
}


extension InterstitialManager: YMAInterstitialAdDelegate {
    func interstitialAd(_ interstitialAd: YMAInterstitialAd, didFailToShowWithError error: Error) {
        promise?.reject(FailedToShowException(error.localizedDescription))
        cleanUp()
    }
    
    func interstitialAdDidDismiss(_ interstitialAd: YMAInterstitialAd) {
        promise?.resolve([
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
