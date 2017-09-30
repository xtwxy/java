package protocol.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CompletionCallback;
<<<<<<< HEAD
=======
import protocol.DefaultInitializer;
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
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
<<<<<<< HEAD
		resetter = new Resetter(b);
=======
		resetter = new DefaultInitializer(b);
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
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
<<<<<<< HEAD
	public byte[] value() {
=======
	public byte[] getValue() {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		return value;
	}
	
	@Override
<<<<<<< HEAD
	public void value(Object v) {
=======
	public void setValue(Object v) {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
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
<<<<<<< HEAD
	public void reset() {
		value = (byte[]) resetter.reset();
	}

	@Override
	public void setResetter(Resetter r) {
=======
	public void initValue() {
		value = (byte[]) resetter.initialValue();
	}

	@Override
	public void onInitialize(DefaultInitializer r) {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		resetter = r;
	}
}
