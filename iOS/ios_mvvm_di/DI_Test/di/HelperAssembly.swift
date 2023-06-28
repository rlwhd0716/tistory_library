//
//  HelperAssembly.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/01.
//

import Foundation
import Swinject
import CoreLocation

class HelperAssembly: Assembly {
    func assemble(container: Container) {
        container.register(UIApplication.self) { _ in UIApplication.shared }
        container.register(UserDefaults.self) { _ in UserDefaults.standard }
        container.register(Bundle.self) { _ in Bundle.main }
        container.register(FileManager.self) { _ in FileManager.default }
        container.register(UIDevice.self) { _ in UIDevice.current }
        container.register(CLLocationManager.self) { _ in CLLocationManager.init() }
    }
}
