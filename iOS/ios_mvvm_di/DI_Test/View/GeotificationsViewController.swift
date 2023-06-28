//
//  GeotificationsViewController.swift
//  DI_Test
//
//  Created by Winitech on 2021/04/13.
//

import UIKit
import MapKit

class GeotificationsViewController: UIViewController {
    @IBOutlet weak var mMapView: MKMapView!
    
    public var viewModel: GeotificationsViewModeling? {
        didSet {
            if let viewModel = viewModel {
                
                
            }
        }
    }
    
    public var mLocationManager: CLLocationManager? {
        didSet {
            if let manager = mLocationManager {
                manager.delegate = self
                manager.requestAlwaysAuthorization()
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        print("coder")
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        NSLog(#function)
        
        viewModel?.loadAllGeotifications() { geotification in
            geotification.forEach { self.add($0) }
            print("loadAllGeotifications")
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "addGeotification",
           let navigationController = segue.destination as? UINavigationController,
           let addVC = navigationController.viewControllers.first as? AddGeotificationViewController {
            addVC.delegate = self
        }
    }
    
    // MARK: Functions that update the model/associated views with geotification changes
    func add(_ geotification: Geotification) {
        viewModel?.addGeotifications(geotification: geotification)
        mMapView.addAnnotation(geotification)
        addRadiusOverlay(forGeotification: geotification)
        updateGeotificationsCount()
    }
    
    func remove(_ geotification: Geotification) {
        guard let index = viewModel!.mGeotifications.firstIndex(of: geotification) else { return }
        viewModel?.removeGeotifications(geotification: geotification, index: index)
        mMapView.removeAnnotation(geotification)
        removeRadiusOverlay(forGeotification: geotification)
        updateGeotificationsCount()
    }
    
    func updateGeotificationsCount() {
        title = "Geotifications: \(viewModel!.getGeotificationsCount())"
        navigationItem.rightBarButtonItem?.isEnabled = ((viewModel?.getGeotificationsCount())! < 20)
    }
    
    // MARK: Map overlay functions
    func addRadiusOverlay(forGeotification geotification: Geotification) {
        mMapView.addOverlay(MKCircle(center: geotification.coordinate, radius: geotification.radius))
    }
    
    func removeRadiusOverlay(forGeotification geotification: Geotification) {
        // Find exactly one overlay which has the same coordinates & radius to remove
        guard let overlays = mMapView?.overlays else { return }
        for overlay in overlays {
            guard let circleOverlay = overlay as? MKCircle else { continue }
            let coord = circleOverlay.coordinate
            if coord.latitude == geotification.coordinate.latitude &&
                coord.longitude == geotification.coordinate.longitude &&
                circleOverlay.radius == geotification.radius {
                mMapView.removeOverlay(circleOverlay)
                break
            }
        }
    }
    
    @IBAction func zoomToCurrentLocation(_ sender: UIBarButtonItem) {
        mMapView.zoomToLocation(mMapView.userLocation.location)
    }
    
    // MARK: Put monitoring functions below
    func startMonitoring(geotification: Geotification) {
        // 1
        if !CLLocationManager.isMonitoringAvailable(for: CLCircularRegion.self) {
            showAlert(
                withTitle: "Error",
                message: "Geofencing is not supported on this device!")
            return
        }
        
        // 2
        let fenceRegion = geotification.region
        mLocationManager?.startMonitoring(for: fenceRegion)
    }
    
    func stopMonitoring(geotification: Geotification) {
        for region in mLocationManager!.monitoredRegions {
            guard
                let circularRegion = region as? CLCircularRegion,
                circularRegion.identifier == geotification.identifier
            else { continue }
            
            mLocationManager?.stopMonitoring(for: circularRegion)
        }
    }
}

// MARK: AddGeotificationViewControllerDelegate
extension GeotificationsViewController: AddGeotificationsViewControllerDelegate {
    func addGeotificationViewController(
        _ controller: AddGeotificationViewController,
        didAddGeotification geotification: Geotification
    ) {
        controller.dismiss(animated: true, completion: nil)
        
        // 1
        geotification.clampRadius(maxRadius:
                                    mLocationManager!.maximumRegionMonitoringDistance)
        add(geotification)
        
        // 2
        startMonitoring(geotification: geotification)
        viewModel?.saveAllGeotifications()
    }
}

// MARK: - Location Manager Delegate
extension GeotificationsViewController: CLLocationManagerDelegate {
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        // 1
        let status = manager.authorizationStatus
        
        // 2
        mMapView.showsUserLocation = (status == .authorizedAlways)
        
        // 3
        if status != .authorizedAlways {
            let message = """
        Your geotification is saved but will only be activated once you grant
        Geotify permission to access the device location.
        """
            showAlert(withTitle: "Warning", message: message)
        }
    }
    
    func locationManager(
        _ manager: CLLocationManager,
        monitoringDidFailFor region: CLRegion?,
        withError error: Error
    ) {
        guard let region = region else {
            print("Monitoring failed for unknown region")
            return
        }
        print("Monitoring failed for region with identifier: \(region.identifier)")
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Location Manager failed with the following error: \(error)")
    }
}

// MARK: - MapView Delegate
extension GeotificationsViewController: MKMapViewDelegate {
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
      let identifier = "myGeotification"
      if annotation is Geotification {
        var annotationView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier) as? MKPinAnnotationView
        if annotationView == nil {
          annotationView = MKPinAnnotationView(annotation: annotation, reuseIdentifier: identifier)
          annotationView?.canShowCallout = true
          let removeButton = UIButton(type: .custom)
          removeButton.frame = CGRect(x: 0, y: 0, width: 23, height: 23)
          removeButton.setImage(UIImage(systemName: "trash.fill"), for: .normal)
          annotationView?.leftCalloutAccessoryView = removeButton
        } else {
          annotationView?.annotation = annotation
        }
        return annotationView
      }
      return nil
    }
    
    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
        if overlay is MKCircle {
            let circleRenderer = MKCircleRenderer(overlay: overlay)
            circleRenderer.lineWidth = 1.0
            circleRenderer.strokeColor = .purple
            circleRenderer.fillColor = UIColor.purple.withAlphaComponent(0.4)
            return circleRenderer
        }
        return MKOverlayRenderer(overlay: overlay)
    }
    
    func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl) {
        guard let geotification = view.annotation as? Geotification else { return }
        stopMonitoring(geotification: geotification)
        remove(geotification)
        viewModel?.saveAllGeotifications()
    }
    
}
