package chordsequences;

import java.util.ArrayList;
import java.util.Random;

import chords.ChordAbstract;
import chords.DominantSeventhChord;
import chords.HalfDiminishedChord;
import chords.MajorSeventhChord;
import chords.MinorSeventhChord;

public class RandomChordSequence extends ChordSequenceAbstract {
	
	private Random rand = new Random();
	private int baseNoteForChords = 52;

	public RandomChordSequence() {
		this.chordSequence = new ArrayList<ChordAbstract>();
		
		// hold all possible diatonic chords in an ArrayList:
		ArrayList<ChordAbstract> possibleChords = new ArrayList<ChordAbstract>();

		for(int i=0; i<12; i++) {
			possibleChords.add(new MajorSeventhChord(baseNoteForChords+i)); 
			possibleChords.add(new MinorSeventhChord(baseNoteForChords+i)); 
			possibleChords.add(new DominantSeventhChord(baseNoteForChords+i));
			possibleChords.add(new HalfDiminishedChord(baseNoteForChords+i)); 

		}
		
		// generate 25 random diatonic chords using that list:
		for(int i=0; i<25; i++) {
			ChordAbstract clonedChord = new ChordAbstract(possibleChords.get((int) rand.nextInt(possibleChords.size()))); // beware not to use references to the same chord object many times in the chord sequence!
			chordSequence.add(clonedChord); 
		}
		
	}

}
