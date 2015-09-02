package chords;

import java.util.ArrayList;

public class HalfDiminishedChord extends ChordAbstract {
	
	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 */
	public HalfDiminishedChord(int midiRootNote) {
		super(midiRootNote);
		this.nameOfChord = "HalfDiminished";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(3);
		this.listOfIntervalsRelativeToRootInBasePosition.add(6);
		this.listOfIntervalsRelativeToRootInBasePosition.add(10);
	}


}
