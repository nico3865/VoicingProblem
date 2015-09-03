package voicing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import chords.ChordAbstract;
import chordsequences.ChordSequenceAbstract;

public class ChordVoicer {

	protected ChordSequenceAbstract chordSequence = null;
	private ArrayList<ArrayList<Integer>> ListOfPermutations = new ArrayList<ArrayList<Integer>>(); // since method is recursive, simpler to keep as member var
	
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

			System.out.println("=========== chord #" + i + " in sequence ===========");

			// deduce the next chord using the voiceTwoChords function:
			try {
				ChordAbstract chord1 = this.chordSequence.getChordSequence().get(i);
				ChordAbstract chord2 = this.chordSequence.getChordSequence().get(i + 1);
				// ChordAbstract voicedChord = voiceTwoChords_MelodicStrategy(chord1, chord2); // because java passes references as function params, the values are already updated at this point
				// this.chordSequence.getChordSequence().set(i + 1, voicedChord);
				ChordAbstract voicedChord = voiceTwoChords_GapMinimizor(chord1, chord2); // because java passes references as function params, the values are already updated at this point

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
		for (int i = 0; i < numOfNotesInChord_Chord1; i++) {
			// notes are not in order of pitch if voiced, they are in order of pitch WHEN IN BASE POSITION.
			Integer newNote = chord1.getVoicedChord().get(i); // beware, BAD was this: chord1.getVoicedChord().get(numOfNotesInChord_Chord1 - 1);
			if (highestNote_Chord1 < newNote) {
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
			int numOctaves = (int) ((difference + 6 * ((int) Math.signum(difference))) / 12); // can't use Math.floor here since we want -1.10 to become 1.00, and not -2.00

			int differenceIfAlreadyMinimal = highestNote_Chord1 - (note_Chord2 + 12 * numOctaves); // difference % 6; // 6 because then shifting octaves can't help to minimize the interval, it's already in the minimum range

			// keep min: determine if it's the note of the chord with the min melodic distance to the highest note of the previous chord:
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

			if (noteIndex <= minIndex) {
				octavesForFinalVoicing.add(numOctaves_forMinInterval);
			} else {
				// if it's a note of the chord that should be below our new melodic note for this chord, move it one octave down:
				octavesForFinalVoicing.add(numOctaves_forMinInterval - 1);
			}
		}
		chord2.setVoicing(octavesForFinalVoicing);

		System.out.println("chord2: " + chord2.getVoicedChord());
		System.out.println();

		// --> ERROR CHECKS:

		// check if there's a big gap between top notes:
		int MeasuredDifferenceAfterVoicing = Math.abs(highestNote_Chord1 - desiredHighestNote_Chord2_AfterVoicingItUpOrDown);
		if (MeasuredDifferenceAfterVoicing > 2) {
			System.out.println("ERROR? verify highest notes, there shouldn't be such a big difference between top notes");
			System.out.println("MeasuredDifferenceAfterVoicing: " + MeasuredDifferenceAfterVoicing);
		}
		// check if desired highest note is indeed highest after voicing:
		int maxNote = -1;
		for (int i = 0; i < chord2.getVoicedChord().size(); i++) {
			if (maxNote < chord2.getVoicedChord().get(i)) {
				maxNote = chord2.getVoicedChord().get(i);
			}
		}
		if (maxNote != desiredHighestNote_Chord2_AfterVoicingItUpOrDown) {
			System.out.println("ERROR? verify voicing");
		}

		// TEST --> to find when and where the voicings are changed:
		for (int i = 0; i < this.chordSequence.getChordSequence().size(); i++) {
			if (this.chordSequence.getChordSequence().get(i).getVoicedChord() != null) {
				System.out.print(this.chordSequence.getChordSequence().get(i).getVoicedChord());
				System.out.print(this.chordSequence.getChordSequence().get(i));
				System.out.println();
			}
		}

		return chord2;
	}

	// get the square distance (or abs distance) between notes of chord1 and all possibilities of voicings for chord2 --> then pick the smallest square distance:
	// NB --> only works for a fixed number of parts/voices, constant throughout the chord sequence.
	private ChordAbstract voiceTwoChords_GapMinimizor(ChordAbstract chord1, ChordAbstract chord2) {

		// if first chord doesn't have a voicing defined yet, take any voicing arbitrarily for now:
		if (((ChordAbstract) chord1).getVoicedChord() == null) {
			ArrayList<Integer> octaves = new ArrayList<Integer>();
//			octaves.add(0);
//			octaves.add(0);
//			octaves.add(0);
//			octaves.add(0);
			octaves.add(-1);
			octaves.add(0);
			octaves.add(1);
			octaves.add(2);
			chord1.setVoicing(octaves);
		}

		// measure the distance between previous chord1 note and corresponding notes in chord2:

		// to do this, first get the chord2 in base position:
		ArrayList<Integer> octaves2 = new ArrayList<Integer>();
		octaves2.add(0);
		octaves2.add(0);
		octaves2.add(0);
		octaves2.add(0);
		chord2.setVoicing(octaves2);

		// then get the order of notes in chord1, pitch wise, because it is likely voiced, i.e. not in base position:
		// --DONE: TURN THIS INTO A FUNCTION MEMBER OF THE CHORDABSTRACT CLASS:
		// get order of notes, this is now done every time the setVoicing function is called.
		// ArrayList<Integer> chord1NotesInOrderOfPitch = chord1.getIndexesByIncreasingPitchOrder();

		// then for every voicing of chord2 (every possible pairing of notes from chord2 to notes from chord1),
		// ... measure the absolute distance of all notes, and keep the voicing that minimizes it.
		Set<Integer> voicedChord_asSet = new HashSet<Integer>(chord2.getVoicedChord());
		ListOfPermutations = new ArrayList<ArrayList<Integer>>(); // is this necessary? causing the error?
		ArrayList<ArrayList<Integer>> listOfPermutations = permutations(voicedChord_asSet, new Stack<Integer>(), voicedChord_asSet.size());
		
		int minCumulativeAbsDistance = 999999999; // very big number 
		int bestVoicingIndex = -1;
		int indexOfPossibleVoicing = 0;
		ArrayList<Integer> listOfOctavesOfDisplacementForEachNoteOfTheChord;
		ArrayList<Integer> listOfOctavesOfDisplacementForEachNoteOfTheChord_ForBestVoicingSoFar = null;
		ArrayList<Integer> bestVoicing = null;
		for(ArrayList<Integer> possibleVoicing : listOfPermutations) { // for all possible voicing permutations:
			int cumulativeAbsDistanceForOneChord = 0;
			listOfOctavesOfDisplacementForEachNoteOfTheChord = new ArrayList<Integer>();
			int counterOfZeroIntervalsInOneChord2 = 0;
			for(int i=0; i<possibleVoicing.size(); i++) { // for each note, measure the distance to the corresponding note in chord1:
				
				// make sure that it's within 6+/- of the other note
				int difference = chord1.getVoicedChord().get(chord1.getIndexesByIncreasingPitchOrder().get(i)) - possibleVoicing.get(i); // chord1 might not be in pitch order, but chord2 is since we just did it.

				// get num of octave(s) to shift up or down:
				int numOctaves = (int) ((difference + 6 * ((int) Math.signum(difference))) / 12); // can't use Math.floor here since we want -1.10 to become 1.00, and not -2.00
				listOfOctavesOfDisplacementForEachNoteOfTheChord.add(numOctaves);
				
				int differenceIfAlreadyMinimal = chord1.getVoicedChord().get(i) - (possibleVoicing.get(i) + 12 * numOctaves); // difference % 6; // 6 because then shifting octaves can't help to minimize the interval, it's already in the minimum range
				int absOfDiffIfMin = (int) Math.abs(differenceIfAlreadyMinimal); // Math.sqrt(diff) to give less weight to a big gap as long as most other gaps are very small
				cumulativeAbsDistanceForOneChord = (int) (cumulativeAbsDistanceForOneChord + absOfDiffIfMin);
				//System.out.println();
				
				if(differenceIfAlreadyMinimal == 0) {
					//System.out.println();
					counterOfZeroIntervalsInOneChord2++;
				}
				
			}
			// keep min:
			if(counterOfZeroIntervalsInOneChord2 == 2) {
				System.out.println("in a cycle of fiths, the best voicing should have 2 unisons.");
			}
			if(minCumulativeAbsDistance > cumulativeAbsDistanceForOneChord) {
				minCumulativeAbsDistance = cumulativeAbsDistanceForOneChord;
				bestVoicingIndex = indexOfPossibleVoicing;
				listOfOctavesOfDisplacementForEachNoteOfTheChord_ForBestVoicingSoFar = new ArrayList<Integer>(listOfOctavesOfDisplacementForEachNoteOfTheChord);
				bestVoicing = possibleVoicing;
			}
			indexOfPossibleVoicing++;
		}
		System.out.println("bestVoicingIndex: "+bestVoicingIndex);
		System.out.println("minCumulativeAbsDistance (should never be more than 3 or 4 in a cycle of fifths): " + minCumulativeAbsDistance);
		System.out.println("listOfOctavesOfDisplacementForEachNoteOfTheChord_ForBestVoicingSoFar: "+listOfOctavesOfDisplacementForEachNoteOfTheChord_ForBestVoicingSoFar);
		System.out.println("bestVoicing: "+bestVoicing);
		
		//chord2. = listOfPermutations.get(bestVoicingIndex);
		//chord2.setVoicing(listOfOctavesOfDisplacementForEachNoteOfTheChord_ForBestVoicingSoFar); // this is the right way to do it ... but to be refactored.
		chord2.setVoicedChord(bestVoicing, listOfOctavesOfDisplacementForEachNoteOfTheChord_ForBestVoicingSoFar); // this is so wrong but it will work right away, before I refactor.
		
		System.out.println("chord2.getVoicedChord(): "+chord2.getVoicedChord());
		
		return chord2;
	}

	// helper for getting all voicing combinations in voicerizor:
	// BAD: from http://hmkcode.com/calculate-find-all-possible-combinations-of-an-array-using-java/
	// from http://stackoverflow.com/questions/14132877/order-array-in-every-possible-sequence
	private ArrayList<ArrayList<Integer>> permutations(Set<Integer> items, Stack<Integer> permutation, int size) {
		
		//ArrayList<ArrayList<Integer>> ListOfPermutations = new ArrayList<ArrayList<Integer>>(); // since method is recursive, simpler to keep as member var

		/* permutation stack has become equal to size that we require */
		if (permutation.size() == size) {
			/* print the permutation */
			System.out.println(Arrays.toString(permutation.toArray(new Integer[0])));
			ListOfPermutations.add(new ArrayList<Integer>());
			ListOfPermutations.set(ListOfPermutations.size()-1, new ArrayList<Integer>(Arrays.asList(permutation.toArray(new Integer[0]))));
		}

		/* items available for permutation */
		Integer[] availableItems = items.toArray(new Integer[0]);
		for (Integer i : availableItems) {
			/* add current item */
			permutation.push(i);

			/* remove item from available item set */
			items.remove(i);

			/* pass it on for next permutation */
			permutations(items, permutation, size);

			/* pop and put the removed item back */
			items.add(permutation.pop());
		}
		
		return ListOfPermutations;
	}

}
