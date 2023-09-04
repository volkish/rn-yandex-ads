//
//  InitializeOptions.swift
//  RnYandexAds
//
//  Created by Artem Ivanov on 04.09.2023.
//

import Foundation
import ExpoModulesCore

struct InitializeOptions: Record {
    @Field
    var userConsent: Bool = false
    
    @Field
    var locationConsent: Bool = false
    
    @Field
    var enableLogging: Bool = false
    
    @Field
    var enableDebugErrorIndicator: Bool = false
}

extension InitializeOptions: CustomStringConvertible {
    var description: String {
        return "(userConsent: \(userConsent), locationConsent: \(locationConsent), enableLogging: \(enableLogging), enableDebugErrorIndicator: \(enableDebugErrorIndicator))"
    }
}
