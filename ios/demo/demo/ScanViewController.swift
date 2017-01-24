//
//  ScanViewController.swift
//  demo
//
//  Created by zhe zhu on 2017/1/24.
//  Copyright © 2017年 zhu zhe. All rights reserved.
//

import UIKit
import AVFoundation

class ScanViewController: UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    //私有变量
    private var device: AVCaptureDevice!
    private var input: AVCaptureInput!
    private var output: AVCaptureMetadataOutput!
    private var session: AVCaptureSession!
    private var preview: AVCaptureVideoPreviewLayer!
    private let ScreenWidth = UIScreen.main.bounds.size.width
    private let ScreenHeight = UIScreen.main.bounds.size.height
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "扫描"
        self.initDevice()
    }
    
    func initDevice(){
        // Device
        self.device = AVCaptureDevice.defaultDevice(withMediaType: AVMediaTypeVideo)
        
        
        // Input
        do{
            self.input = try AVCaptureDeviceInput(device: self.device)
        }catch{
            print("Input 初始化失败")
            return
        }
        
        
        // Output
        self.output = AVCaptureMetadataOutput()
        self.output.setMetadataObjectsDelegate(self, queue:DispatchQueue.main)
        self.output.rectOfInterest = CGRect(x: ((ScreenHeight-220)/2)/ScreenHeight, y: ((ScreenWidth-220)/2)/ScreenWidth, width: 220/ScreenHeight, height: 220/ScreenWidth)//感兴趣的区域，设置为中心，否则全屏可扫
        
        // Session
        self.session = AVCaptureSession()
        self.session.sessionPreset = AVCaptureSessionPresetHigh
        if self.session.canAddInput(self.input){
            self.session.addInput(self.input)
        }else{
            print("Session Add Input 初始化失败")
            return
        }
        
        if self.session.canAddOutput(self.output){
            self.session.addOutput(self.output)
        }else{
            print("Session Add Output 初始化失败")
            return
        }
        
        // 条码类型 AVMetadataObjectTypeQRCode
        self.output.metadataObjectTypes = [AVMetadataObjectTypeQRCode]
        
        // Preview
        self.preview = AVCaptureVideoPreviewLayer(session: self.session)
        self.preview.videoGravity = AVLayerVideoGravityResizeAspectFill
        self.preview.frame = self.view.layer.bounds
        self.view.layer.insertSublayer(self.preview, at: 0)
        
        
        // Start
        
        self.session.startRunning()
    }
    
    //MARK: CaptureOutputDelegate
    internal func captureOutput(_ captureOutput: AVCaptureOutput!, didOutputMetadataObjects metadataObjects: [Any]!, from connection: AVCaptureConnection!) {
        
        var stringValue: String?
        if metadataObjects.count > 0 {
            // 停止扫描
            self.session.stopRunning()
            if let metadataObject = metadataObjects.first as? AVMetadataMachineReadableCodeObject{
                stringValue = metadataObject.stringValue
                let controller = WeexViewController();
                controller.openUrl = stringValue
                self.navigationController?.pushViewController(controller, animated: true);
                
            }
        }
    }
    
    
}
