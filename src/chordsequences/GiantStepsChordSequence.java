package chordsequences;

import java.util.ArrayList;

import chords.ChordAbstract;
import chords.DominantSeventhChord;
import chords.HalfDiminishedChord;
import chords.MajorSeventhChord;
import chords.MinorSeventhChord;

public class GiantStepsChordSequence extends ChordSequenceAbstract {

	public GiantStepsChordSequence() throws Exception {
		this.chordSequence = new ArrayList<ChordAbstract>();
		chordSequence.add(new MajorSeventhChord(59)); // Bmaj7
		chordSequence.add(new DominantSeventhChord(62)); // D7
		chordSequence.add(new MajorSeventhChord(55)); // Gmaj7
		chordSequence.add(new DominantSeventhChord(58)); // Bb7
		chordSequence.add(new MajorSeventhChord(51)); // ----
		chordSequence.add(new MajorSeventhChord(51));
		chordSequence.add(new MinorSeventhChord(57));
		chordSequence.add(new DominantSeventhChord(62));
		chordSequence.add(new MajorSeventhChord(55)); ////////////////
		chordSequence.add(new DominantSeventhChord(58));
		chordSequence.add(new MajorSeventhChord(51));
		chordSequence.add(new DominantSeventhChord(54));
		chordSequence.add(new MajorSeventhChord(59)); // ----
		chordSequence.add(new MajorSeventhChord(59)); 
		chordSequence.add(new MinorSeventhChord(53)); 
		chordSequence.add(new DominantSeventhChord(58));
		chordSequence.add(new MajorSeventhChord(51)); // Ebmaj7 // ----
		chordSequence.add(new MajorSeventhChord(51));
		chordSequence.add(new MinorSeventhChord(57)); 
		chordSequence.add(new DominantSeventhChord(62));
		chordSequence.add(new MajorSeventhChord(55)); // Gmaj7 // ----
		chordSequence.add(new MajorSeventhChord(55)); 
		chordSequence.add(new MinorSeventhChord(49)); 
		chordSequence.add(new DominantSeventhChord(54));
		chordSequence.add(new MajorSeventhChord(47)); //Bbmaj7 // ----
		chordSequence.add(new MajorSeventhChord(47)); 
		chordSequence.add(new MinorSeventhChord(53)); 
		chordSequence.add(new DominantSeventhChord(58));
		chordSequence.add(new MajorSeventhChord(51)); // ======
		chordSequence.add(new MajorSeventhChord(51)); 
		chordSequence.add(new MinorSeventhChord(49)); 
		chordSequence.add(new DominantSeventhChord(54));



	}

}
