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
import chordsequences.GiantStepsChordSequence;
import chordsequences.RandomChordSequence;
import chordsequences.RandomDiatonicChordSequence;

/**
 * 
 * @author nick
 *
 * - Simply instantiates a ChordVoicer with a ChordSequence (automatically voices all the chords)
 * - Then writes the midi file: instantiates MyMidiFileWriter with the chord sequence that was automatically outputted after the ChordVoicer creation
 * 
 * TODO: for in the future:
 * - allow to forcing root note as bass in voicing
 * - new voicing function that will look for continuous scales in a part and favour that if possible, over just minimizing everything
 *  
 */
public class Main {

	public static void main(String argv[]) throws Exception {

		// preparing the chords and their voicings:
		
		// ChordVoicer cv = new ChordVoicer(new SimpleJazzTurnAround()); //RandomDiatonicChordSequence
		// ChordVoicer cv = new ChordVoicer(new RandomDiatonicChordSequence()); //RandomChordSequence
		// ChordVoicer cv = new ChordVoicer(new RandomChordSequence());
		// ChordVoicer cv = new ChordVoicer(new CycleOfFifthChordSequence());
		ChordVoicer cv = new ChordVoicer(new GiantStepsChordSequence());
		
		// outputting to midi file:
		
		new MyMidiFileWriter(cv.getVoicedChordSequence());
	
	} 
	
} 
