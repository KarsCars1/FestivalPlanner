package DataStructure.Data;

import java.util.ArrayList;

public class Band extends Performer {
	private ArrayList<Performer> members = new ArrayList<>();

	public Band(String performerName, int popularity) {
		super(performerName, popularity);
	}

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
