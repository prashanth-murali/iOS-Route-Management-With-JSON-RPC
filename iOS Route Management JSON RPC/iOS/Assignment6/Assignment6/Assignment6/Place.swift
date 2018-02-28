/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 April 6, 2017
 */

import Foundation
public class Place {
    open var name: String
    open var description: String
    open var category: String
    open var address_title: String
    open var address_street: String
    open var elevation: String
    open var latitude: String
    open var longitude: String
    
    init (jsonStr: String){
        self.name = ""
        self.description = ""
        self.category = ""
        self.address_title = ""
        self.address_street = ""
        self.elevation = ""
        self.latitude = ""
        self.longitude = ""
        
        if let data: Data = jsonStr.data(using: String.Encoding.utf8){
            do{
                let dict = try JSONSerialization.jsonObject(with: data,options:.mutableContainers) as?[String:AnyObject]
                self.name = (dict!["name"] as? String)!
                self.description = (dict!["description"] as? String)!
                self.category = (dict!["category"] as? String)!
                self.address_title = (dict!["address-title"] as? String)!
                self.address_street = (dict!["address-street"] as? String)!
                self.elevation = (dict!["elevation"] as? String)!
                self.latitude = (dict!["latitude"] as? String)!
                self.longitude = (dict!["longitude"] as? String)!
            } catch {
                print("unable to convert to dictionary")
                
            }
        }
    }
    
    init(dict: [String:Any]){
        self.name = dict["name"] as! String
        self.description = dict["description"] as! String
        self.category = dict["category"] as! String
        self.address_title = dict["address-title"] as! String
        self.address_street = dict["address-street"] as! String
        self.elevation = dict["elevation"] as! String
        self.latitude = dict["latitude"] as! String
        self.longitude = dict["longitude"] as! String
        
    }
    
    func toJsonString() -> String {
        var jsonStr = "";
        let dict = ["name": name,"description": description,"category": category,"address-title": address_title,"address-street": address_street,"elevation": elevation,"latitude": latitude,"longitude": longitude] as [String : Any]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: dict, options: JSONSerialization.WritingOptions.prettyPrinted)
            // here "jsonData" is the dictionary encoded in JSON data
            jsonStr = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue)! as String
        } catch let error as NSError {
            print(error)
        }
        return jsonStr
    }
    
 
    func toDict() -> [String:Any] {
        let dict:[String:Any] = ["name": name,"description": description,"category": category,"address-title": address_title,"address-street": address_street,"elevation": elevation,"latitude": latitude,"longitude": longitude ] as [String : Any]
        return dict
    }
    
}
