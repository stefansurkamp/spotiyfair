package secondMavenTest.secondMavenTest;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.*;

//spotify-web-api-java
import com.wrapper.spotify.*; //Funktioniert nicht!?
import com.wrapper.spotify.methods.authentication.ClientCredentialsGrantRequest;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.ClientCredentials;
import com.wrapper.spotify.models.Track;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Page;
import com.google.common.util.concurrent.FutureCallback;import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture; // com.google.guava dependency hinzugefügt
import secondMavenTest.models.ResponseObject;

import java.util.List;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args )
    {
    	
    	setPort(9595); 
    	
    	//gibt einfach nur den string "Hello World" zurück
        get("/hello", (req, res) -> "Hello World");
        
        //in der URL wird ein parameter übergeben der ausgegeben wird
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name"); 
        });
        
        //das gleiche wie das erste bsp.
        get("/get", (request, response) -> {
        	return "this is the result of a get request";
        });
        
        //hier werden die daten vom anwender nicht in der URL (get) sondern im body (post) übergeben
        post("/post", (request, response) -> {
        	String stringResult = request.body();
        	int result = Integer.parseInt(stringResult);
        	String resp;
        	if(result==11){
        		resp = "Das ist richtig!";
        	}
        	else{
        		resp = "Das ist leider Falsch.";
        	}
        	return resp;
        });
        
        //hier wird ein html dokument zurückgegeben. Das html template (testTemplate.wm) liegt in src/main/ressources.
        //diese funktion tauscht nur die variablen im template gegen konkrete werte.
        get("/helloVelo", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("hello", "Velocity World");
            model.put("person", "Sven");

            return new ModelAndView(model, "testTemplate.wm");
        }, new VelocityTemplateEngine());
        
        //erster spotify test
        get("/spotify", (request, response) -> {
        	// Client Credentials flow Beispiel aus der github readme
        	// Beziehen eines Acces Token auskommentiert
        	final String clientId = "609e33506de24010b8e8cb4e29d5a733";
        	final String clientSecret = "cf386060cac347e79e166cb9b350c63a";

        	final Api api = Api.builder()
        	  .clientId(clientId)
        	  .clientSecret(clientSecret)
        	  .build();

//        	/* Create a request object. */
//        	final ClientCredentialsGrantRequest req = api.clientCredentialsGrant().build(); 
//
//        	/* Use the request object to make the request, either asynchronously (getAsync) or synchronously (get) */
//        	final SettableFuture<ClientCredentials> responseFuture = req.getAsync();
//
//        	/* Add callbacks to handle success and failure */
//        	Futures.addCallback(responseFuture, new FutureCallback<ClientCredentials>() {
//        	  @Override
//        	  public void onSuccess(ClientCredentials clientCredentials) {
//        	    /* The tokens were retrieved successfully! */
//        	    System.out.println("Successfully retrieved an access token! " + clientCredentials.getAccessToken());
//        	    System.out.println("The access token expires in " + clientCredentials.getExpiresIn() + " seconds");
//
//        	    /* Set access token on the Api object so that it's used going forward */
//        	    api.setAccessToken(clientCredentials.getAccessToken());
//
//        	    /* Please note that this flow does not return a refresh token.
//        	   * That's only for the Authorization code flow */
//        	  }
//
//        	  @Override
//        	  public void onFailure(Throwable throwable) {
//        	    /* An error occurred while getting the access token. This is probably caused by the client id or
//        	     * client secret is invalid. */
//        	  }
//        	});        	
        	
        	// Searching for track Beispiel aus der github readme
        	final TrackSearchRequest trackReq = api.searchTracks("Mr. Brightside").build();

        	try {
        	   final Page<Track> trackSearchResult = trackReq.get();
        	   //die ersten drei ergebnisse ausgeben
        	   List<Track> res = trackSearchResult.getItems();
        		for (int i = 0; i < res.size() && i<11; i+=5) {
        			Track resTrack = new Track();
        			List<SimpleArtist> resTrackArtists = new ArrayList<SimpleArtist>();
        			SimpleArtist resTrackArtist = new SimpleArtist();
        			resTrack = res.get(i);
        			resTrackArtists = resTrack.getArtists();
        			resTrackArtist = resTrackArtists.get(0);
        			System.out.println(resTrackArtist.getName() + " - " + resTrack.getName() + " + (" + resTrack.getId() + ")");
        		}
        		
        	   System.out.println("I got " + trackSearchResult.getTotal() + " results!");
        	} catch (Exception e) {
        	   System.out.println("Something went wrong!" + e.getMessage());
        	}
        	
        	return "Ausgabe der Java Konsole überprüfen";
        });
        
        //spotify test mit param
        get("/spotify/:searchParam", (request, response) -> {
        	// Client Credentials flow Beispiel aus der github readme
        	// Beziehen eines Acces Token auskommentiert
        	final String clientId = "609e33506de24010b8e8cb4e29d5a733";
        	final String clientSecret = "cf386060cac347e79e166cb9b350c63a";

        	final Api api = Api.builder()
        	  .clientId(clientId)
        	  .clientSecret(clientSecret)
        	  .build();  	
        	
        	// Searching for track Beispiel aus der github readme
        	final TrackSearchRequest trackReq = api.searchTracks(request.params(":searchParam")).build();
        	try {
        	   final Page<Track> trackSearchResult = trackReq.get();
        	   //die ersten drei ergebnisse ausgeben + in resObject schreiben
        	   List<Track> res = trackSearchResult.getItems();
        	   ResponseObject[] responseObjectArray = new ResponseObject[3];
        		for (int i = 0; i < res.size() && i<11; i+=5) {
        			Track resTrack = new Track();
        			List<SimpleArtist> resTrackArtists = new ArrayList<SimpleArtist>();
        			SimpleArtist resTrackArtist = new SimpleArtist();
        			resTrack = res.get(i);
        			resTrackArtists = resTrack.getArtists();
        			resTrackArtist = resTrackArtists.get(0);
        			
        			System.out.println(resTrackArtist.getName() + " - " + resTrack.getName() + " + (" + resTrack.getId() + ")");
        			
        			ResponseObject responseObject = new ResponseObject();
        			responseObject.setArtist(resTrackArtist.getName());
        			responseObject.setTrack(resTrack.getName());
        			responseObject.setId(resTrack.getId());
        			if(i==0){
        				responseObjectArray[0] = responseObject;
        			}
        			if(i==5){
        				responseObjectArray[1] = responseObject;
        			}
        			if(i==10){
        				responseObjectArray[2] = responseObject;
        			}
        		}
        	   //System.out.println("I got " + trackSearchResult.getTotal() + " results!");
        	} catch (Exception e) {
        	   System.out.println("Something went wrong!" + e.getMessage());
        	}
        	
        	return "Java Konsole überprüfen";
        });
    }
}


