package chordsequences;

import java.util.ArrayList;

import chords.ChordAbstract;
import chords.DominantSeventhChord;
import chords.MajorSeventhChord;
import chords.MinorSeventhChord;

public class GiantStepsChordSequence extends ChordSequenceAbstract {

	public GiantStepsChordSequence() {
		this.chordSequence = new ArrayList<ChordAbstract>();
		chordSequence.add(new MajorSeventhChord(60));
		chordSequence.add(new MinorSeventhChord(57));
		chordSequence.add(new MinorSeventhChord(62));
		chordSequence.add(new DominantSeventhChord(55));
	}

}
