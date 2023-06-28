//
//  AlamoViewModeling.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/09.
//

import Foundation


protocol AlamoViewModeling {
    func getAddressData(address:String,
                        _ completion: @escaping ([Addresses]) -> Void)
}
