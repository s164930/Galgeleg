/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;

import javax.xml.ws.Endpoint;


/**
 *
 * @author viktor
 */
public class GalgeServer {


  	public static void main(String[] arg) throws Exception
	{                
		GalgelogikImpl l = new GalgelogikImpl();
                Endpoint.publish("http://[::]:4930/Galgespil", l);
		System.out.println("Galgeleg startet");
	}
}
