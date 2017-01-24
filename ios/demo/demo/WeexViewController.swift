//
//  WeexViewController.swift
//  demo
//
//  Created by zhe zhu on 2017/1/24.
//  Copyright © 2017年 zhu zhe. All rights reserved.
//

import UIKit
import WeexSDK

class WeexViewController : UIViewController{
    var script:String?
    var openUrl:String!
    var source:String?
    //    var hotReloadSocket:SRWebSocket:
    
    var instanceWX: WXSDKInstance?
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white;
//        NotificationCenter.default.addObserver(self, selector: #selector(self.notificationRefreshInstance(_:)), name: NSNotification.Name(rawValue: "RefreshInstance"), object: nil);
        self.render();
    }
    func render(){
        instanceWX = WXSDKInstance()
        instanceWX?.viewController = self
        instanceWX?.frame = CGRect.init(x: 0, y: 64, width: self.view.frame.size.width, height: self.view.frame.size.height - 64)
        instanceWX?.onCreate = { (weexView:UIView?) -> Void in
            weexView?.removeFromSuperview()
            weexView?.backgroundColor = UIColor.white
            self.view.addSubview(weexView!)
//            UIAccessibilityPostNotification(UIAccessibilityScreenChangedNotification, weexView)
        };
        instanceWX?.render(with: URL(string:self.openUrl), options: ["bundleUrl":self.openUrl], data: nil);
    }
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func notificationRefreshInstance(_ notification: Notification){
        
    }
    
}
