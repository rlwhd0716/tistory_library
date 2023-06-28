//
//  CustomTextField.swift
//  DI_Test
//
//  Created by wini on 2021/05/18.
//

import Foundation
import UIKit

class CustomTextField : UITextField {
    
    override func canPerformAction(_ action: Selector, withSender sender: Any?) -> Bool {
        switch action {
        case #selector(UIResponderStandardEditActions.paste(_:)): return false
        default:
            return super.canPerformAction(action, withSender: sender)
        }
    }
}
