//
//  ImpressionDataConverter.swift
//  RnYandexAds
//
//  Created by Artem Ivanov on 15.09.2023.
//

import Foundation
import YandexMobileAds

struct xImpressionDataNetwork: Codable {
    var name: String
    var adapter: String
    var ad_unit_id: String
}

struct xImpressionData: Codable {
    var currency: String
    var revenueUSD: String
    var precision: String
    var revenue: String
    var requestId: String
    var blockId: String
    var adType: String
    var ad_unit_id: String
    var network: xImpressionDataNetwork
}

func convertImpressionData(_ impressionData: ImpressionData?) -> [String: Any] {
    do {
        if let encodedData = impressionData,
           let binaryEncodedData = encodedData.rawData.data(using: .utf8)
        {
            let data = try JSONDecoder().decode(xImpressionData.self, from: binaryEncodedData)
            
            return [
                "currency": data.currency,
                "revenueUSD": data.revenueUSD,
                "precision": data.precision,
                "revenue": data.revenue,
                "requestId": data.requestId,
                "blockId": data.blockId,
                "adType": data.adType,
                "ad_unit_id": data.ad_unit_id,
                "network": [
                    "name": data.network.name,
                    "adapter": data.network.adapter,
                    "ad_unit_id": data.network.ad_unit_id,
                ],
            ];
        }
    } catch {
    }
    
    return [:];
}
