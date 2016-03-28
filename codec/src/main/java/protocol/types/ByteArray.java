package protocol.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CompletionCallback;
import protocol.DefaultInitializer;
import protocol.CodecException;
import protocol.Validator;

public class ByteArray extends Observable implements ByteCodecable, Observer {

	public ByteArray() {
		value = null;
		validators = new LinkedList<Validator>();
	}
	
	public ByteArray(final byte[] b) {
		this();
		value = b;
		resetter = new DefaultInitializer(b);
	}
	
	@Override
	public int encode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
		if (length - offset < size()) {
			h.completed(false);
			return offset;
		}
		if(value != null) {
			for(int i = 0; i < value.length; ++i) {
				bytes[offset + i] = value[i];
			}
			h.completed(true);
			return offset + size();
		} else {
			h.completed(true);
			return offset;
		}
	}

	@Override
	public int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
    	if (length - offset < size()) {
    		h.completed(false);
			return offset;
		}
    	if(size() == 0) {
    		h.completed(true);
    		return offset;
    	}
		try {
			for(int i = 0; i < value.length; ++i) {
				value[i] = bytes[offset + i];
			}
			validate(value);
		} catch (Exception e) {
			throw new CodecException(e);
		}
		setChanged();
		notifyObservers(value);
		h.completed(true);
		return offset + size();
	}


	@Override
	public byte[] getValue() {
		return value;
	}
	
	@Override
	public void setValue(Object v) {
		value = (byte[])v;
	}

	@Override
	public int size() {
		return value != null ? value.length : 0;
	}

	private void validate(byte[] decodedValue) throws Exception {
		for(Validator v : validators) {
			v.validate(decodedValue);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Integer) {
			int length = (Integer) arg;
			value = new byte[length];
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
	
	private byte[] value;
	private List<Validator> validators;

	private DefaultInitializer resetter = new DefaultInitializer(){
		@Override
		public Object initialValue() {
			return value;
		}
	};
	
	@Override
	public void initValue() {
		value = (byte[]) resetter.initialValue();
	}

	@Override
	public void onInitialize(DefaultInitializer r) {
		resetter = r;
	}
}
