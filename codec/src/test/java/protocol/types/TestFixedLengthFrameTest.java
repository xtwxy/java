package protocol.types;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import protocol.CodecException;
import protocol.CompletionStatus;
import protocol.Validator;
import protocol.test.frames.TestFixedLengthFrame;

public class TestFixedLengthFrameTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(this.getClass());

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
		
		TestFixedLengthFrame f = new TestFixedLengthFrame(
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
				);
		logger.info("f.size() = " + f.size());
		byte[] output = new byte[f.size()];
		CompletionStatus s = new CompletionStatus();
		f.encode(output, 0, output.length, s);
		assertEquals(true, s.isCompleted());
		
		StringBuffer sb = new StringBuffer();
		for(byte c : output) {
			sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
		}
		logger.info(sb.toString());
		
		f.initValue();
		int offset = 0;
		while(true) {
			int offsetAfter = 0;
			try {
				offsetAfter = f.decode(input, offset, input.length, s);
				assertEquals(true, s.isCompleted());
			} catch (CodecException e) {
				offset += 1;
				continue;
			}
			offset = offsetAfter;
			break;
		}
		
		logger.info("f.size() = " + f.size());
		output = new byte[f.size()];
		f.encode(output, 0, output.length, s);
		assertEquals(true, s.isCompleted());
		sb = new StringBuffer();
		for(byte c : output) {
			sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
		}
		logger.info(sb.toString());

	}
}
