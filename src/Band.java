import java.util.ArrayList;

public class Band extends Performer {
	private ArrayList<Performer> members = new ArrayList<>();

	public Band(String performerName) {
		super(performerName);
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
}
