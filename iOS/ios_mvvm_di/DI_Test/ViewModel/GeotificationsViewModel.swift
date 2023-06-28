//
//  GeotificationsViewModel.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/13.
//

import Foundation
import MapKit

class GeotificationsViewModel: GeotificationsViewModeling {
    
    var mGeotifications: [Geotification] = []
    
    func addGeotifications(geotification: Geotification) {
        mGeotifications.append(geotification)
    }
    
    func removeGeotifications(geotification: Geotification, index: Int) {
        mGeotifications.remove(at: index)
    }
    
    // MARK: Loading and saving functions
    func loadAllGeotifications(_ completion: @escaping ([Geotification]) -> Void) {
        mGeotifications.removeAll()
      let allGeotifications = Geotification.allGeotifications()
      completion(allGeotifications)
    }

    func saveAllGeotifications() {
      let encoder = JSONEncoder()
      do {
        let data = try encoder.encode(mGeotifications)
        UserDefaults.standard.set(data, forKey: PreferencesKeys.savedItems.rawValue)
      } catch {
        print("error encoding geotifications")
      }
    }
    
    func getGeotificationsCount() -> Int {
        return mGeotifications.count
    }
    
}
