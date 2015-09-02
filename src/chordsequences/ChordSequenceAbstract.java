package chordsequences;

import java.util.ArrayList;

import chords.ChordAbstract;

public abstract class ChordSequenceAbstract {

	protected ArrayList<ChordAbstract> chordSequence;
	
	public ArrayList<ChordAbstract> getChordSequence() {
		return this.chordSequence;
	}
	
	// transpose function:
	public void transposeChordSequence(){
		// TODO: 
		return;
	}
	
	
}
