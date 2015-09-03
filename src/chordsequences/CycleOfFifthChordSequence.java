package chordsequences;

import java.util.ArrayList;

import chords.ChordAbstract;
import chords.DominantSeventhChord;
import chords.HalfDiminishedChord;
import chords.MajorSeventhChord;
import chords.MinorSeventhChord;

public class CycleOfFifthChordSequence extends ChordSequenceAbstract {

	public CycleOfFifthChordSequence() {
		this.chordSequence = new ArrayList<ChordAbstract>();
		chordSequence.add(new MinorSeventhChord(60));
		chordSequence.add(new DominantSeventhChord(65));
		chordSequence.add(new MajorSeventhChord(58));
		chordSequence.add(new MajorSeventhChord(63));
		chordSequence.add(new HalfDiminishedChord(57));
		chordSequence.add(new DominantSeventhChord(62));
		chordSequence.add(new MinorSeventhChord(55));
	}

}
