//
//  GeotificationsViewModeling.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/13.
//

import Foundation
import MapKit

protocol GeotificationsViewModeling {
    var mGeotifications:[Geotification] { get }
    
    func addGeotifications(geotification: Geotification)
    func removeGeotifications(geotification: Geotification, index: Int)
    func loadAllGeotifications(_ completion: @escaping (([Geotification]) -> Void))
    func saveAllGeotifications()
    func getGeotificationsCount() -> Int
}
