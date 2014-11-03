package secondMavenTest.secondMavenTest;

public class Artist {

	private String id;
	private String name;
	private int popularity;
	
	public Artist(String id, String name, int popularity) {
		this.id = id;
		this.name = name;
		this.popularity = popularity;
	}
	
	public int getPopularity() {
		return popularity;
	}
	
	public String toString() {
		return id + " / " + name + ": " + popularity;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}
