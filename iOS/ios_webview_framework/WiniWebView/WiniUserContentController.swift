//
//  WiniUserContentController.swift
//  WiniWebView
//
//  Created by Winitech on 2021/02/10.
//

import Foundation
import WebKit

protocol WiniScriptDelegate : class {}

class WiniMessageHandler: NSObject, WKScriptMessageHandler {
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        print(message)
    }
}

