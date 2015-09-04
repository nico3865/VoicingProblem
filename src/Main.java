/**
 * midifile.java
 *
 * A very short program which builds and writes
 * a one-note Midi file.
 *
 * author  Karl Brown
 * last updated 2/24/2003
 */

import midi.MyMidiFileWriter;

import voicing.ChordVoicer;

import chordsequences.CycleOfFifthChordSequence;
import chordsequences.RandomChordSequence;
import chordsequences.RandomDiatonicChordSequence;

public class Main {

	public static void main(String argv[]) {

		// preparing the chords and their voicings:
		
		// ChordVoicer cv = new ChordVoicer(new SimpleJazzTurnAround()); //RandomDiatonicChordSequence
		// ChordVoicer cv = new ChordVoicer(new RandomDiatonicChordSequence()); //RandomChordSequence
		// ChordVoicer cv = new ChordVoicer(new RandomChordSequence());
		ChordVoicer cv = new ChordVoicer(new CycleOfFifthChordSequence());
		new MyMidiFileWriter(cv.getChordSequence());
	
	} 
	
} 
