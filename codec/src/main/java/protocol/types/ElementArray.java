package protocol.types;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.CompletionStatus;
<<<<<<< HEAD
=======
import protocol.DefaultInitializer;
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
import protocol.Validator;

public class ElementArray extends Observable implements ByteCodecable, Observer {

	private ByteCodecable[] value;
	private DefaultInitializer resetter = new DefaultInitializer();
	private LinkedList<Validator> validators;
	private int decodeElementIndex = 0;

	public ElementArray() {
<<<<<<< HEAD
		value = new ByteCodecable[0];
=======
		ByteCodecable[] bc = new ByteCodecable[0];
		value = bc;
		resetter = new DefaultInitializer(bc);
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		validators = new LinkedList<Validator>();
	}
	public ElementArray(ByteCodecable[] b) {
		this();
		value = b;
		resetter = new DefaultInitializer(b);
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(arg instanceof Integer) {
			int length = (Integer) arg;
			value = new ByteCodecable[length];
			// do not call notifyObservers() to notify observers
			// since it may cause cyclic call, which is an endless recursion.
		} else {
			// cannot update...
		}
	}

	@Override
	public int encode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
		int decodedOffset = offset;
		CompletionStatus s = new CompletionStatus();
		while(decodeElementIndex < value.length) {
			ByteCodecable bc = value[decodeElementIndex];
			int off = bc.encode(bytes, decodedOffset, length, s);
			if(!s.isCompleted()) {
				h.completed(false);
				return off;
			} else {
				decodeElementIndex += 1;
				decodedOffset = off;
			}
		}
		decodeElementIndex = 0;
		h.completed(true);
		return decodedOffset;
	}

	@Override
	public int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException {
		if(length - offset < size()) {
			h.completed(false);
			return offset;
		}
		int decodedOffset = offset;
		CompletionStatus s = new CompletionStatus();
		for(ByteCodecable bc : value) {
			int off = bc.decode(bytes, decodedOffset, length, s);
			if(!s.isCompleted()) {
				h.completed(true);
				return off;
			} else {
				decodedOffset = off;
			}
		}
		h.completed(true);
		return decodedOffset;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public void setValue(Object v) {
		value = (ByteCodecable[])v;
	}

	@Override
	public void addValidator(Validator v) {
		validators.add(v);
	}

	@Override
	public int size() {
		int length = 0;
		for(ByteCodecable b: value) {
			length += b.size();
		}
		return length;
	}
	public void setAt(int index, ByteCodecable value) {
		this.value[index] = value;
	}
	
	@Override
	public void initValue() {
		decodeElementIndex = 0;
		value = (ByteCodecable[])resetter.initialValue();
		for(ByteCodecable bc : value) {
			bc.initValue();
		}
	}

	@Override
	public void onInitialize(DefaultInitializer r) {
		resetter = r;
	}
}
