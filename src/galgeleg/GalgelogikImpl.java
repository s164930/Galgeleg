
package galgeleg;

import brugerautorisation.transport.soap.BenytBruger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import javax.jws.WebService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebService(endpointInterface = "galgeleg.Galgelogik")
public class GalgelogikImpl{
  /** AHT afprøvning er muligeOrd synlig på pakkeniveau */
  ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;

  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  public String getSynligtOrd() {
    return synligtOrd;
  }

  public String getOrdet() {
    return ordet;
  }

  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }


  public GalgelogikImpl(){
    muligeOrd.add("bil");
    muligeOrd.add("computer");
    muligeOrd.add("programmering");
    muligeOrd.add("motorvej");
    muligeOrd.add("busrute");
    muligeOrd.add("gangsti");
    muligeOrd.add("skovsnegl");
    muligeOrd.add("solsort");
    muligeOrd.add("seksten");
    muligeOrd.add("sytten");
    nulstil();
  }

  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
  }


  private void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  public void gætBogstav(String bogstav) {
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public ArrayList<String> logStatus() {
    ArrayList<String> log = new ArrayList<String>();
    System.out.println("---------- ");
    log.add("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    log.add("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    log.add("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    log.add("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    log.add("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt){
        System.out.println("- SPILLET ER TABT");
        log.add("- SPILLET ER TABT");
    }
    if (spilletErVundet){
        System.out.println("- SPILLET ER VUNDET");
        log.add("- SPILLET ER VUNDET");
    }
    System.out.println("---------- ");
    log.add("---------- ");
    return log;
  }




  public void hentOrdFraDr() throws Exception{
        Client client = ClientBuilder.newClient();
        Response res = client.target("https://www.dr.dk/mu-online/api/1.3/list/view/mostviewed?channel=dr1&channel=dr2&channeltype=TV&limit=48")
                .request(MediaType.APPLICATION_JSON).get();
        String svar = res.readEntity(String.class);
        Random rand = new Random();
        muligeOrd.clear();
        int max = rand.nextInt(48)+1;
        try {
            JSONObject json = new JSONObject(svar);
            for (int i = 0; i < max; i++) {
                System.out.println(json.getJSONArray("Items").getJSONObject(i).getString("Title"));
                String id = json.getJSONArray("Items").getJSONObject(i).getString("Slug");
                Response prog = client.target("https://www.dr.dk/mu-online/api/1.3/programcard/" + id + "?")
                        .request(MediaType.APPLICATION_JSON).get();
                String progsvar = prog.readEntity(String.class);
                JSONObject progj = new JSONObject(progsvar);
                String data = progj.getString("Description");
                data = data.substring(0).replaceAll("<.+?>", " ").toLowerCase().
                        replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                        replaceAll(" [a-zæøåx] "," "). // fjern 1-bogstavsord
                        replaceAll(" [a-zæøåx][a-zæøåx] "," "); // fjern 2-bogstavsord         
                muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    
    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }
  
  public boolean Login(String name, String pass) throws Exception{
      System.out.println(name);
      System.out.println(pass);
      BenytBruger b = new BenytBruger();
      
      boolean login = b.LoginFraServer(name, pass);
      if(login){
          return true;
      } else {
          return false;
      }
  }
  
}
