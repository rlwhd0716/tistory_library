//
//  WiniDownloadHelper.swift
//  WiniWebView
//
//  Created by Winitech on 2021/02/09.
//

import Foundation
import WebKit
import QuickLook

protocol WKWebViewDownloadHelperDelegate {
    func fileDownloadedAtURL(url:URL)
}

class WKWebviewDownloadHelper : NSObject {
    var webView:WiniWebView
    var viewController:UIViewController
    var mimeTypes:[String]
    var delegate:WKWebViewDownloadHelperDelegate
    var documentUrl = URL(fileURLWithPath: "")
    var documentDownloadTask: URLSessionTask?
    var downloadHelper:WKWebviewDownloadHelper!
    var documentPreviewController = QLPreviewController()
    
    init(viewController: UIViewController ,webView:WiniWebView, mimeTypes:[String], delegate:WKWebViewDownloadHelperDelegate) {
        self.webView = webView
        self.mimeTypes = mimeTypes
        self.delegate = delegate
        self.viewController = viewController
        super.init()
    }
    
    func downloadData(fromURL url:URL,
                              fileName:String,
                              completion:@escaping (Bool, URL?) -> Void) {
        webView.configuration.websiteDataStore.httpCookieStore.getAllCookies() { cookies in
            let session = URLSession.shared
            session.configuration.httpCookieStorage?.setCookies(cookies, for: url, mainDocumentURL: nil)
            let task = session.downloadTask(with: url) { localURL, urlResponse, error in
                if let localURL = localURL {
                    let destinationURL = self.moveDownloadedFile(url: localURL, fileName: fileName)
                    completion(true, destinationURL)
                }
                else {
                    completion(false, nil)
                }
            }
            
            task.resume()
        }
    }
    
    func getFileNameFromResponse(_ response:URLResponse) -> String? {
        if let httpResponse = response as? HTTPURLResponse {
            let headers = httpResponse.allHeaderFields
            if let disposition = headers[AnyHashable("Content-Disposition")] as? String {
                let components = disposition.components(separatedBy: " ")
                if components.count > 1 {
                    let innerComponents = components[1].components(separatedBy: "=")
                    if innerComponents.count > 1 {
                        if innerComponents[1].contains("filename") {
                            return innerComponents[1]
                        } else {
                            return response.suggestedFilename!
                        }
                    } else {
                        return response.suggestedFilename!
                    }
                } else {
                    return response.suggestedFilename!
                }
            }
        }
        return nil
    }
    
    func isMimeTypeConfigured(_ mimeType:String) -> Bool {
        for type in self.mimeTypes {
            if mimeType.contains(type) {
                return true
            }
        }
        return false
    }
    
    func moveDownloadedFile(url:URL, fileName:String) -> URL {
        let tempDir = NSTemporaryDirectory()
        let destinationPath = tempDir + fileName
        let destinationURL = URL(fileURLWithPath: destinationPath)
        try? FileManager.default.removeItem(at: destinationURL)
        try? FileManager.default.moveItem(at: url, to: destinationURL)
        return destinationURL
    }
    
    func startDownload(decidePolicyFor navigationResponse: WKNavigationResponse, _ completion: @escaping (Bool) -> Void) {
        DispatchQueue.main.async { [self] in
            if let mimeType = navigationResponse.response.mimeType {
                if isMimeTypeConfigured(mimeType) {
                    if let url = navigationResponse.response.url {
                        let fileName = getFileNameFromResponse(navigationResponse.response) ?? "default"
                        downloadData(fromURL: url, fileName: fileName) { [self] success, destinationURL in
                            if success, let destinationURL = destinationURL {
                                print(destinationURL)
                                fileDownloadedAtURL(url: destinationURL)
                            }
                        }
                        completion(true)
                        return
                    }
                }
            }
            completion(false)
        }
    }
}

extension WKWebviewDownloadHelper: WKWebViewDownloadHelperDelegate {
    func fileDownloadedAtURL(url: URL) {
        DispatchQueue.main.async {
            self.documentUrl = url
            self.documentPreviewController.refreshCurrentPreviewItem()
            self.viewController.present(self.documentPreviewController, animated: true, completion: nil)
        }
    }
}
