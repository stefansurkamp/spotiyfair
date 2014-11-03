package secondMavenTest.secondMavenTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class GetTest {
	
	private static ArrayList<Artist> artistList;
	private static JsonArray jo2;

	public String searchForArtist(String query) throws Exception {
		// Suchstring muss eigentlich escaped werden --> Leerzeichen, Umlaute, Satzzeichen, etc.
		String response = searchArtist(query);	// gibt den JSON-Antwort-String zur�ck
		System.out.println("Suche nach: " + query);
		
		// Read JSON from a String
		JsonObject jo = JsonObject.readFrom(response);
		System.out.println("Response vom Server:\n" + response);

		// Bearbeiten des JSON Objekts (Artists)
		jo2 = jo.get("artists").asObject().get("items").asArray();
		
		// �ber alle Elemente iterieren
		System.out.println("Die Ergebnisse:\n");
		artistList = new ArrayList<Artist>();
		getAllItems();
				
		// HashMap nach popularity sortieren
		sortArtistsByPopularity(artistList);
		
		// Ausgabe aller Artists (sortiert)
		printArtists(artistList);
		
		// in JSON umformen
		return createJSON(artistList);
		
		
		
		
		
		
		
//		String response = searchTrack("Scary+monsters+and+nice+sprites");	// gibt den JSON-Antwort-String zur�ck
//		System.out.println("Suche nach: Scary monsters and nice sprites");
		
		
	}
	
		private static String createJSON(ArrayList<Artist> artistList) {
			JsonArray jsa = new JsonArray();			
			
			for(Artist a : artistList) {
				JsonArray tempArray = new JsonArray();
				tempArray.add(a.getName()).add(a.getId()).add(a.getPopularity());
				
				jsa.add(tempArray);
				}
			
			return jsa.toString();
		}

	private static void getAllItems() {	
		for(JsonValue jv : jo2) {
			// Werte des aktuellen Durchlaufs erheben
			String id = jv.asObject().get("uri").asString().split("spotify:artist:")[1];
			String name = jv.asObject().get("name").asString();
			int popularity = jv.asObject().get("popularity").asInt();

			// Artist in HashMap ablegen
			artistList.add(new Artist(id, name, popularity));
		}
	}

	private static void printArtists(ArrayList<Artist> artistList) {
		for(Artist a : artistList) {
			System.out.println(a.toString());
		}
	}

	private static void sortArtistsByPopularity(ArrayList<Artist> arrayList) {
		Collections.sort(arrayList, new Comparator<Artist>() {
			@Override
			public int compare(Artist a1, Artist a2) {
				// - um die Ordnung umzukehren
				return -Integer.compare(a1.getPopularity(), a2.getPopularity());
			}
		});
		
	}

	// HTTP GET request (Quelle: http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/)
	// TODO: Exceptions angleichen und catchen
		private static String searchArtist(String query) throws Exception {
	 
			String url = "https://api.spotify.com/v1/search?q=" + query + "&type=artist";
			// Suche nach "Skrillex", Suchtyp "Artist", 
			
			String url2 = "https://api.spotify.com/v1/search?q=scary+monsters+and+nice+sprites&type=track&limit=1";
			// Suche nach "Make it bun dem" (Leerzeichen werden durch Plus ersetzt), Suchtyp "Track", Limit auf 1
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			return response.toString();
	 
		}
		
		private static String searchTrack(String query) throws Exception {
			 
			String url = "https://api.spotify.com/v1/search?q=" + query + "&type=track";
			// Suchtyp "Artist", 
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			//TODO: Kann der User Agent noch angepasst werden?
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			//TODO: switch-case einf�gen f�r Fehlercodes
	 
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			return response.toString();
	 
		}
}
