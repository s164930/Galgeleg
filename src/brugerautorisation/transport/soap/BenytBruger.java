/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brugerautorisation.transport.soap;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author viktor
 */
public class BenytBruger {

    public BenytBruger() {

    }

    public boolean LoginFraServer(String name, String pass) throws Exception {
        URL hej = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
        QName lol = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
        Service bum = Service.create(hej, lol);

        Brugeradmin ba = bum.getPort(Brugeradmin.class);
        
        try{
            ba.hentBruger(name, pass);
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        
        return true;

    }

}
