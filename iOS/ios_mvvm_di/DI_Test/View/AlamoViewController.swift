//
//  AlamoViewController.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/01.
//

import UIKit

class AlamoViewController: UIViewController {
    public var viewModel: AlamoViewModeling? {
        didSet {
            if let viewModel = viewModel {
//                viewModel.getAddressData(address: "송현로 205") {[self] address in
//                    lblTest.text = "\(address)"
//                    lblTest.lineBreakMode = .byWordWrapping
//                    lblTest.numberOfLines = 0
//                    lblTest.backgroundColor = .yellow
//                    print(address)
//                }
            }
        }
    }
    
    @IBOutlet weak var lblTest: UILabel!
    var mData:APIResponse!
    var defaults: UserDefaults!
    
    init(vm: AlamoViewModeling, defaults:UserDefaults) {
        self.viewModel = vm
        self.defaults = defaults
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        print("coder")
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("\(#function)")
        
        defaults.setValue("테스트", forKey: "TEST")
        
        print(defaults.value(forKey: "TEST"))
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        //checkFirst()
        print("\(#function)")
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        print("\(#function)")
    }
    
    @IBAction func goAnother(_ sender: UIButton) {
        
    }
    
    func checkFirst() {
        if defaults.value(forKey: "isFirst") as? Bool ?? true {
            performSegue(withIdentifier: "goFirst", sender: self)
//            coordinator?.pushToChange(navigationController!, test: "test")
        } else {

        }
    }
}
