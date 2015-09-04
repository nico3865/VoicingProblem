package chords;

import java.util.ArrayList;

public class MinorSeventhChord extends ChordAbstract {

	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 * @throws Exception 
	 */
	public MinorSeventhChord(int midiRootNote) throws Exception {
		super(midiRootNote);
	}
	public MinorSeventhChord(ChordAbstract chordAbstract) throws Exception {
		super(chordAbstract);
	}


	@Override
	protected void defineChordIntervalsInIncreasingOrderOfPitch() {
		this.nameOfChord = "MinorSeventh";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(3);
		this.listOfIntervalsRelativeToRootInBasePosition.add(7);
		this.listOfIntervalsRelativeToRootInBasePosition.add(10);		
	}
	
	@Override
	public ChordAbstract clone() {
		try {
			return new MinorSeventhChord((ChordAbstract) this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
