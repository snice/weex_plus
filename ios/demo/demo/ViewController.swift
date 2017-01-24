//
//  ViewController.swift
//  demo
//
//  Created by zhe zhu on 2017/1/24.
//  Copyright © 2017年 zhu zhe. All rights reserved.
//

import UIKit
import WeexSDK

class ViewController: UIViewController {

    let url:String = "https://www.zhuzhe.wang/examples/mobile/index.js";
    var instance: WXSDKInstance?
    var wee:UIView?
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "WeexPlus示例";
        self.view.backgroundColor = UIColor.white;
        let refreshItem = UIBarButtonItem.init(barButtonSystemItem: UIBarButtonSystemItem.search, target: self, action: #selector(self.refreshAction(sender:)));
        self.navigationItem.rightBarButtonItems = [refreshItem];
        instance = WXSDKInstance();
        instance?.viewController = self;
        instance?.frame = CGRect.init(x: 0, y: 64, width: self.view.frame.size.width, height: self.view.frame.size.height - 64)
        instance?.onCreate = { (weexView:UIView?) -> Void in
            self.wee = weexView
            weexView?.removeFromSuperview();
            weexView?.backgroundColor = UIColor.white
            self.view.addSubview(weexView!);
        };
        let dyUrl = String.init(format: "%@%@random=%d", url, "?", arc4random())
        instance?.render(with: URL(string: url), options: ["bundleUrl":dyUrl], data: nil)
    }
    override func viewDidAppear(_ animated: Bool) {
        self.wee?.subviews[0].subviews[0].backgroundColor = UIColor.green
    }
    func refreshAction(sender: UIBarButtonItem){
//        instance?.render(with: URL(string: url), options: ["bundleUrl":url], data: nil)
        self.navigationController?.pushViewController(ScanViewController(), animated: true)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    deinit {
        instance?.destroy()
    }
}

