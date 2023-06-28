//
//  InputAlertViewController.swift
//  CustomAlert
//
//  Created by wini on 2020/05/27.
//  Copyright © 2020 wini. All rights reserved.
//

import UIKit

public class InputAlertViewController: UIViewController,UITextViewDelegate {

    @IBOutlet weak var alertMainView: UIView!
    @IBOutlet weak var mTitle: UILabel!
    @IBOutlet weak var mMessage: UILabel!
    @IBOutlet weak var mTextView: UITextView!
    @IBOutlet weak var mActionBtn: UIButton!
    @IBOutlet weak var mCancleBtn: UIButton!
    
    var _title = String()
    var message = String()
    var inputText = String()
    var actionName = String()
    var cancelName = "취소"
    var confirm:((String)->Void)?
    var cancel:(()->Void)?
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        setView()
    }
    
    func setView(){
        self.mTextView.delegate = self
        self.mTextView.autocapitalizationType = .none
        self.mTextView.autocorrectionType = .no
        
        if #available(iOS 13.0, *) {
            self.makeConer(sender: self.alertMainView as Any)
//            self.makeConer(sender: self.mTextView as Any)
        } else {
        
        }
        
        self.mTitle.text = _title
        self.mMessage.text = message
        self.mTextView.text = inputText
        self.mActionBtn.setTitle(actionName, for: .normal)
        self.mCancleBtn.setTitle(cancelName, for: .normal)
    }
    
    @available(iOS 13.0, *)
    func makeConer(sender:Any){
        (sender as AnyObject).layer.borderWidth = 1
        (sender as AnyObject).layer.borderColor = UIColor.clear.cgColor
        (sender as AnyObject).layer.cornerRadius = 20
        (sender as AnyObject).layer.masksToBounds = true
    }
    
    @IBAction func onCancle(_ sender: UIButton) {
        cancel?()
        dismiss(animated: true)
    }
    
    @IBAction func onAction(_ sender: UIButton) {
        confirm?(self.mTextView.text ?? "")
        dismiss(animated: true)
    }
    
    //키보드 done 키 입력 시 키보드 숨기기
    public func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        if (text == "\n") {
            self.view.endEditing(true)
        }
        return true
    }
    
    //배경 터치시 키보드 내리기
    public override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?){
        self.view.endEditing(true)
    }
}
