package protocol.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.Validator;

public class Byte8 extends Observable implements ByteCodecable, Observer {

	public Byte8() {
		value = 0;
		validators = new LinkedList<Validator>();
	}
	
	public Byte8(final byte b) {
		this();
		value = b;
		resetter = new Resetter(b);
		validators.add(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(b != (Byte)value) {
					throw new CodecException("value != " + b);
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
		bytes[offset] = value;
		h.completed(true);
		return offset + SIZE;
	}

	@Override
	public int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
    	if (length - offset < SIZE) {
    		h.completed(false);
			return offset;
		}
		byte decodedValue = bytes[offset];
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
	public Byte value() {
		return value;
	}
	
	@Override
	public void value(Object v) {
		value = (Byte)v;
	}

	@Override
	public int size() {
		return SIZE;
	}

	private void validate(byte decodedValue) throws Exception {
		for(Validator v : validators) {
			v.validate(decodedValue);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Byte) {
			value = (Byte) arg;
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
	
	private byte value;
	private List<Validator> validators;

	private static final int SIZE = 1;
	private Resetter resetter = new Resetter(){
		@Override
		public Object reset() {
			return value;
		}
	};
	
	@Override
	public void reset() {
		value = (Byte) resetter.reset();
	}

	@Override
	public void setResetter(Resetter r) {
		resetter = r;
	}
}
