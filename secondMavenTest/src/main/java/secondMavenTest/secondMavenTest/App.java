package secondMavenTest.secondMavenTest;

import static spark.Spark.get;
import static spark.SparkBase.setPort;

public class App {
	public static void main(String[] args) {

		setPort(9595);

		// showcase example
		get("/search/artist/:name", (request, response) -> {
			GetTest testlauf = new GetTest();
			try {
				return testlauf.searchForArtist(request.params(":name"));
			} catch (Exception e) {
				e.printStackTrace();
				return "Es ist ein Fehler aufgetreten.";
			}
		});

	}
}
