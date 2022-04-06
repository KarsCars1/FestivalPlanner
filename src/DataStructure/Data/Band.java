package DataStructure.Data;

import java.util.ArrayList;

public class Band extends Performer {
	private ArrayList<Performer> members = new ArrayList<>();

	public Band(String performerName, int popularity) {
		super(performerName, popularity);
	}

	public void addMember(Performer member) {
		if (!this.members.contains(member) && member != this) {
			this.members.add(member);
		}
	}

	public void removeMember(Performer member) {
		if (this.members.contains(member)) {
			this.members.remove(member);
		}
	}

	@Override
	public String toString() {
		return this.performerName;
	}

	public ArrayList<Performer> getMembers() {
		return this.members;
	}
}