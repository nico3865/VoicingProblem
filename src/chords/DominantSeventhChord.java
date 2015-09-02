package chords;

import java.util.ArrayList;

public class DominantSeventhChord extends ChordAbstract {
	
	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 */
	public DominantSeventhChord(int midiRootNote) {
		super(midiRootNote);
		this.nameOfChord = "DominantSeventh";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(4);
		this.listOfIntervalsRelativeToRootInBasePosition.add(7);
		this.listOfIntervalsRelativeToRootInBasePosition.add(10);
	}


}
