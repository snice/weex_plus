//
//  WXEventModule.swift
//  demo
//
//  Created by zhe zhu on 2017/1/24.
//  Copyright © 2017年 zhu zhe. All rights reserved.
//

import WeexSDK

class WXEventModule : NSObject, WXEventModuleProtocol{
    var weexInstance:WXSDKInstance?;
    static func wx_export_method_openURL() -> String{
        return NSStringFromSelector(#selector(self.openURL(_:)))
    }
    func openURL(_ url: String!) {
        var newURL:String! = url;
        if (url.hasPrefix("//")) {
            newURL = String.localizedStringWithFormat("http:%@", url);
        } else if (!url.hasPrefix("http")) {
            newURL = NSURL.init(string: url, relativeTo: weexInstance?.scriptURL)?.absoluteString
        }
        NSLog("==url:%@", newURL)
        let controller = WeexViewController();
        controller.openUrl = newURL
        weexInstance?.viewController.navigationController?.pushViewController(controller, animated: true);
    }
}
