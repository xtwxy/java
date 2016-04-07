package dc2800.server.proto;



<<<<<<< HEAD
=======
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.CompletionStatus;
import protocol.Validator;
import protocol.types.Byte8;
import protocol.types.ElementArray;
import protocol.types.Resetter;
import protocol.types.Short16LE;
import protocol.utils.ShiftByteCodec;

public class Frame {
<<<<<<< HEAD
=======
	static Logger logger = LoggerFactory.getLogger(Frame.class);
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
	
	private ElementArray elementArray;
	
	public Frame(ElementArray ea) {
		
		this.elementArray = ea;
	}
	
	public int decode(byte[] input, int offset, int length, CompletionCallback h) throws CodecException {
		return ShiftByteCodec.decode(elementArray, input, offset, length, h);
	}
	public int encode(byte[] input, int offset, int length, CompletionCallback h) throws CodecException {
		return ShiftByteCodec.encode(elementArray, input, offset, length, h);
	}
	
<<<<<<< HEAD
	public static Frame createForDecoding() {
=======
	public static Frame createForDecoding() throws CodecException {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		final ByteCodecable[] bc = createBasic();
		Short16LE lenShort = new Short16LE();
		ElementArray data = new ElementArray();
		lenShort.addObserver(data);
		// 2. 1 byte ADDR
		bc[1] = new Byte8();
		// 3. 1 byte PackNo
		bc[2] = new Byte8();
		// 4. 1 byte CMD
		bc[3] = new Byte8();
		// 5. 2 byte short LEN
		bc[4] = lenShort;
		// 6. n byte DATA
		bc[5] = data;
<<<<<<< HEAD
=======

		Short16LE checksum = new Short16LE();
		
		checksum.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				int calculated = checksum(bc);
				// to void padding '1's on the high word when casting a negative unsigned short to integer. 
				int sum = (0xffff & (Integer) value);
				if(sum != calculated) {
					throw new CodecException("checksum != caculated: sum =  " + sum + ", calculated = " + calculated);
				}
			}

		});
		
		bc[6] = checksum;
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		
		return new Frame(new ElementArray(bc));
	} 
	
<<<<<<< HEAD
	public static Frame createForDecoding(final byte addr, final byte batteryNo, final byte cmd) {
=======
	public static Frame createForDecoding(final byte addr, final byte batteryNo, final byte cmd) throws CodecException {
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		final ByteCodecable[] bc = createBasic(addr, batteryNo, cmd);
		Short16LE lenShort = new Short16LE();
		ElementArray data = new ElementArray();
		lenShort.addObserver(data);
		// 5. 2 byte short LEN
		bc[4] = lenShort;
		// 6. n byte DATA
		bc[5] = data;
		
<<<<<<< HEAD
=======
		bc[6] = new Short16LE((short)checksum(bc));
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		return new Frame(new ElementArray(bc));
	} 
	
	
<<<<<<< HEAD
	public static Frame createForEncoding(final byte addr, final byte batteryNo, final byte cmd, final ElementArray data) {
		final ByteCodecable[] bc = createBasic(addr, batteryNo, cmd);

		final int len = (short) data.size();
		Short16LE lenShort = new Short16LE();
=======
	public static Frame createForEncoding(final byte addr, final byte batteryNo, final byte cmd, final ElementArray data) throws CodecException {
		final ByteCodecable[] bc = createBasic(addr, batteryNo, cmd);

		final int len = (short) data.size();
		Short16LE lenShort = new Short16LE((short)len);
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		lenShort.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws Exception {
				int length = (0xffff & (Short) value);
				if(length > 256) {
					throw new CodecException("value out of range " + length);
				}
			}
		});
		
		lenShort.setResetter(new Resetter() {
			@Override
			public Object reset() {
<<<<<<< HEAD
				return len;
			}
		});

		Short16LE checksum = new Short16LE();
		
		checksum.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				int len = 0;
				for(int i = 1; i < bc.length - 2; ++i) {
					len += bc[i].size();
				}
				byte[] bytes = new byte[len];
				int offset = 0;
				CompletionStatus s = new CompletionStatus();
				for(int i = 1; i < bc.length - 2; ++i) {
					offset = bc[i].encode(bytes, offset, bytes.length, s);
				}
				int calculated = checksum(bytes);
				// to void padding '1's on the high word when casting a negative unsigned short to integer. 
				int sum = (0xffff & (Integer) value);
				if(sum != calculated) {
					throw new CodecException("checksum != caculated: sum =  " + sum + ", calculated = " + calculated);
				}
			}
			
		});
=======
				return (short)len;
			}
		});

>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		// 5. 2 byte short LEN
		bc[4] = lenShort;
		// 6. n byte DATA
		bc[5] = data;
		// 7. 2 byte short CHECKSUM
<<<<<<< HEAD
		bc[6] = checksum;

=======
		bc[6] = new Short16LE((short)checksum(bc));
		
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		return new Frame(new ElementArray(bc));
	}
	
	private static ByteCodecable[] createBasic() {
		final ByteCodecable[] bc = new ByteCodecable[8];
		bc[0] = new ElementArray(new ByteCodecable[] {
				new Byte8((byte) 0xfa), 
				new Byte8((byte) 0x55), 
				new Byte8((byte) 0xfa), 
				new Byte8((byte) 0x55), 
				new Byte8((byte) 0xfa), 
				new Byte8((byte) 0x55)		
		});
		
		bc[7] = new ElementArray(new ByteCodecable[] {
				new Byte8((byte) 0xfd), 
				new Byte8((byte) 0x22), 
				new Byte8((byte) 0xfd), 
				new Byte8((byte) 0x22) 
		});
		
		return bc;
	}
	private static ByteCodecable[] createBasic(final byte addr, final byte batteryNo, final byte cmd) {
		final ByteCodecable[] bc = new ByteCodecable[8];

		// 1. 6 byte SOH = { 0xfa, 0x55, 0xfa, 0x55, 0xfa, 0x55 }
		bc[0] = new ElementArray(new ByteCodecable[] {
				new Byte8((byte) 0xfa), 
				new Byte8((byte) 0x55), 
				new Byte8((byte) 0xfa), 
				new Byte8((byte) 0x55), 
				new Byte8((byte) 0xfa), 
				new Byte8((byte) 0x55)		
		});

		// 2. 1 byte ADDR
		Byte8 bAddr = new Byte8();
		bAddr.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws Exception {
				if(addr != (Byte)value) {
					throw new CodecException("value != " + addr);
				}
			}
		});
		bAddr.setResetter(new Resetter() {
			@Override
			public Object reset() {
				return addr;
			}
		});
		bc[1] = bAddr;
		
		// 3. 1 byte BATTERY NO
		Byte8 bBatNo = new Byte8();
		bBatNo.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws Exception {
				if(batteryNo != (Byte)value) {
					throw new CodecException("value != " + batteryNo);
				}
			}
		});
		bBatNo.setResetter(new Resetter() {
			@Override
			public Object reset() {
				return batteryNo;
			}
		});
		bc[2] = bBatNo;

		// 4. 1 byte CMD
		Byte8 bCmd = new Byte8();
		bCmd.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws Exception {
				if(cmd != (Byte)value) {
					throw new CodecException("value != " + cmd);
				}
			}
		});
		bCmd.setResetter(new Resetter() {
			@Override
			public Object reset() {
				return cmd;
			}
		});
		bc[3] = bCmd;

		// 5. 2 byte LEN
		
		// left to be filled.
		
		// 6. n byte DATA
		
		// left to be filled.
		
		// 7. 2 byte CHECKSUM

		// left to be filled.
		
		// 8. 4 byte EOT = { 0xfd, 0x22, 0xfd, 0x22 }
		bc[7] = new ElementArray(new ByteCodecable[] {
				new Byte8((byte) 0xfd), 
				new Byte8((byte) 0x22), 
				new Byte8((byte) 0xfd), 
				new Byte8((byte) 0x22) 
		});
		
		return bc;
	}
<<<<<<< HEAD
	
=======

	private static int checksum(final ByteCodecable[] bc) throws CodecException {
		int len = 0;
		for(int i = 1; i < bc.length - 2; ++i) {
			len += bc[i].size();
		}
		byte[] bytes = new byte[len];
		int offset = 0;
		CompletionStatus s = new CompletionStatus();
		for(int i = 1; i < bc.length - 2; ++i) {
			offset = bc[i].encode(bytes, offset, bytes.length, s);
			if(!s.isCompleted()) {
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("bytes: ");
		for(byte c : bytes) {
			sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
		}
		logger.info(sb.toString());
		int calculated = checksum(bytes);
		return calculated;
	}
	

>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
	public static int checksum(byte[] ba) {
		int sum = 0;
		for(byte b: ba) {
			sum += b;
		}
		return sum;
	}


	public byte getAddr() {
		return (Byte) getElementArray()[1].value();
	}


	public byte getBatteryNo() {
		return (Byte) getElementArray()[2].value();
	}


	public byte getCmd() {
		return (Byte) getElementArray()[3].value();
	}


	private ByteCodecable[] getElementArray() {
		ByteCodecable[] bc = (ByteCodecable[]) elementArray.value();
		return bc;
	}

	public int sizeInBytes() {
		return elementArray.size();
	}

<<<<<<< HEAD
}
=======
	public void reset() {
		this.elementArray.reset();
	}

}

>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
