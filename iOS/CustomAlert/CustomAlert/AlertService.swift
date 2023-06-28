//
//  AlertService.swift
//  CustomAlert
//
//  Created by wini on 2020/05/25.
//  Copyright © 2020 wini. All rights reserved.
//

import Foundation
import UIKit

open class AlertService {
    public init() {}
    
    public static let instance = AlertService()
    let frameworkBundle = Bundle(identifier: "com.winitech.CustomAlert")
    
    // FrameWork Bundle Name Setting
    // https://cauca.tistory.com/24
    public func alertNotice(title:String, message:String, actionName:String, compltion: @escaping () -> Void) -> AlertViewController{
        let storyboard = UIStoryboard.init(name: "AlertStoryboard", bundle: frameworkBundle)
        let AlertVC = storyboard.instantiateViewController(withIdentifier: "AlertVC") as! AlertViewController
        
        AlertVC._title = title
        AlertVC.message = message
        AlertVC.actionName = actionName
        AlertVC.confirm = compltion
        return AlertVC
    }
    
    public func alertNotice(title:String, message:String, actionName:String, actionCompltion: @escaping () -> Void, cancelName:String, cancelCompltion: @escaping () -> Void) -> AlertViewController{
        let storyboard = UIStoryboard.init(name: "AlertStoryboard", bundle: frameworkBundle)
        let AlertVC = storyboard.instantiateViewController(withIdentifier: "AlertVC") as! AlertViewController
        
        AlertVC._title = title
        AlertVC.message = message
        AlertVC.actionName = actionName
        AlertVC.confirm = actionCompltion
        AlertVC.cancelName = cancelName
        AlertVC.cancel = cancelCompltion
        return AlertVC
    }
    
    public func alertInput(title:String, message:String, inputText:String, actionName:String, compltion: @escaping (String) -> Void) -> InputAlertViewController{
        let storyboard = UIStoryboard.init(name: "InputAlertStoryboard", bundle: frameworkBundle)
        let InputAlertVC = storyboard.instantiateViewController(withIdentifier: "InputAlertVC") as! InputAlertViewController
        
        InputAlertVC._title = title
        InputAlertVC.message = message
        InputAlertVC.actionName = actionName
        InputAlertVC.inputText = inputText
        InputAlertVC.confirm = compltion
        return InputAlertVC
    }
    
    public func alertInput(title:String, message:String, inputText:String, actionName:String, actionCompltion: @escaping (String) -> Void, cancelName:String, cancelCompltion: @escaping () -> Void) -> InputAlertViewController{
        let storyboard = UIStoryboard.init(name: "InputAlertStoryboard", bundle: frameworkBundle)
        let InputAlertVC = storyboard.instantiateViewController(withIdentifier: "InputAlertVC") as! InputAlertViewController
        
        InputAlertVC._title = title
        InputAlertVC.message = message
        InputAlertVC.inputText = inputText
        InputAlertVC.actionName = actionName
        InputAlertVC.confirm = actionCompltion
        InputAlertVC.cancelName = cancelName
        InputAlertVC.cancel = cancelCompltion
        return InputAlertVC
    }
    
    public func presentAlertOnPad(_ viewController: UIViewController, actionSheet: UIAlertController, animated: Bool) {
        if UIDevice.current.userInterfaceIdiom == .pad {
            if let popoverController = actionSheet.popoverPresentationController {
                popoverController.sourceView = viewController.view
                popoverController.sourceRect = CGRect(x: viewController.view.bounds.midX, y: viewController.view.bounds.midY, width: 0, height: 0)
                popoverController.permittedArrowDirections = []
            }
            viewController.present(actionSheet, animated: animated)
        } else {
            viewController.present(actionSheet, animated: animated)
        }
    }
}
