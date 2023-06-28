//
//  APIResponse.swift
//  FWTA
//
//  Created by Winitech on 2021/03/31.
//

import Foundation

struct APIResponse: Codable {
    var addresses: [Addresses]?
    var errorMessage: String?
    var meta: Meta?
    var status: String?
}

struct Addresses: Codable {
    var addressElements: [AddressElements]?
    var distance: Double?
    var englishAddress: String?
    var jibunAddress: String?
    var roadAddress: String?
    var x: String?
    var y: String?
}

struct AddressElements: Codable {
    var code: String?
    var longName: String?
    var shortName: String?
    var types: [String]?
}

struct Meta: Codable {
    var count: Int?
    var page: Int?
    var totalCount: Int?
}
