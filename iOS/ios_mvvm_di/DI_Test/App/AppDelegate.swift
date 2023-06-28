//
//  AppDelegate.swift
//  DI_Test
//
//  Created by Winitech on 2021/03/31.
//

import UIKit
import Swinject
import Alamofire
import SwinjectStoryboard

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var container = Container()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        let _ = DependencyProvider(container)
        return true
    }
    
    // MARK: UISceneSession Lifecycle
    
    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }
    
    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
    
    
    
    
    
    
//
//    func registerDependencies() {
//        self.container.register(AppInjection.self) {
//            _ in AppDependency(self.container)
//        }.initCompleted({ [self] resolver, app in
//            resolver.resolve(AppInjection.self)?.inject()
//
//
//            // 등록해 주어야 한다. 해당 스토리보드를 사용하겠다고 등록하는 부분.
//            let sb = SwinjectStoryboard.create(name: "Main", bundle: nil, container: container)
//            // 스토리보드에서 어떤 Controller를 쓸것인지 선택
//            let alamoController = sb.instantiateViewController(withIdentifier: "AlamoVC") as! AlamoViewController
//            let anotherController = sb.instantiateViewController(withIdentifier: "AnotherVC") as! AnotherViewController
//            alamoController.alamoVM.getDataCodable(request:alamoController.alamoVM.requestGet(params: ["query" : "송현로 205"]), data: APIResponse(), {
//                value in
//                print("===========================alamo===========================")
//                print(value) // 이런 형태로 초기값 설정을 서버에서 가져올 수 있음.
//            })
//
//            anotherController.alamoVM.getDataCodable(request:alamoController.alamoVM.requestGet(params: ["query" : "송현로 205"]), data: APIResponse(), {
//                value in
//                print("===========================another===========================")
//                print(value) // 이런 형태로 초기값 설정을 서버에서 가져올 수 있음.
//            })
//        })
//    }
}

