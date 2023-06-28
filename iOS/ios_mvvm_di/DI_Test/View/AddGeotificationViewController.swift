//
//  AddGeotificationViewController.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/13.
//

import UIKit
import MapKit

protocol AddGeotificationsViewControllerDelegate: class {
  func addGeotificationViewController(_ controller: AddGeotificationViewController, didAddGeotification: Geotification)
}

class AddGeotificationViewController: UITableViewController {
    @IBOutlet weak var mBtnAdd: UIBarButtonItem!
    @IBOutlet weak var mBtnZoom: UIBarButtonItem!
    @IBOutlet weak var mSegCtrlEventType: UISegmentedControl!
    @IBOutlet weak var mTxtRadius: UITextField!
    @IBOutlet weak var mTxtNote: UITextField!
    @IBOutlet weak var mMapView: MKMapView!
    
    weak var delegate: AddGeotificationsViewControllerDelegate?
    
    public var viewModel: AddGeotificationViewModeling? {
        didSet {
            if let viewModel = viewModel {
                
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        print("coder")
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.rightBarButtonItems = [mBtnAdd, mBtnZoom]
        mBtnAdd.isEnabled = false
    }
    
    @IBAction func textFieldEditingChanged(sender: UITextField) {
      let radiusSet = !(mTxtRadius.text?.isEmpty ?? true)
      let noteSet = !(mTxtNote.text?.isEmpty ?? true)
      mBtnAdd.isEnabled = radiusSet && noteSet
    }
    
    @IBAction func onCancel(_ sender: UIBarButtonItem) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func onAdd(_ sender: UIBarButtonItem) {
        let coordinate = mMapView.centerCoordinate
        let radius = Double(mTxtRadius.text ?? "") ?? 0
        let identifier = NSUUID().uuidString
        let note = mTxtNote.text ?? ""
        let eventType: Geotification.EventType = (mSegCtrlEventType.selectedSegmentIndex == 0) ? .onEntry : .onExit
        let geotification = Geotification(
          coordinate: coordinate,
          radius: radius,
          identifier: identifier,
          note: note,
          eventType: eventType)
        delegate?.addGeotificationViewController(self, didAddGeotification: geotification)
    }
    
    @IBAction func onZoomToCurrentLocation(_ sender: UIBarButtonItem) {
        mMapView.zoomToLocation(mMapView.userLocation.location)
    }
}


