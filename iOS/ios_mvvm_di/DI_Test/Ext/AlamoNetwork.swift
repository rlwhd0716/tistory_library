//
//  AlamoNetwork.swift
//  NetworkExt
//
//  Created by Winitech on 2021/03/25.
//

import Foundation
import Alamofire

class AlamoNetwork: AlamoNetworking {
    private var API_URL:URL!
    private let mEncoder = JSONEncoder()
    private var mHeaders:HTTPHeaders?
    var mInterceptor:RequestInterceptor?
    
    init(url:String,
         headers:HTTPHeaders? = nil,
         interceptor:RequestInterceptor? = nil) {
        self.API_URL = URL(string: url)!
        self.mHeaders = headers
        self.mInterceptor = interceptor
    }
    
    func requestGet(params:Parameters? = nil) -> DataRequest {
        return AF.request(API_URL,
                   method: .get,
                   parameters: params,
                   headers: mHeaders,
                   interceptor: mInterceptor)
    }
    
    func requestPost(params:Parameters? = nil) -> DataRequest {
        return AF.request(API_URL,
                   method: .post,
                   parameters: params,
                   headers: mHeaders,
                   interceptor: mInterceptor)
    }
    
    func response<T: DataRequest>(request: T, completion: @escaping (Any) -> Void) {
        request.responseJSON { value in
            switch value.result {
            case .success(let value):
                completion(value)
                break
                
            case .failure(let error):
                print("Error - \(error)")
                break
            }
        }
    }
    
    func getDataCodable<T: Codable>(request: DataRequest, data:T, _ completion: @escaping (T) -> Void) {
        response(request: request, completion: { value in
            do {
                // value 를 JSON으로 변경
                let jsonData = try JSONSerialization.data(withJSONObject: value, options: .prettyPrinted)
                // JSON Decoder 사용 (Codable)
                // 디코딩 할때 변수의 타입이 맞지 않으면 디코딩이 안될수도 있음.
                let data = try JSONDecoder().decode(T.self, from: jsonData)
                
                completion(data)
            } catch(let err) {
                print(err.localizedDescription)
            }
        })
    }
    
    func requestUpload(
        _ params:Dictionary<String,String>,
        document:Data,
        withName:String? = "pdfDocuments",
        fileName:String,
        mimeType:String? = "application/pdf") -> UploadRequest {
        return AF.upload(multipartFormData: { multipartFormData in
            for (key, value) in params {
                multipartFormData.append("\(value)".data(using: String.Encoding.utf8)!, withName: key as String)
            }
            multipartFormData.append(document, withName: withName!, fileName: fileName, mimeType: mimeType!)
        }, to: API_URL, usingThreshold: UInt64.init(), method: .post, headers: mHeaders)
    }
}
