package secondMavenTest.models;

public class ResponseObject {
	private String artist;
	private String track;
	private String id;
	
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ResponseObject [artist=" + artist + ", track=" + track
				+ ", id=" + id + "]";
	}

	
}
