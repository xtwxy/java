package protocol.types;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import protocol.ByteCodecable;
import protocol.CodecException;
import protocol.CompletionCallback;
import protocol.Validator;
import protocol.utils.ShiftByteCodec;

public class DecodeElementArrayAlignFrameTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	boolean decodeCompleted = false;
	
	public void testDecode() throws CodecException {
		byte[] input = new byte[] {
				0x00,
				0x0e,
				0x7e, // start of header
				
				0x03, // command type
				
				0x11, 
				0x00, // frame length
				
				0x01, // port number
				
				(byte)0x80, 
				0x25, 
				0x00, 
				0x00, // baud rate

				0x08, // data bits
				
				0x01, // stop bits
				
				0x6e, // parity, i.e. 'n'
				
				0x00,  
				0x00, // status code 
				
				(byte)0xaf, 
				0x01, // checksum  
				
				0x6e // end of tail
		};
		
		Byte8 soh = new Byte8((byte)0x7e);
		soh.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws Exception {
				if(value == null || !(0x7e == (Byte)value)) {
					throw new CodecException("value != 0x7e");
				}
			}
			
		});
		Byte8 type = new Byte8((byte)0x03);
		Short16LE length = new Short16LE((short)0x11);
		Byte8 port = new Byte8((byte)0x01);
		Integer32LE baudrate = new Integer32LE(0x2580);
		Byte8 databits = new Byte8((byte)0x08);
		Byte8 stopbits = new Byte8((byte)0x01);
		Byte8 parity = new Byte8((byte) 'n');
		Short16BE status = new Short16BE((short)0x0000);
		Short16LE checksum = new Short16LE((short)0x01af);
		Byte8 eot = new Byte8((byte)0x6e);
		
		ByteCodecable[] elements = new ByteCodecable[] {
				soh,
				type,
				length,
				port,
				baudrate,
				databits,
				stopbits,
				parity,
				status,
				checksum,
				eot
		};
		
		ElementArray ea = new ElementArray(elements);
		
		int offset = 0;
<<<<<<< HEAD
		ea.reset();
=======
		ea.initValue();
>>>>>>> 5ee300bc956ea05fc6dcd5e077cdc714fb7c4b6d
		offset = ShiftByteCodec.decode(ea, input, offset, input.length, new CompletionCallback() {

			@Override
			public void completed(boolean b) {
				decodeCompleted = b;
			}
			
		});
		assertEquals(true, decodeCompleted);
		
		byte[] output = new byte[ea.size()];
		ShiftByteCodec.encode(ea, output, 0, output.length, new CompletionCallback() {

			@Override
			public void completed(boolean b) {
				decodeCompleted = b;				
			}
			
		});
		assertEquals(true, decodeCompleted);

		StringBuffer sb = new StringBuffer();
		sb.append("input: ");
		for(byte c : input) {
			sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
		}
		logger.info(sb.toString());
		
		sb = new StringBuffer();
		sb.append("output: ");
		for(byte c : output) {
			sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
		}
		logger.info(sb.toString());
	}

}
