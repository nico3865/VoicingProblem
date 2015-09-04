package chordsequences;

import java.util.ArrayList;

import chords.ChordAbstract;

/**
 * 
 * @author nick
 *
 * - Simply holds a sequence of chords
 * - Way to think about: "the changes", i.e. a "chorus" for a jazz tune
 * - Can be transposed etc.
 * 
 */
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
