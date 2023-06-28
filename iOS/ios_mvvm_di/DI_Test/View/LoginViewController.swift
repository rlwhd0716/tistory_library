//
//  LoginViewController.swift
//  DI_Test
//
//  Created by wini on 2021/05/18.
//

import Foundation
import UIKit

class LoginViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    @IBAction func didTapAutoLogin(_ sender: UIButton) {
        sender.isSelected.toggle()
    }
}
