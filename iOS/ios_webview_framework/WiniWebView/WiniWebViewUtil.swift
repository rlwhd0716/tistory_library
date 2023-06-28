//
//  WiniWebViewUtil.swift
//  WiniWebView
//
//  Created by Winitech on 2021/02/09.
//

import Foundation
import UIKit

class WiniWebViewUtil {
    var currentVC:UIViewController!
    var mWebView:WiniWebView!
    
    init(_ viewController: UIViewController, webView: WiniWebView) {
        currentVC = viewController
        mWebView = webView
    }
    
    func loadRequest(url: String) {
        mWebView.load(URLRequest(url: URL(string: url)!))
    }
    
    func pageAnimation(type: Int) {
        let animation = CATransition()
        animation.delegate = (currentVC as! CAAnimationDelegate)
        animation.duration = 0.3
        animation.timingFunction = CAMediaTimingFunction.init(name: CAMediaTimingFunctionName.linear)
        animation.type = .moveIn
        animation.subtype = .fromRight
        
        animation.isRemovedOnCompletion = false
        animation.fillMode = CAMediaTimingFillMode(rawValue: "extended")
        mWebView.layer.add(animation, forKey: "WebPageMoveIn")
    }
}
