/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 March 24, 2017
 */
package edu.asu.msse.pmural10.Assignment5;

import edu.asu.msse.pmural10.Assignment5.MainActivity;

public class MethodInformation {
    public String method;
    public Object[] params;
    public MainActivity parent;
    public DisplayActivity parent2;
    public String urlString;
    public String resultAsJson;

    MethodInformation(MainActivity parent, String urlString, String method, Object[] params){
        this.method = method;
        this.parent = parent;
        this.urlString = urlString;
        this.params = params;
        this.resultAsJson = "{}";
    }

    MethodInformation(DisplayActivity parent2, String urlString, String method, Object[] params){
        this.method = method;
        this.parent2 = parent2;
        this.urlString = urlString;
        this.params = params;
        this.resultAsJson = "{}";
    }
}
