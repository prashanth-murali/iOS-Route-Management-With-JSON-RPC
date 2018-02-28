/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 April 6, 2017
 */
import UIKit

class ViewController: UIViewController ,UIPickerViewDelegate, UITextFieldDelegate {
    
    @IBOutlet weak var name1: UITextField!
    @IBOutlet weak var description1: UITextField!
    @IBOutlet weak var category1: UITextField!
    @IBOutlet weak var address_title1: UITextField!
    @IBOutlet weak var address_street1: UITextField!
    @IBOutlet weak var latitude: UITextField!
    @IBOutlet weak var longitude: UITextField!
    @IBOutlet weak var elevation: UITextField!
    @IBOutlet weak var placePicker: UIPickerView!
    @IBOutlet weak var placeTF: UITextField!
    public var flag:Int = 0
    
    var urlString:String = "http://127.0.0.1:8080"
    var selectedPlace:String=""
    //var selectedCourse:String=""
    var places:[String]=[String]()
    var takes: [String] = [String]()



    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.placePicker.removeFromSuperview()
        self.placePicker.delegate = self
        self.placeTF.inputView = self.placePicker
        
        
        self.urlString = self.setURL()
        self.callGetNamesNUpdatePlacePicker()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func setURL () -> String {
        var serverhost:String = "localhost"
        var jsonrpcport:String = "8080"
        var serverprotocol:String = "http"
        // access and log all of the app settings from the settings bundle resource
        if let path = Bundle.main.path(forResource: "ServerInfo", ofType: "plist"){
            // defaults
            if let dict = NSDictionary(contentsOfFile: path) as? [String:AnyObject] {
                serverhost = (dict["server_host"] as? String)!
                jsonrpcport = (dict["jsonrpc_port"] as? String)!
                serverprotocol = (dict["server_protocol"] as? String)!
            }
        }
        print("setURL returning: \(serverprotocol)://\(serverhost):\(jsonrpcport)")
        return "\(serverprotocol)://\(serverhost):\(jsonrpcport)"
    }
    
    func callGetNamesNUpdatePlacePicker() {
        let aConnect:PlaceCollectionStub = PlaceCollectionStub(urlString: urlString)
        let _:Bool = aConnect.getNames(callback: { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: Data = res.data(using: String.Encoding.utf8){
                    do{
                        let dict = try JSONSerialization.jsonObject(with: data,options:.mutableContainers) as?[String:AnyObject]
                        self.places = (dict!["result"] as? [String])!
                        //self.places = self.places.sorted()
                        self.placeTF.text = ((self.places.count>0) ? self.places[0] : "")
                        if(self.places.count==0)
                        {
                            self.name1.text=""
                            self.description1.text=""
                            self.category1.text=""
                            self.address_title1.text=""
                            self.address_street1.text=""
                            self.elevation.text=""
                            self.latitude.text=""
                            self.longitude.text=""
                        }
                        self.placePicker.reloadAllComponents()
                        if self.places.count > 0 {
                            self.callGetNPopulatUIFields(self.places[0])
                        }
                    } catch {
                        print("unable to convert to dictionary")
                    }
                }
                
            }
        })  // end of method call to getNames
    }
    
    func callGetNPopulatUIFields(_ name: String){
        let aConnect:PlaceCollectionStub = PlaceCollectionStub(urlString: urlString)
        let _:Bool = aConnect.get(name: name, callback: { (res: String, err: String?) -> Void in
            if err != nil {
                NSLog(err!)
            }else{
                NSLog(res)
                if let data: Data = res.data(using: String.Encoding.utf8){
                    do{
                        let dict = try JSONSerialization.jsonObject(with: data,options:.mutableContainers) as?[String:AnyObject]
                        let aDict:[String:AnyObject] = (dict!["result"] as? [String:AnyObject])!
                        let aPlace:Place = Place(dict: aDict)
                        //self.studentNumTF.text = "\(aStud.studentid)"
                        self.name1.text = aPlace.name
                        self.description1.text=aPlace.description
                        self.category1.text=aPlace.category
                        self.address_title1.text=aPlace.address_title
                        self.address_street1.text=aPlace.address_street
                        self.elevation.text=aPlace.elevation
                        self.latitude.text=aPlace.latitude
                        self.longitude.text=aPlace.longitude
                    } catch {
                        NSLog("unable to convert to dictionary")
                    }
                }
            }
        })
    }

    @IBAction func refreshButtonClicked(_ sender: Any) {
        
        self.viewDidLoad()
    }
    
    @IBAction func editButtonClicked(_ sender: Any) {
        
            print("edit button clicked")
            
            
            let promptND = UIAlertController(title: name1.text, message: "Edit Place Details", preferredStyle: UIAlertControllerStyle.alert)
            
            promptND.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default, handler: nil))
            
            promptND.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: { (action) -> Void in
                
                let newPlaceName:String=self.name1.text!
                
                let newPlaceDescription:String = (promptND.textFields?[0].text == "") ?
                    "Enter Description" : (promptND.textFields?[0].text)!
                
                let newPlaceCategory:String = (promptND.textFields?[1].text == "") ?
                    "Enter Category" : (promptND.textFields?[1].text)!
                //self.places[self.selectedPlace]!.category=newPlaceCategory
                
                let newPlaceAddress_Title:String = (promptND.textFields?[2].text == "") ?
                    "Enter Address-Title" : (promptND.textFields?[2].text)!
                //self.places[self.selectedPlace]!.address_title=newPlaceAddress_Title
                
                let newPlaceAddress_Street:String = (promptND.textFields?[3].text == "") ?
                    "Enter Address-Street" : (promptND.textFields?[3].text)!
                //self.places[self.selectedPlace]!.address_street=newPlaceAddress_Street
                
                
                let newPlaceElevation:String = (promptND.textFields?[4].text == "") ?
                    "Enter Elevation" : (promptND.textFields?[4].text)!
                //self.places[self.selectedPlace]!.elevation=newPlaceElevation
                
                
                let newPlaceLatitude:String = (promptND.textFields?[5].text == "") ?
                    "Enter Latitude" : (promptND.textFields?[5].text)!
                //self.places[self.selectedPlace]!.latitude=newPlaceLatitude
                
                let newPlaceLongitude:String = (promptND.textFields?[6].text == "") ?
                    "Enter Longitude" : (promptND.textFields?[6].text)!
                //self.places[self.selectedPlace]!.longitude=newPlaceLongitude
                
                
                let aPlace:Place = Place(dict:["name": newPlaceName,"description": newPlaceDescription, "category": newPlaceCategory,"address-title": newPlaceAddress_Title, "address-street": newPlaceAddress_Street,"elevation": newPlaceElevation,"latitude": newPlaceLatitude, "longitude": newPlaceLongitude])
                
                let aConnect:PlaceCollectionStub = PlaceCollectionStub(urlString:self.urlString)
                let _:Bool = aConnect.add(student: aPlace,callback: { _ in
                    //self.places.append(newPlaceName)
                    self.placePicker.reloadAllComponents()
                    self.callGetNPopulatUIFields(newPlaceName)
                })
                
                self.viewDidLoad()
            }))
            
            if description1.text != "Enter Description"
            {
                
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.description1.text
                })
                
            }
                
            else
                
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Description"
                })
                
            }
            
            if category1.text != "Enter Category"
            {
                
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.category1.text
                })
            }
                
            else
                
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Category"
                })
                
            }
            
            if address_title1.text != "Enter Address-Title"
            {
                
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.address_title1.text
                })
            }
                
            else
            {
                
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Address-Title"
                })
                
            }
            
            
            if address_street1.text != "Enter Address-Street"
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.address_street1.text
                })
            }
                
            else
            {
                
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Address-Street"
                })
            }
            
            
            if elevation.text != "Enter Elevation"
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.elevation.text
                })
            }
                
            else
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Elevation"
                })
            }
            
            if latitude.text != "Enter Latitude"
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.latitude.text
                })
            }
                
            else
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Latitude"
                })
                
            }
            
            if longitude.text != "Enter Longitude"
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.text = self.longitude.text
                })
            }
                
            else
            {
                promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                    textField.placeholder = "Enter Longitude"
                })
                
            }
            
            
            present(promptND, animated: true, completion: nil)
        }
        

    
    
    
    @IBAction func addButtonClicked(_ sender: Any) {
        
        /*let placeName:String = self.name1.text!
        let placeDescription:String = self.description1.text!
        let placeCategory:String = self.category1.text!
        let placeAddressTitle:String = self.address_title1.text!
        let placeAddressStreet:String = self.address_street1.text!
        let placeElevation:String = self.elevation.text!
        let placeLatitude:String = self.latitude.text!
        let placeLongitude:String = self.longitude.text!
        
        let aPlace:Place = Place(dict:["name": placeName, "description": placeDescription,"category": placeCategory,"address-title": placeAddressTitle,"address-street": placeAddressStreet,"elevation": placeElevation,"latitude": placeLatitude,"longitude": placeLongitude])
        let aConnect:PlaceCollectionStub = PlaceCollectionStub(urlString: urlString)
        let _:Bool = aConnect.add(student: aPlace,callback: { _ in
            self.places.append(placeName)
            self.placePicker.reloadAllComponents()
            self.callGetNPopulatUIFields(placeName)
        })*/
        
     
            print("add button clicked")
            
            
            let promptND = UIAlertController(title: "New Place", message: "Enter Place Name & Details", preferredStyle: UIAlertControllerStyle.alert)
            
            promptND.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default, handler: nil))
            
            promptND.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: { (action) -> Void in
                
                var newPlaceName:String = (promptND.textFields?[0].text == "") ?
                    "None" : (promptND.textFields?[0].text)!
                let newPlaceDesctiption:String = (promptND.textFields?[1].text == "") ?
                    "Enter Description" : (promptND.textFields?[1].text)!
                let newPlaceCategory:String = (promptND.textFields?[2].text == "") ?
                    "Enter Category" : (promptND.textFields?[2].text)!
                let newPlaceAddress_Title:String = (promptND.textFields?[3].text == "") ?
                    "Enter Address-Title" : (promptND.textFields?[3].text)!
                let newPlaceAddress_Street:String = (promptND.textFields?[4].text == "") ?
                    "Enter Address-Street" : (promptND.textFields?[4].text)!
                
                let newPlaceElevation:String = (promptND.textFields?[5].text == "") ?
                    "Enter Elevation" : (promptND.textFields?[5].text)!
                let newPlaceLatitude:String = (promptND.textFields?[6].text == "") ?
                    "Enter Latitude" : (promptND.textFields?[6].text)!
                let newPlaceLongitude:String = (promptND.textFields?[7].text == "") ?
                    "Enter Longitude" : (promptND.textFields?[7].text)!
                
                if newPlaceName == "None"
                {
                    
                    let alert = UIAlertController(title: "ERROR: Place Name Missing", message: "Enter a Place Name", preferredStyle: .alert)
                    
                    alert.addAction(UIAlertAction(title: "Done", style: .default, handler: { [weak alert] (_) in
                        self.viewDidLoad()
                    }))
                    
                    self.present(alert, animated: true, completion: nil)
                    
                    self.flag = 1
                }
                
                
                if(self.places.count>0)
                {
                    for i in 0...self.places.count-1
                    {
                        if newPlaceName==self.places[i]
                        {
                            
                            let alert = UIAlertController(title: "ERROR: Invalid Place Name", message: "Enter a New Place Name", preferredStyle: .alert)
                            
                            
                            alert.addAction(UIAlertAction(title: "Done", style: .default, handler: { [weak alert] (_) in
                                //self.places=self.places.sorted()
                                self.viewDidLoad()
                            }))
                            
                            
                            self.present(alert, animated: true, completion: nil)
                            
                            self.flag = 1
                            
                        }
                    }
                }
                
                
                if self.flag == 0
                    
                {
                    let aPlace:Place = Place(dict:["name": newPlaceName,"description": newPlaceDesctiption, "category": newPlaceCategory,"address-title": newPlaceAddress_Title, "address-street": newPlaceAddress_Street,"elevation": newPlaceElevation,"latitude": newPlaceLatitude, "longitude": newPlaceLongitude])
                    
                    
                    
                    let aConnect:PlaceCollectionStub = PlaceCollectionStub(urlString:self.urlString)
                    let _:Bool = aConnect.add(student: aPlace,callback: { _ in
                        self.places.append(newPlaceName)
                        self.placePicker.reloadAllComponents()
                        self.callGetNPopulatUIFields(newPlaceName)
                        })

                }
                //self.places = Array(self.places.keys).sorted()
                //self.tableView.reloadData(),
                //self.places=self.places.sorted()
                self.viewDidLoad()
                        self.flag = 0
                
            }))
            
            
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Name"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Description"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Category"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Address-Title"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Address-Street"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Elevation"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Latitude"
            })
            promptND.addTextField(configurationHandler: {(textField: UITextField!) in
                textField.placeholder = "Place Longitude"
            })
            
            present(promptND, animated: true, completion: nil)
        }
    
    
    @IBAction func removeButtonClicked(_ sender: Any) {
        let aConnect:PlaceCollectionStub = PlaceCollectionStub(urlString: urlString)
        let _:Bool = aConnect.remove(studentName: name1.text!,callback: { _ in
            self.callGetNamesNUpdatePlacePicker()
        })
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        //self.studentNumTF.resignFirstResponder()
        self.placeTF.resignFirstResponder()
        //self.takesTF.resignFirstResponder()
    }
    
    // UITextFieldDelegate Method
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        //self.studentNumTF.resignFirstResponder()
        self.placeTF.resignFirstResponder()
        return true
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        
            self.selectedPlace = places[row]
            self.placeTF.text = self.selectedPlace
            self.placeTF.resignFirstResponder()
            self.callGetNPopulatUIFields(self.selectedPlace)
        
    }
    
    func numberOfComponentsInPickerView(_ pickerView: UIPickerView) -> Int {
        return 1
    }

    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return places.count
    }
    
    func pickerView (_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return places[row]
    }


}

