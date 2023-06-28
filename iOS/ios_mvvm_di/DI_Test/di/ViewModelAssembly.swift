//
//  ViewModelAssembly.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/07.
//

import Foundation
import Swinject
import SwinjectStoryboard

class ViewModelAssembly: Assembly {
    func assemble(container: Container) {
        // Alamo
        container.register(AlamoViewModeling.self) { resolver in
            AlamoViewModel(alamo: resolver.resolve(AlamoNetworking.self)!)
        }.inObjectScope(.container)
        
        // Another
        container.register(AnotherViewModeling.self) { resolver in
            AnotherViewModel(device: resolver.resolve(UIDevice.self)!)
        }.inObjectScope(.container)
        
        // Geotifications
        container.register(GeotificationsViewModeling.self) { resolver in
            GeotificationsViewModel()
        }.inObjectScope(.container)
        
        
        // Add Geotifications
        container.register(AddGeotificationViewModeling.self) { resolver in
            AddGeotificationViewModel()
        }.inObjectScope(.container)
    }
}

