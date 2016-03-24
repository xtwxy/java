package protocol.test.frames;

import java.util.ArrayList;
import java.util.List;

import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.Validator;
import protocol.types.Byte8;
import protocol.types.Integer32LE;
import protocol.types.Resetter;
import protocol.types.Short16BE;
import protocol.types.Short16LE;

public class TestFixedLengthFrame implements ByteCodecable {

	private ArrayList<ByteCodecable> frameElements = new ArrayList<ByteCodecable>();
	private int decodeElementIndex = 0;
	
	public TestFixedLengthFrame(
			Byte8 soh,
			Byte8 type,
			Short16LE length,
			Byte8 port,
			Integer32LE baudrate,
			Byte8 databits,
			Byte8 stopbits,
			Byte8 parity,
			Short16BE status,
			Short16LE checksum,
			Byte8 eot
			) {
		frameElements.add(soh);
		frameElements.add(type);
		frameElements.add(length);
		
		frameElements.add(port);
		frameElements.add(baudrate);
		frameElements.add(databits);
		frameElements.add(stopbits);
		frameElements.add(parity);
		frameElements.add(status);
		
		frameElements.add(checksum);
		frameElements.add(eot);
	}
	
	@Override
	public int encode(byte[] bytes, int offset, int length) throws CodecException {
		if(length - offset < size()) {
			return offset;
		}
		int decodedOffset = offset;
		while(decodeElementIndex < frameElements.size()) {
			ByteCodecable bc = frameElements.get(decodeElementIndex);
			int off = bc.encode(bytes, decodedOffset, length);
			if(off == decodedOffset) {
				return off;
			} else {
				decodeElementIndex += 1;
				decodedOffset = off;
			}
		}
		decodeElementIndex = 0;
		return decodedOffset;
	}

	@Override
	public int decode(byte[] bytes, int offset, int length) throws CodecException {
		if(length - offset < size()) {
			return offset;
		}
		int decodedOffset = offset;
		for(ByteCodecable bc : frameElements) {
			int off = bc.decode(bytes, decodedOffset, length);
			if(off == decodedOffset) {
				return off;
			} else {
				decodedOffset = off;
			}
		}
		return decodedOffset;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> value() {
		@SuppressWarnings("rawtypes")
		ArrayList l = new ArrayList();	
		for(ByteCodecable bc : frameElements) {
			l.add(bc.value());
		}
		return l;
	}

	@Override
	public void addValidator(Validator v) {
	}

	@Override
	public int size() {
		
		int length = 0;
		
		for(ByteCodecable bc : frameElements) {
			length += bc.size();
		}
		
		return length;
	}

	@Override
	public void reset() {
		decodeElementIndex = 0;
		for(ByteCodecable bc : frameElements) {
			bc.reset();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void value(Object v) {
		frameElements = (ArrayList<ByteCodecable>)v;
	}

	@Override
	public void setResetter(Resetter r) {
	}
}