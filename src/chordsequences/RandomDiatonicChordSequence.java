package chordsequences;

import java.util.ArrayList;
import java.util.Random;

import chords.ChordAbstract;
import chords.DominantSeventhChord;
import chords.HalfDiminishedChord;
import chords.MajorSeventhChord;
import chords.MinorSeventhChord;

public class RandomDiatonicChordSequence extends ChordSequenceAbstract {
	
	private Random rand = new Random();
	private int baseNoteForChords = 52;
	
	public RandomDiatonicChordSequence() {
		this.chordSequence = new ArrayList<ChordAbstract>();
		
		// hold all possible diatonic chords in an ArrayList:
		ArrayList<ChordAbstract> possibleChords = new ArrayList<ChordAbstract>();
		possibleChords.add(new MajorSeventhChord(baseNoteForChords)); // C
		possibleChords.add(new MinorSeventhChord(baseNoteForChords+2)); // D
		possibleChords.add(new MinorSeventhChord(baseNoteForChords+4)); // E
		possibleChords.add(new MajorSeventhChord(baseNoteForChords+5)); // F*
		possibleChords.add(new DominantSeventhChord(baseNoteForChords+7)); // G
		possibleChords.add(new MinorSeventhChord(baseNoteForChords+9)); // A
		possibleChords.add(new HalfDiminishedChord(baseNoteForChords+11)); // B
		possibleChords.add(new MajorSeventhChord(baseNoteForChords+12)); // C high*
		
		// generate 25 random diatonic chords using that list:
		for(int i=0; i<25; i++) {
			ChordAbstract clonedChord = new ChordAbstract(possibleChords.get((int) rand.nextInt(possibleChords.size()))); // beware not to use references to the same chord object many times in the chord sequence!
			chordSequence.add(clonedChord);
		}
		
	}

}
