//
//  AlamoViewModel.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/09.
//

import Foundation

class AlamoViewModel: AlamoViewModeling {
    var apiResponse: APIResponse!
    
    let alamo:AlamoNetworking
    init(alamo:AlamoNetworking) {
        self.alamo = alamo
    }
    
    func getAddressData(address: String, _ completion: @escaping ([Addresses]) -> Void) {
        alamo.getDataCodable(request:alamo.requestGet(params: ["query" : address]), data: APIResponse(), {
            value in
            completion(value.addresses!)
            print(value)
        })
    }
}
