//
//  DependencyProvider.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/07.
//

import Foundation
import Swinject

class DependencyProvider {
    let assembler: Assembler
    
    init(_ container: Container) {
        assembler = Assembler(
            [
                HelperAssembly(),
                ViewModelAssembly(),
                ViewAssembly(),
                ModelAssembly()
            ],
            container: container
        )
    }
}
