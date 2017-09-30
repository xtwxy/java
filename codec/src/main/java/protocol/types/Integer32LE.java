package protocol.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
<<<<<<< HEAD
=======
import protocol.DefaultInitializer;
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
import protocol.Validator;

public class Integer32LE extends Observable implements ByteCodecable, Observer {

	public Integer32LE() {
		value = 0;
		validators = new LinkedList<Validator>();
	}
	
	public Integer32LE(final int i) {
		this();
		value = i;
<<<<<<< HEAD
		resetter = new Resetter(i);
=======
		resetter = new DefaultInitializer(i);
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		validators.add(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(i != (Integer)value) {
					throw new CodecException("value != " + i);
				}
			}
			
		});
	}
	
	@Override
	public int encode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
		if (length - offset < SIZE) {
			h.completed(false);
			return offset;
		}
		bytes[offset] = (byte)(0xff & value);
		bytes[offset + 1] = (byte)(0xff & value >> 8);
		bytes[offset + 2] = (byte)(0xff & value >> 16);
		bytes[offset + 3] = (byte)(0xff & value >> 24);
		h.completed(true);
		return offset + SIZE;
	}

	@Override
	public int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
    	if (length - offset < SIZE) {
    		h.completed(false);
			return offset;
		}
    	int decodedValue = (
				(0xff & bytes[offset]) 
				| (0xff & bytes[offset + 1]) << 8
				| (0xff & bytes[offset + 2]) << 16
				| (0xff & bytes[offset + 3]) << 24
			);
		try {
			validate(decodedValue);
			value = decodedValue;
		} catch (Exception e) {
			throw new CodecException(e);
		}
		setChanged();
		notifyObservers(value);
		h.completed(true);
		return offset + SIZE;
	}


	@Override
	public Integer getValue() {
		return value;
	}
	
	@Override
	public void setValue(Object v) {
		value = (Integer)v;
	}

	@Override
	public int size() {
		return SIZE;
	}

	private void validate(int decodedValue) throws Exception {
		for(Validator v : validators) {
			v.validate(decodedValue);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Integer) {
			value = (Integer) arg;
			// do not call notifyObservers() to notify observers
			// since it may cause cyclic call, which is an endless recursion.
		} else {
			// cannot update...
		}
	}


	@Override
	public void addValidator(Validator v) {
		validators.add(v);
	}

	private int value;
	
	private List<Validator> validators;

	private static final int SIZE = 4;
<<<<<<< HEAD
	private Resetter resetter = new Resetter(){
		@Override
		public Object reset() {
=======
	private DefaultInitializer resetter = new DefaultInitializer(){
		@Override
		public Object initialValue() {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
			return value;
		}
	};
	
	@Override
	public void initValue() {
		value = (Integer) resetter.initialValue();
	}

	@Override
	public void onInitialize(DefaultInitializer r) {
		resetter = r;
	}
}
