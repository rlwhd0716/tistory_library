//
//  AnotherViewModel.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/09.
//

import Foundation
import UIKit

class AnotherViewModel: AnotherViewModeling {
    var device:UIDevice!
    init(device:UIDevice) {
        self.device = device
    }
    
    func getDeviceInfo() -> Dictionary<String, Any> {
        return [
            "name":device.name,
            "model":device.model,
            "localizeModel":device.localizedModel,
            "systemName":device.systemName,
            "systemVersion":device.systemVersion,
            "orientation":device.orientation,
            "identifierForVendor":device.identifierForVendor,
            "isGeneratingDeviceOrientationNotifications":device.isGeneratingDeviceOrientationNotifications,
            "isBatteryMonitoringEnabled":device.isBatteryMonitoringEnabled,
            "batteryState":device.batteryState,
            "batteryLevel":device.batteryLevel,
            "isProximityMonitoringEnabled":device.isProximityMonitoringEnabled,
            "proximityState":device.proximityState,
            "isMultitaskingSupported":device.isMultitaskingSupported,
            "userInterfaceIdiom":device.userInterfaceIdiom
        ]
    }
}
