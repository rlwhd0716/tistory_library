//
//  RegisterCodeViewController.swift
//  DI_Test
//
//  Created by wini on 2021/05/24.
//

import Foundation
import UIKit

class RegisterCodeViewController: UIViewController, BKPagingSliderDelegate {
    @IBOutlet var steperView: BKPagingSlider!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        steperView.titleArray = ["기본정보\n입력", "번호인증 및\n약관동의", "가입완료"]
        steperView.changeViewSelection(tag: 1)
    }
    
    func canChangeSliderPosition(to index: Int, stepper: BKPagingSlider) -> Bool {
        false
    }
    
}
