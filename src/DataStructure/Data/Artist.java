package DataStructure.Data;

// the Artist class is for performances with one person, it adds a name variable to the performer class
public class Artist extends Performer {

	private String name;

	public Artist(String name, int popularity) {
		super(name, popularity);
		this.name = name;
	}

	public Artist(String name){
		super(name, 0);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}