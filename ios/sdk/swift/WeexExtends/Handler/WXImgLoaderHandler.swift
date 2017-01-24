//
//  WXImgLoaderHandler.swift
//  demo
//
//  Created by zhe zhu on 2017/1/24.
//  Copyright © 2017年 zhu zhe. All rights reserved.
//

import UIKit
import WeexSDK
import SDWebImage

class WXImgLoaderHandler:NSObject, WXImgLoaderProtocol{
    func downloadImage(withURL url: String!, imageFrame: CGRect, userInfo options: [AnyHashable : Any]! = [:], completed completedBlock: ((UIImage?, Error?, Bool) -> Void)!) -> WXImageOperationProtocol! {
        var temp:String;
        if(url.hasPrefix("//")){
            temp = "http:" + url;
        }else{
            temp = url;
        }
        
        if(temp.hasPrefix("mipmap:") || temp.hasPrefix("drawable:")){
            let imgName:String! = temp.components(separatedBy: "://")[1]
            completedBlock(UIImage.init(named: imgName), nil, true);
        }else{
            SDWebImageManager.shared().downloadImage(with: URL(string: temp), options: SDWebImageOptions.retryFailed, progress: { (receivedSize:Int, expectedSize:Int) in
                
            }) { (image:UIImage?, error:Error?, cacheType:SDImageCacheType, finished:Bool, imageURL:URL?) in
                if ((completedBlock) != nil) {
                    completedBlock(image, error, finished);
                }
            };
        }
        return nil;
    }
}
