//
//  ImpressionDataConverter.swift
//  RnYandexAds
//
//  Created by Artem Ivanov on 15.09.2023.
//

import Foundation
import YandexMobileAds

struct ImpressionDataNetwork: Codable {
    var name: String
    var adapter: String
    var ad_unit_id: String
}

struct ImpressionData: Codable {
    var currency: String
    var revenueUSD: String
    var precision: String
    var revenue: String
    var requestId: String
    var blockId: String
    var adType: String
    var ad_unit_id: String
    var network: ImpressionDataNetwork
}

func convertImpressionData(_ impressionData: YMAImpressionData?) -> [String: Any] {
    do {
        if let encodedData = impressionData,
           let binaryEncodedData = encodedData.rawData.data(using: .utf8)
        {
            let data = try JSONDecoder().decode(ImpressionData.self, from: binaryEncodedData)
            
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
