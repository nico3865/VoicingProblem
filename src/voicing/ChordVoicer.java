package voicing;

import java.util.ArrayList;

import chords.ChordAbstract;
import chordsequences.ChordSequenceAbstract;

public class ChordVoicer {

	protected ChordSequenceAbstract chordSequence = null;

	
	// ------------------ constructor: -------------------------
	
	// constructor already populates and voices automatically:
	public ChordVoicer(ChordSequenceAbstract cs) {
		// setChordSequence(new SimpleJazzTurnAround());
		setChordSequence(cs);
		voiceAllChords();
	}

	// ------------------ getters and setters: ---------------
	
	private void setChordSequence(ChordSequenceAbstract cs) {
		this.chordSequence = cs;
	}

	public ChordSequenceAbstract getChordSequence() {
		if (chordSequence != null) {
			return chordSequence;
		}
		else
			return null; // TODO throw error instead
	}

	// ------------------ functions: ------------------  
	
	private void voiceAllChords() {

		// throw error if no chord sequence was passed:
		if (this.chordSequence == null) {
			System.out.println("ERROR, no chord sequence was passed before calling voicing function");
			return;
		}

		// then for each chord in the sequence:
		for (int i = 0; i < this.chordSequence.getChordSequence().size(); i++) {
			
			// watch for end of sequence:
			if (i == this.chordSequence.getChordSequence().size() - 1) {
				return; // we're done, can't voice anything more
			}
			
			System.out.println("=========== chord #"+i+" in sequence ===========");

			// deduce the next chord using the voiceTwoChords function:
			try {
				ChordAbstract chord1 = this.chordSequence.getChordSequence().get(i);
				ChordAbstract chord2 = this.chordSequence.getChordSequence().get(i + 1);
				ChordAbstract voicedChord = voiceTwoChords_MelodicStrategy(chord1, chord2); // because java passes references as function params, the values are already updated at this point
				//this.chordSequence.getChordSequence().set(i + 1, voicedChord);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// these are the voicing functions requested for this problem:
	private ChordAbstract voiceTwoChords_TEST(ChordAbstract chord1, ChordAbstract chord2) {

		// if first chord doesn't have a voicing defined yet, take any voicing arbitrarily for now:
		if (((ChordAbstract) chord1).getVoicedChord() == null) {
			ArrayList<Integer> octaves = new ArrayList<Integer>();
			octaves.add(0);
			octaves.add(0);
			octaves.add(0);
			octaves.add(0);
			chord1.setVoicing(octaves);
		}

		// for now as test, simply play them all in root position:
		ArrayList<Integer> octaves = new ArrayList<Integer>();
		octaves.add(0);
		octaves.add(0);
		octaves.add(0);
		octaves.add(0);
		chord2.setVoicing(octaves);

		return chord2;
	}

	// a first, simple strategy: minimize melodic intervals and then generate only tightly voiced chords
	private ChordAbstract voiceTwoChords_MelodicStrategy(ChordAbstract chord1, ChordAbstract chord2) {

		// if first chord doesn't have a voicing defined yet, take any voicing arbitrarily for now:
		if (((ChordAbstract) chord1).getVoicedChord() == null) {
			ArrayList<Integer> octaves = new ArrayList<Integer>();
			octaves.add(0);
			octaves.add(0);
			octaves.add(0);
			octaves.add(0);
			chord1.setVoicing(octaves);
		}

		// SIMPLE APPROACH:
		// make sure to pick the highest note in the chord that is closest to previous highest note, then voice a small chord (tighly voiced):
		// in other words, only minimize the melody's intervals, and not the other notes in the chords.

		// measure the distance between previous high note and current high note:

		// to do this, first get the chord2 in base position:
		ArrayList<Integer> octaves2 = new ArrayList<Integer>();
		octaves2.add(0);
		octaves2.add(0);
		octaves2.add(0);
		octaves2.add(0);
		chord2.setVoicing(octaves2);

		// get the highest note of the first chord:
		int numOfNotesInChord_Chord1 = chord1.getListOfIntervalsRelativeToRootInBasePosition().size() + 1;
		Integer highestNote_Chord1 = -1; // start with something out of midi range.
		for(int i=0; i<numOfNotesInChord_Chord1; i++) {
			// notes are not in order of pitch if voiced, they are in order of pitch WHEN IN BASE POSITION. 
			Integer newNote = chord1.getVoicedChord().get(i); // beware, BAD was this: chord1.getVoicedChord().get(numOfNotesInChord_Chord1 - 1);
			if(highestNote_Chord1 < newNote) {
				highestNote_Chord1 = newNote;
			}
		}
		System.out.println("chord1: " + chord1.getVoicedChord());
		System.out.println("highestNote_Chord1: " + highestNote_Chord1); // TESTING

		// then get all notes and find the note that would be closest to the highest of the other chord (if it was within the closest range (6+/-) to the other note)
		int numOfNotesInChord_Chord2 = chord2.getListOfIntervalsRelativeToRootInBasePosition().size() + 1;
		Integer note_Chord2;
		int min = 1000; // should never go above that since midi range is smaller than this.
		int minIndex = -1;
		int numOctaves_forMinInterval = 0;
		int desiredHighestNote_Chord2 = -1;
		for (int i = 0; i < numOfNotesInChord_Chord2; i++) {

			note_Chord2 = chord2.getVoicedChord().get(i);

			// then compare them and keep min:
			int difference = highestNote_Chord1 - note_Chord2;

			// get num of octave(s) to shift up or down:
			int numOctaves = (int) ((difference + 6*((int) Math.signum(difference))) / 12); // can't use Math.floor here since we want -1.10 to become 1.00, and not -2.00

			int differenceIfAlreadyMinimal = highestNote_Chord1 - (note_Chord2 + 12 * numOctaves); //difference % 6; // 6 because then shifting octaves can't help to minimize the interval, it's already in the minimum range 

			// determine if it's the note of the chord with the min melodic distance to the highest note of the previous chord:
			int differenceAbs = Math.abs(differenceIfAlreadyMinimal);
			if (min > differenceAbs) {
				min = differenceAbs;
				minIndex = i;
				numOctaves_forMinInterval = numOctaves;
				desiredHighestNote_Chord2 = note_Chord2;
			}
		}
		int desiredHighestNote_Chord2_AfterVoicingItUpOrDown = (desiredHighestNote_Chord2 + 12 * numOctaves_forMinInterval);
		
		System.out.println("chord2" + chord2.getVoicedChord());
		System.out.println("desiredHighestNote_Chord2: " + desiredHighestNote_Chord2); // TESTING
		System.out.println("desiredHighestNote_Chord2_AfterVoicingItUpOrDown: " + desiredHighestNote_Chord2_AfterVoicingItUpOrDown); // TESTING


		// after finding which note would be the closest, voice the chord accordingly:
		// i.e. put all other notes below it:
		// ... and don't forget to put the note at the octave that is nearest to the highest note of previous chord, as detected previously:

		ArrayList<Integer> octavesForFinalVoicing = new ArrayList<Integer>();
		for (int noteIndex = 0; noteIndex < numOfNotesInChord_Chord2; noteIndex++) {
			
			if(noteIndex <= minIndex) {
				octavesForFinalVoicing.add(numOctaves_forMinInterval);			
			} else { 
				// if it's a note of the chord that should be below our new melodic note for this chord, move it one octave down:
				octavesForFinalVoicing.add(numOctaves_forMinInterval - 1);
			}
		}
		chord2.setVoicing(octavesForFinalVoicing);
		
		System.out.println("chord2: "+chord2.getVoicedChord());
		System.out.println();
		
		
		// --> ERROR CHECKS:
		
		// check if there's a big gap between top notes:
		int MeasuredDifferenceAfterVoicing = Math.abs(highestNote_Chord1 - desiredHighestNote_Chord2_AfterVoicingItUpOrDown);
		if(MeasuredDifferenceAfterVoicing > 2) {
			System.out.println("ERROR? verify highest notes, there shouldn't be such a big difference between top notes");
			System.out.println("MeasuredDifferenceAfterVoicing: " + MeasuredDifferenceAfterVoicing);
		}
		// check if desired highest note is indeed highest after voicing:
		int maxNote = -1;
		for(int i=0; i<chord2.getVoicedChord().size(); i++) {
			if(maxNote < chord2.getVoicedChord().get(i)) {
				maxNote = chord2.getVoicedChord().get(i);
			}
		}
		if(maxNote != desiredHighestNote_Chord2_AfterVoicingItUpOrDown) {
			System.out.println("ERROR? verify voicing");
		}
		
		// TEST --> to find when and where the voicings are changed:
		for(int i=0; i<this.chordSequence.getChordSequence().size(); i++) {
			if(this.chordSequence.getChordSequence().get(i).getVoicedChord() != null) {
				System.out.print(this.chordSequence.getChordSequence().get(i).getVoicedChord());
				System.out.print(this.chordSequence.getChordSequence().get(i));
				System.out.println();
			}
		}
		
		return chord2;
	}
}
