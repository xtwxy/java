package protocol;

public interface ByteCodecable {

	/**
	 * Encode this object to bytes.
	 * @param bytes byte buffer for output encoded bytes
	 * @param offset offset to the buffer for output bytes
	 * @param length the size of the buffer
	 * @param h callback indicate that the operation is finished or not
	 * @return new offset to the buffer, the input offset plus the count of bytes encoded. 
	 * 	the input offset if not encoded, maybe buffer size is too small.
	 * @throws CodecException if encode failed.
	 */
	int encode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException;
	
	/**
	 * Decode this object to bytes.
	 * @param bytes byte buffer for input bytes
	 * @param offset offset to the buffer for input bytes
	 * @param length the size of the buffer
	 * @param h callback indicate that the operation is finished or not
	 * @return new offset to the buffer, the input offset plus the count of bytes decoded. 
	 * 	the input offset if not decoded, maybe more bytes expected.
	 * @throws CodecException if decode failed.
	 */
	int decode(byte[] bytes, int offset, int length, CompletionCallback h) throws CodecException;
	
	
	/**
	 * Get the value of this object.
	 * @return value.
	 */
	Object getValue();
	
	/**
	 * Set the value of this object.
	 * @param v the value
	 */
	void setValue(Object v);
	
	/**
	 * Add a validator.
	 * @param v the validator
	 */
	void addValidator(Validator v);
	
	/**
	 * Get encoded size in bytes, of this object, or bytes required to decode
	 * this object. 
	 * @return the count of bytes.
	 */
	int size();
	
	/**
	 * Reset Codec state to initial state.
	 */
	void initValue();

	/**
	 * Set Restter to assign value before encoding.
	 * @param r the initializer.
	 */
	void onInitialize(DefaultInitializer r);
	
}
