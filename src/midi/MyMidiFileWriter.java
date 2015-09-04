package midi;
import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

import chords.ChordAbstract;
import chordsequences.ChordSequenceAbstract;

/**
 * 
 * @author nick
 *	
 * - just writes a ChordSequence's midi notes to a file
 * 
 */
public class MyMidiFileWriter {
	
	public MyMidiFileWriter(ChordSequenceAbstract cs) {
		
		System.out.println("midifile begin ");
		try
		{
			// **** Create a new MIDI sequence with 24 ticks per beat ****
			Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ, 24);

			// **** Obtain a MIDI track from the sequence ****
			Track t = s.createTrack();

			// **** General MIDI sysex -- turn on General MIDI sound set ****
			byte[] b = { (byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7 };
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm, (long) 0);
			t.add(me);

			// **** set tempo (meta event) ****
			MetaMessage mt = new MetaMessage();
			byte[] bt = { 0x02, (byte) 0x00, 0x00 };
			mt.setMessage(0x51, bt, 3);
			me = new MidiEvent(mt, (long) 0);
			t.add(me);

			// **** set track name (meta event) ****
			mt = new MetaMessage();
			String TrackName = new String("midifile track");
			mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt, (long) 0);
			t.add(me);

			// **** set omni on ****
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7D, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// **** set poly on ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7F, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// **** set instrument to Piano ****
			mm = new ShortMessage();
			mm.setMessage(0xC0, 0x00, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// // **** note on - middle C ****
			// mm = new ShortMessage();
			// mm.setMessage(0x90, 0x3C, 0x60);
			// me = new MidiEvent(mm, (long) 1);
			// t.add(me);
			//
			// // **** note off - middle C - 120 ticks later ****
			// mm = new ShortMessage();
			// mm.setMessage(0x80, 0x3C, 0x40);
			// me = new MidiEvent(mm, (long) 121);
			// t.add(me);

			// ArrayList<ArrayList<Integer>> chords = new ArrayList<ArrayList<Integer>>();
			// ChordSequenceAbstract chordSequence = cv.getChordSequence();
			ArrayList<ChordAbstract> chordSequence_asArrayList = cs.getChordSequence();
			int barStartTime = 1;
			int barLengthFixed = 140;
			int counter = 0;
			for (ChordAbstract chord : chordSequence_asArrayList) {

				System.out.print("CHORD # "+ (counter++) +": ");
				for (Integer note : chord.getVoicedChord()) {

					System.out.print(note + ", ");

					mm = new ShortMessage();
					// mm.setMessage(0x90, 0x3C, 0x60); //message.setMessage(type (0x90=start,0x80=end), data, data.length);
					mm.setMessage(0x90, note, 0x60);
					me = new MidiEvent(mm, (long) barStartTime);
					t.add(me);

					mm = new ShortMessage();
					mm.setMessage(0x80, note, 0x00);
					me = new MidiEvent(mm, (long) barStartTime + barLengthFixed);
					t.add(me);

				}
				System.out.println();

				barStartTime += barLengthFixed; // increment time to next bar
			}

			// **** set end of track (meta event) 19 ticks later ****
			mt = new MetaMessage();
			byte[] bet = {}; // empty array
			mt.setMessage(0x2F, bet, 0);
			// me = new MidiEvent(mt, (long) 140);
			me = new MidiEvent(mt, (long) barStartTime);
			t.add(me);

			// **** write the MIDI sequence to a MIDI file ****
			File f = new File("/Users/nick/Desktop/midifile.mid");
			MidiSystem.write(s, 1, f);
		} // try
		catch (Exception e)
		{
			System.out.println("Exception caught " + e.toString());
		} // catch
		System.out.println("midifile end ");

	}

}
