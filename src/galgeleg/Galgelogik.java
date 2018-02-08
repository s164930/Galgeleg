/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author viktor
 */
@WebService
public interface Galgelogik
{
    @WebMethod ArrayList<String> getBrugteBogstaver();
    @WebMethod String getSynligtOrd();
    @WebMethod String getOrdet();
    @WebMethod int getAntalForkerteBogstaver();
    @WebMethod boolean erSidsteBogstavKorrekt();
    @WebMethod boolean erSpilletVundet();
    @WebMethod boolean erSpilletTabt();
    @WebMethod boolean erSpilletSlut();
    @WebMethod void nulstil();
    @WebMethod void gætBogstav(String bogstav);
    @WebMethod ArrayList<String> logStatus();
    @WebMethod void hentOrdFraDr() throws Exception;
    @WebMethod boolean Login(String navn, String pass);
}
