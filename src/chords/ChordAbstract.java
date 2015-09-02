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

		
	// ----------------------------- methods: ---------------------------- 
	
	// whoever uses this chord class is free to voice it as they please, with this function:
	public void setVoicing(ArrayList<Integer> listOfOctavesOfDisplacementForEachNoteOfTheChord) {
		
		// init voiced chord:
		this.chordNotesAfterVoicing = new ArrayList<Integer>();
		
		// for each of the notes, set its actual pitch:
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
		
		// TODO: we could and should add a check for validity: is this a valid voicing of the chord?
		// ... Only octaves of original base position voicing are accepted.
		// ... code:
		
	}

	private Integer returnOctaveForNote(Integer note, Integer numOfOctaves) {
		return note + 12 * numOfOctaves;
	}
	
	// output, retrieve chord: 
	public ArrayList<Integer> getVoicedChord() {
		return chordNotesAfterVoicing;
	}



	

}
