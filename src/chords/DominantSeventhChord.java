package chords;

import java.util.ArrayList;

public class DominantSeventhChord extends ChordAbstract {
	
	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 * @throws Exception 
	 */
	public DominantSeventhChord(int midiRootNote) throws Exception {
		super(midiRootNote);

	}
	public DominantSeventhChord(ChordAbstract chordAbstract) throws Exception {
		super(chordAbstract);

	}

	
	protected void defineChordIntervalsInIncreasingOrderOfPitch() {
		this.nameOfChord = "DominantSeventh";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(4);
		this.listOfIntervalsRelativeToRootInBasePosition.add(7);
		this.listOfIntervalsRelativeToRootInBasePosition.add(10);
	}

	@Override
	public ChordAbstract clone() {
		try {
			return new DominantSeventhChord((ChordAbstract) this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
