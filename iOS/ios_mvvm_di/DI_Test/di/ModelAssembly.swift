//
//  ModelAssembly.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/07.
//

import Foundation
import Swinject
import SwinjectStoryboard

class ModelAssembly: Assembly {
    func assemble(container: Container) {
        // Network
        container.register(AlamoNetworking.self) {
            _ in AlamoNetwork(
                url: "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode",
                headers: [
                    "X-NCP-APIGW-API-KEY-ID":"phqbnfaznb",
                    "X-NCP-APIGW-API-KEY":"fQqcRPG0S5vE3JfQQqJwotLC0TkfeUeq5sl6rp7G",
                    "Accept":"application/json"])
        }.inObjectScope(.container)
    }
}
