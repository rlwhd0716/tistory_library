//
//  ViewAssembly.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/07.
//

import Foundation
import Swinject
import SwinjectStoryboard
import CoreLocation

class ViewAssembly: Assembly {
    func assemble(container: Container) {
        // Alamo
        container.storyboardInitCompleted(AlamoViewController.self) { resolver, controller in
            controller.viewModel = resolver.resolve(AlamoViewModeling.self)!
            controller.defaults = resolver.resolve(UserDefaults.self)!
        }
        
        // Another
        container.storyboardInitCompleted(AnotherViewController.self) { resolver, controller in
            controller.viewModel = resolver.resolve(AnotherViewModeling.self)!
        }
        
        // Geotifications
        container.storyboardInitCompleted(GeotificationsViewController.self) { resolver, controller in
            controller.viewModel = resolver.resolve(GeotificationsViewModeling.self)!
            controller.mLocationManager = resolver.resolve(CLLocationManager.self)!
        }
        
        // Geotifications
        container.storyboardInitCompleted(AddGeotificationViewController.self) { resolver, controller in
            controller.viewModel = resolver.resolve(AddGeotificationViewModeling.self)!
        }
    }
}
