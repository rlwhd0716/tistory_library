//
//  WiniWebViewPreloader.swift
//  WiniWebView
//
//  Created by Winitech on 2021/02/08.
//

import Foundation
import UIKit
import WebKit

class WiniWebViewPreloader: NSObject, WKNavigationDelegate {
    private var handler: (WKWebView) -> Void
    
    init(webView: WiniWebView, handler: @escaping (WKWebView) -> Void) {
        self.handler = handler
        super.init()
        webView.wkNavigationDelegate = self
    }
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        handler(webView)
    }
}

public extension WiniWebView {
    enum AssociatedKeys {
        static var preloader: String = "preloader"
    }
    
    private var preloader: WiniWebViewPreloader? {
        get {
            return objc_getAssociatedObject(self, &AssociatedKeys.preloader) as? WiniWebViewPreloader
        }
        set {
            objc_setAssociatedObject(self, &AssociatedKeys.preloader, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
    
    @objc
    class func preloadWithDomainForCookieSync(urlString: String, completion: (() -> Void)? = nil) {
        guard let url = URL(string: urlString),
              let window = UIApplication.shared.keyWindow else {
            completion?()
            return
        }
        
        let webView = WiniWebView(frame: .zero)
        webView.alpha = 0.1
        webView.preloader = WiniWebViewPreloader(webView: webView, handler: { view in
            completion?()
            view.removeFromSuperview()
        })
        webView.load(URLRequest(url: url))
        window.addSubview(webView)
        
    }
}
