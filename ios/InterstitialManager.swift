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
    
    private var interstitialAd: InterstitialAd?
    private lazy var interstitialAdLoader: InterstitialAdLoader = {
        let loader = InterstitialAdLoader()
        loader.delegate = self
        return loader
    }()
    
    private var promise: Promise?
    private var didClick = false
    private var trackImpression: ImpressionData?
    
    func showAd(
        _ adUnitID: String,
        withPromise promise: Promise
    ) {
        self.promise = promise
        
        interstitialAdLoader.loadAd(
            with: AdRequestConfiguration(adUnitID: adUnitID)
        )
    }
    
    private func cleanUp () {
        promise = nil
        didClick = false
        trackImpression = nil
        interstitialAd = nil
    }

    deinit {
        cleanUp()
    }
}


extension InterstitialManager: InterstitialAdLoaderDelegate {
    func interstitialAdLoader(_ adLoader: InterstitialAdLoader, didLoad interstitialAd: InterstitialAd) {
        guard let viewController = RCTPresentedViewController() else {
            promise?.reject(MissingCurrentViewControllerException())
            cleanUp()
            
            return;
        }
        
        self.interstitialAd = interstitialAd
        self.interstitialAd!.delegate = self
        self.interstitialAd!.show(from: viewController)
    }
    
    func interstitialAdLoader(_ adLoader: InterstitialAdLoader, didFailToLoadWithError error: AdRequestError) {
        let id = error.adUnitId
        let error = error.error

        promise?.reject(FailedToLoadException("Ad with Unit ID: \(String(describing: id)). Error: \(String(describing: error))"))
        cleanUp()
    }
}


extension InterstitialManager: InterstitialAdDelegate {
    func interstitialAd(_ interstitialAd: InterstitialAd, didFailToShowWithError error: Error) {
        promise?.reject(FailedToShowException(error.localizedDescription))
        cleanUp()
    }
    
    func interstitialAdDidDismiss(_ interstitialAd: InterstitialAd) {
        promise?.resolve([
            "didClick": didClick,
            "impressionData": convertImpressionData(trackImpression),
        ] as [String : Any])
        cleanUp()
    }
    
    func interstitialAdDidClick(_ interstitialAd: InterstitialAd) {
        didClick = true
    }
    
    func interstitialAd(_ interstitialAd: InterstitialAd, didTrackImpressionWith impressionData: ImpressionData?) {
        trackImpression = impressionData
    }
}
