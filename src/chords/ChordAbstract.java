package chords;

import java.util.ArrayList;

public class ChordAbstract {

	// input variables:
	// (to be defined by whoever creates a subclass for a new type of chord (say sharp 11th, or even any new aggregate of notes)):
	protected String nameOfChord;
	protected ArrayList<Integer> listOfIntervalsRelativeToRootInBasePosition; // can be "voiced" by sending any note one octave lower or higher
	protected Integer rootNote;
	
	// output variable:
	private ArrayList<Integer> chordNotesAfterVoicing = null; // must be null until actual voicing was defined by someone, using function below
	private ArrayList<Integer> noteIndexesByIncreasingPitchOrder = null;
	
	
	/// ---------------------------- constructors: ---------------------------- 
	
	// constructor MUST define a rootNote when creating a concrete chord object:
	public ChordAbstract(int midiRootNote) {
		this.rootNote = midiRootNote; 
	}
	
	// copy constructor:
	public ChordAbstract(ChordAbstract oldChord) {
		this.nameOfChord = oldChord.nameOfChord;
		//this.listOfIntervalsRelativeToRootInBasePosition = oldChord.listOfIntervalsRelativeToRootInBasePosition; // NO deep copy!
		this.listOfIntervalsRelativeToRootInBasePosition = new ArrayList<Integer>();
		for(Integer intervalInOldChord : oldChord.listOfIntervalsRelativeToRootInBasePosition) {
			this.listOfIntervalsRelativeToRootInBasePosition.add(intervalInOldChord);
		}
		this.rootNote = oldChord.rootNote;
		this.chordNotesAfterVoicing = oldChord.chordNotesAfterVoicing;
		
	}

	// ----------------------------- getters and setters: ---------------------------- 
	
	public ArrayList<Integer> getListOfIntervalsRelativeToRootInBasePosition() {
		return listOfIntervalsRelativeToRootInBasePosition;
	}

	public Integer getRootNote() {
		return rootNote;
	}

		
	// ----------------------------- functions: ---------------------------- 
	
	// whoever uses this chord class is free to voice it as they please, with this function:
	public void setVoicing(ArrayList<Integer> listOfOctavesOfDisplacementForEachNoteOfTheChord) {
		
		// init voiced chord:
		this.chordNotesAfterVoicing = new ArrayList<Integer>();
		
		// for each of the notes, set its actual pitch using the octaves array param passed:
		int noteCounter = 0;
		
		// start with root note before doing the loop:
		Integer currentChordNoteNonVoiced = rootNote;
		Integer currentChordNoteAfterChangingOctaveForVoicing = returnOctaveForNote(currentChordNoteNonVoiced,listOfOctavesOfDisplacementForEachNoteOfTheChord.get(noteCounter++));
		this.chordNotesAfterVoicing.add(currentChordNoteAfterChangingOctaveForVoicing);
		
		// then do it only as many times as there are intervals in the chord:
		for(Integer i : listOfIntervalsRelativeToRootInBasePosition) {
			currentChordNoteNonVoiced = rootNote + i;
			currentChordNoteAfterChangingOctaveForVoicing = returnOctaveForNote(currentChordNoteNonVoiced,listOfOctavesOfDisplacementForEachNoteOfTheChord.get(noteCounter++));
			this.chordNotesAfterVoicing.add(currentChordNoteAfterChangingOctaveForVoicing);
		}
		
		// that's it, the chord has concrete integer values now representing midi pitches.
		
		// service for other classes: get the new order of note indexes by increasing pitch:
		// REASON: notes are not in order of pitch anymore if voiced, they are in order of pitch ONLY WHEN IN BASE POSITION.
		int numOfNotesInChord = this.getListOfIntervalsRelativeToRootInBasePosition().size() + 1;
		Integer lowestNote = -1; // start with something out of midi range.
		ArrayList<Integer> listOfAlreadyLowerIndexes = new ArrayList<Integer>(); 
		boolean skip = false;
		this.noteIndexesByIncreasingPitchOrder = new ArrayList<Integer>();
		for(int counterOfNotesInChord=0; counterOfNotesInChord<this.chordNotesAfterVoicing.size(); counterOfNotesInChord++) { // each time we find a new lowest note, until we find the lowest.
			lowestNote = -1;
			for (int i = 0; i < numOfNotesInChord; i++) { // find among the remaining notes which is the lowest
				
				// ignore notes that already have been seen as lower and added to our listOfAlreadyTakenIndexes:
				skip = false;
				for(int indexOfAlreadyLowerIndexes=0; indexOfAlreadyLowerIndexes<listOfAlreadyLowerIndexes.size(); indexOfAlreadyLowerIndexes++) {
					if(listOfAlreadyLowerIndexes.get(indexOfAlreadyLowerIndexes) == i) {
						skip = true;
						break;
					}
				}
				
				if(!skip){
					Integer newNote = this.chordNotesAfterVoicing.get(i); // beware, BAD was this: chord1.getVoicedChord().get(numOfNotesInChord_Chord1 - 1);
					if (lowestNote > newNote || lowestNote == -1) {
						lowestNote = newNote;
						listOfAlreadyLowerIndexes.add(i);
						this.noteIndexesByIncreasingPitchOrder.add(i);
					}
				} 
			}
			
		}
		System.out.println("this.chordNotesAfterVoicing: " + this.chordNotesAfterVoicing);
		System.out.println("this.noteIndexesByIncreasingPitchOrder: " + this.noteIndexesByIncreasingPitchOrder); // TESTING

		
		
	}

	private Integer returnOctaveForNote(Integer note, Integer numOfOctaves) {
		return note + 12 * numOfOctaves;
	}
	
	// output, retrieve chord: 
	public ArrayList<Integer> getVoicedChord() {
		return chordNotesAfterVoicing;
	}
	
	public ArrayList<Integer> getIndexesByIncreasingPitchOrder() {
		return this.noteIndexesByIncreasingPitchOrder;
	}



	

}
