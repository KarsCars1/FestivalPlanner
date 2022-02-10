public class Artist extends Performer {

	private String name;

	public Artist(String performerName, String name) {
		super(performerName);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
