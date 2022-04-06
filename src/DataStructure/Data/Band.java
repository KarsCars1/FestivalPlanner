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
		if (!this.members.contains(member) && member != this) {
			this.members.add(member);
		}
	}

	public void removeMember(String removeMember) {
		for (Performer member: members) {
			if (member.getPerformerName().equals(removeMember)) {
				this.members.remove(member);
			}
		}
	}

	@Override
	public String toString() {
		return this.performerName;
	}

	public ArrayList<Performer> getMembers() {
		return this.members;
	}

	public void clearMemberList() {
		members.clear();
	}
}