package chords;

import java.util.ArrayList;

public class MajorSeventhChord extends ChordAbstract {
	
	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 * @throws Exception 
	 */
	public MajorSeventhChord(int midiRootNote) throws Exception {
		super(midiRootNote);
	}

	public MajorSeventhChord(ChordAbstract chordAbstract) {
		super(chordAbstract);
	}

	@Override
	protected void defineChordIntervalsInIncreasingOrderOfPitch() {
		this.nameOfChord = "MajorSeventh";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(4);
		this.listOfIntervalsRelativeToRootInBasePosition.add(7);
		this.listOfIntervalsRelativeToRootInBasePosition.add(11);		
	}

	@Override
	public ChordAbstract clone() {
		try {
			return new MajorSeventhChord((ChordAbstract) this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
