package protocol.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.Validator;

public class Short16LE extends Observable implements ByteCodecable, Observer {

	public Short16LE() {
		value = 0;
		validators = new LinkedList<Validator>();
	}
	
	public Short16LE(final short s) {
		this();
		value = s;
		resetter = new Resetter(s);
		validators.add(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(s != (Short)value) {
					throw new CodecException("value != " + s);
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
		h.completed(true);
		return offset + SIZE;
	}

	@Override
	public int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
    	if (length - offset < SIZE) {
    		h.completed(false);
			return offset;
		}
    	short decodedValue = (short)((0xff & bytes[offset]) | (0xff & bytes[offset + 1]) << 8);
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
	public Short value() {
		return value;
	}
	
	@Override
	public void value(Object v) {
		value = (Short)v;
	}

	@Override
	public int size() {
		return SIZE;
	}

	private void validate(short decodedValue) throws Exception {
		for(Validator v : validators) {
			v.validate(decodedValue);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Short) {
			value = (Short) arg;
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
	
	private short value;
	private List<Validator> validators;

	private static final int SIZE = 2;
	
	private Resetter resetter = new Resetter(){
		@Override
		public Object reset() {
			return value;
		}
	};
	
	@Override
	public void reset() {
		value = (Short) resetter.reset();
	}

	@Override
	public void setResetter(Resetter r) {
		resetter = r;
	}
}
