package chords;

import java.util.ArrayList;

public class MinorSeventhChord extends ChordAbstract {

	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 */
	public MinorSeventhChord(int midiRootNote) {
		super(midiRootNote);
		this.nameOfChord = "MinorSeventh";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(3);
		this.listOfIntervalsRelativeToRootInBasePosition.add(7);
		this.listOfIntervalsRelativeToRootInBasePosition.add(10);
	}

}
