package DataStructure.Data;

import java.util.ArrayList;

//The band class is for performers with more than one person in them, they add a list of members to the performer class
public class Band extends Performer {

	private ArrayList<Performer> members = new ArrayList<>();

	public Band(String performerName, int popularity) {
		super(performerName, popularity);
	}
//here are the methods to add or remove members from the band

	public void addMember(Performer member) {
		if (!members.contains(member) && member != this) {
			members.add(member);
		}
	}

	public void removeMember(Performer member) {
		if (members.contains(member)) {
			members.remove(member);
		}
	}

	@Override
	public String toString() {
		return performerName;
	}

	public ArrayList<Performer> getMembers() {
		return members;
	}
}