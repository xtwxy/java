package protocol.utils;

public class ByteConverter {
	public static byte[] short16LeToBytes(short s) {
		return new byte[] {
				(byte)(0xff & s),	
				(byte)(0xff & (s >> 8))	
		};
	}
	public static byte[] int32LeToBytes(int s) {
		return new byte[] {
				(byte)(0xff & s),	
				(byte)(0xff & (s >> 8)),	
				(byte)(0xff & (s >> 16)),	
				(byte)(0xff & (s >> 24))	
		};
	}
	public static byte[] short16BeToBytes(short s) {
		return new byte[] {
				(byte)(0xff & (s >> 8)),	
				(byte)(0xff & s)	
		};
	}
}
