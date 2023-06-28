//
//  AlamoNetworking.swift
//  FWTA
//
//  Created by Winitech on 2021/03/30.
//

import Foundation
import Alamofire

protocol AlamoNetworking {
    
    func requestGet(params:Parameters?) -> DataRequest
    
    func requestPost(params:Parameters?) -> DataRequest
    
    func response<T: DataRequest>(request:T,
                                  completion: @escaping (Any) -> Void)
    
    func getDataCodable<T: Codable>(request:DataRequest,
                                    data:T,
                                    _ completion: @escaping (T) -> Void)
    
    func requestUpload(_ params:Dictionary<String,String>,
                       document:Data,
                       withName:String?,
                       fileName:String,
                       mimeType:String?) -> UploadRequest
}
