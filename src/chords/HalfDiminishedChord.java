package chords;

import java.util.ArrayList;

public class HalfDiminishedChord extends ChordAbstract {
	
	/**
	 * at creation, define what chord it is (i.e. intervals in base position) in the constructor.
	 * @throws Exception 
	 */
	public HalfDiminishedChord(int midiRootNote) throws Exception {
		super(midiRootNote);
	}
	public HalfDiminishedChord(ChordAbstract chordAbstract) throws Exception {
		super(chordAbstract);
	}


	@Override
	protected void defineChordIntervalsInIncreasingOrderOfPitch() {
		this.nameOfChord = "HalfDiminished";
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		this.listOfIntervalsRelativeToRootInBasePosition.add(3);
		this.listOfIntervalsRelativeToRootInBasePosition.add(6);
		this.listOfIntervalsRelativeToRootInBasePosition.add(10);		
	}

	@Override
	public ChordAbstract clone() {
		try {
			return new HalfDiminishedChord((ChordAbstract) this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
