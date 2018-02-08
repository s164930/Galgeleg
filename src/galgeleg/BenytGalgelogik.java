package galgeleg;

import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
// hej simon
public class BenytGalgelogik {

  public static void main(String[] args) throws Exception{
    Scanner s = new Scanner(System.in);
    URL url = new URL("http://ubuntu4.saluton.dk:4930/Galgespil?wsdl");
    QName qname = new QName("http://galgeleg/", "GalgelogikImplService");
    Service service = Service.create(url, qname);
    Boolean login = false;
    
    Galgelogik spil = service.getPort(Galgelogik.class);
    
    while(!login){
        System.out.println("Indtast brugernavn: ");
        String navn = s.nextLine();
        System.out.println("Indtast password: ");
        String pass = s.nextLine();
        if(spil.Login(navn, pass)){
            login = true;
        } else {
            System.out.println("Forkert brugernavn eller adgangskode, prøv igen");
        }
    }
    
    spil.nulstil();

    try {
      spil.hentOrdFraDr();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    while(!spil.erSpilletSlut()){
        System.out.println("Gæt et bogstav du vil gætte på");
        String input = s.nextLine();
        spil.gætBogstav(input); 

        System.out.println("Antal forkerte bogstaver: " + spil.getAntalForkerteBogstaver());
        System.out.println("Synlige ord: " + spil.getSynligtOrd());
    }
    
    if(spil.erSpilletVundet()){
        System.out.println("Tillykke, du vandt Galgelegen!");
    } else {
        System.out.println("Desværre, du klarede ikke Galgelegen");
        System.out.println("Det rigtige ord var: " + spil.getOrdet());
    }
    

  }
}
