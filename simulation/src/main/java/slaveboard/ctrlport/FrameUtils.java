package slaveboard.ctrlport;

public class FrameUtils {
	public static final byte SOH = 0x7e;
	public static final byte EOT = 0x6e;
	enum State { DECODE_SOH, DECODE_TYPE, DECODE_LENGTH, DECODE_DATA, DECODE_CHKSUM, DECODE_EOT }

	public static int checksum(byte[] b) {
		int chksum = 0;
		for (byte c : b) {
			chksum += c;
		}
		return chksum;
	}
	
	public static int checksum(Frame f) {
		int chksum = 0xff & 0x7e;
		chksum += 0xff & f.getType();
		int frameLength = f.getData().length + 7;
		chksum += 0xff & frameLength;
		chksum += 0xff & (frameLength >> 8);
		
		chksum += checksum(f.getData());
		
		return chksum;
	}
}
