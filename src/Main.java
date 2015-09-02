/**
 * midifile.java
 *
 * A very short program which builds and writes
 * a one-note Midi file.
 *
 * author  Karl Brown
 * last updated 2/24/2003
 */

import java.io.*;
import java.util.*;

import javax.sound.midi.*; // package for all midi classes

import midi.MyMidiFileWriter;

import voicing.ChordVoicer;

import chords.ChordAbstract;
import chordsequences.ChordSequenceAbstract;
import chordsequences.RandomChordSequence;
import chordsequences.RandomDiatonicChordSequence;
import chordsequences.SimpleJazzTurnAround;

public class Main {

	public static void main(String argv[]) {

		// preparing the chords and their voicings:
		
		// ChordVoicer cv = new ChordVoicer(new SimpleJazzTurnAround()); //RandomDiatonicChordSequence
		// ChordVoicer cv = new ChordVoicer(new RandomDiatonicChordSequence()); //RandomChordSequence
		ChordVoicer cv = new ChordVoicer(new RandomChordSequence()); //
		ChordSequenceAbstract cs = cv.getChordSequence();
		MyMidiFileWriter mfw = new MyMidiFileWriter(cs);
	
	} // main
	
} // midifile