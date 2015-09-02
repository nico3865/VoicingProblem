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

import chordsequences.RandomChordSequence;

public class Main {

	public static void main(String argv[]) {

		// preparing the chords and their voicings:
		
		// ChordVoicer cv = new ChordVoicer(new SimpleJazzTurnAround()); //RandomDiatonicChordSequence
		// ChordVoicer cv = new ChordVoicer(new RandomDiatonicChordSequence()); //RandomChordSequence
		ChordVoicer cv = new ChordVoicer(new RandomChordSequence()); 
		new MyMidiFileWriter(cv.getChordSequence());
	
	} 
	
} 
