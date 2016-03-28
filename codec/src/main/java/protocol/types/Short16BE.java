package protocol.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.DefaultInitializer;
import protocol.Validator;

public class Short16BE extends Observable implements ByteCodecable, Observer {

	public Short16BE() {
		value = 0;
		validators = new LinkedList<Validator>();
	}
	
	public Short16BE(final short s) {
		this();
		value = s;
		resetter = new DefaultInitializer(s);
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
		bytes[offset] = (byte)(0xff & value >> 8);
		bytes[offset + 1] = (byte)(0xff & value);
		h.completed(true);
		return offset + SIZE;
	}

	@Override
	public int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
    	if (length - offset < SIZE) {
    		h.completed(false);
			return offset;
		}
    	short decodedValue = (short)((0xff & bytes[offset + 1]) | (0xff & bytes[offset]) << 8);
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
	public Short getValue() {
		return value;
	}
	
	@Override
	public void setValue(Object v) {
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
	
	private DefaultInitializer resetter = new DefaultInitializer(){
		@Override
		public Object initialValue() {
			return value;
		}
	};
	
	@Override
	public void initValue() {
		value = (Short) resetter.initialValue();
	}

	@Override
	public void onInitialize(DefaultInitializer r) {
		resetter = r;
	}
}
