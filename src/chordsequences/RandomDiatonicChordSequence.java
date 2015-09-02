package chordsequences;

import java.util.ArrayList;
import java.util.Random;

import chords.ChordAbstract;
import chords.DominantSeventhChord;
import chords.HalfDiminishedChord;
import chords.MajorSeventhChord;
import chords.MinorSeventhChord;

public class RandomDiatonicChordSequence extends ChordSequenceAbstract {
	
	Random rand = new Random();

	public RandomDiatonicChordSequence() {
		this.chordSequence = new ArrayList<ChordAbstract>();
		
		// hold all possible diatonic chords in an ArrayList:
		ArrayList<ChordAbstract> possibleChords = new ArrayList<ChordAbstract>();
		possibleChords.add(new MajorSeventhChord(60)); // C
		possibleChords.add(new MinorSeventhChord(62)); // D
		possibleChords.add(new MinorSeventhChord(64)); // E
		possibleChords.add(new MajorSeventhChord(65)); // F*
		possibleChords.add(new DominantSeventhChord(67)); // G
		possibleChords.add(new MinorSeventhChord(69)); // A
		possibleChords.add(new HalfDiminishedChord(71)); // B
		possibleChords.add(new MajorSeventhChord(72)); // C high*
		
		// generate 25 random diatonic chords using that list:
		for(int i=0; i<25; i++) {
			chordSequence.add(possibleChords.get((int) rand.nextInt(possibleChords.size())));
		}
		
	}

}
