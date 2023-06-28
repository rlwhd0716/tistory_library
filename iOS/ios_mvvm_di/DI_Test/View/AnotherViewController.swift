//
//  AnotherViewController.swift
//  DI_Test
//
//  Created by Winitech on 2021/03/31.
//

import UIKit

class AnotherViewController: UIViewController {
    public var viewModel: AnotherViewModeling? {
        didSet {
            if let viewModel = viewModel {
                let device = viewModel.getDeviceInfo()
                print(device)
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        print("coder")
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("anotherView")
        
    }
}

