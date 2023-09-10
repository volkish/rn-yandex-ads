//
//  Exceptions.swift
//  RnYandexAds
//
//  Created by Artem Ivanov on 08.09.2023.
//

import Foundation
import ExpoModulesCore

internal class InitializationRequiredException: Exception {
    override var reason: String {
        "Initialization is required before use"
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
