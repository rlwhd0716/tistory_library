//
//  RegisterViewController.swift
//  DI_Test
//
//  Created by wini on 2021/05/20.
//

import Foundation
import UIKit

class RegisterViewController: UIViewController, BKPagingSliderDelegate {
    
    @IBOutlet var steperView: BKPagingSlider!
    @IBOutlet var distinctCheckButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        steperView.sliderDelegate = self
        distinctCheckButton.layer.cornerRadius = 5
        
        steperView.titleArray = ["기본정보\n입력", "번호인증 및\n약관동의", "가입완료"]
    }
    
    func canChangeSliderPosition(to index: Int, stepper: BKPagingSlider) -> Bool {
        return false
    }
}
