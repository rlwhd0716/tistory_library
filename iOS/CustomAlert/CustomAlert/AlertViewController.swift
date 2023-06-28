//
//  AlertViewController.swift
//  CustomAlert
//
//  Created by wini on 2020/05/25.
//  Copyright © 2020 wini. All rights reserved.
//

import UIKit

public class AlertViewController: UIViewController {

    @IBOutlet weak var AlertMainView: UIView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var actionBtn: UIButton!
    @IBOutlet weak var cancleBtn: UIButton!
    
    var _title = String()
    var message = String()
    var actionName = String()
    var cancelName = "취소"
    var confirm:(()->Void)?
    var cancel:(()->Void)?
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        setView()
    }
    
    func setView(){
        if #available(iOS 13.0, *) {
            self.makeConer(sender: self.AlertMainView as Any)
        } else {
        
        }
        
        if cancelName == "" {
            cancleBtn.isHidden = true
        }
        
        self.titleLabel.text = _title
        self.messageLabel.text = message
        self.actionBtn.setTitle(actionName, for: .normal)
        self.cancleBtn.setTitle(cancelName, for: .normal)
    }
    
    @available(iOS 13.0, *)
    func makeConer(sender:Any){
        (sender as AnyObject).layer.borderWidth = 1
        (sender as AnyObject).layer.borderColor = UIColor.clear.cgColor
        (sender as AnyObject).layer.cornerRadius = 20
        (sender as AnyObject).layer.masksToBounds = true
    }
    
    @IBAction func cancelAction(_ sender: Any) {
        cancel?()
        dismiss(animated: true)
    }
    
    @IBAction func action(_ sender: Any) {
        confirm?()
        dismiss(animated: true)
    }
    
}
