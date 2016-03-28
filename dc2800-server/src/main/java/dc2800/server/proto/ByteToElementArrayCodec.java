package dc2800.server.proto;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import protocol.CodecException;
import protocol.CompletionCallback;

public class ByteToElementArrayCodec extends io.netty.handler.codec.ByteToMessageCodec<Frame> {

	private static final int BUFFER_SIZE = 1024;
	private int bytesRead = 0;
	private byte[] readBuffer = new byte[BUFFER_SIZE];
	private Frame decoding = null;
	private boolean decodingComplete = false;
	CompletionCallback completionCallback = new CompletionCallback() {

		@Override
		public void completed(boolean yes) {
			decodingComplete = yes;
		}
		
	};
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Frame msg, ByteBuf out) throws Exception {
		
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		checkDecoding();
		readBytesToBuffer(in);
		int size = 0;
		int newOffset = 0;
		do{
			size = decoding.sizeInBytes();
			
			newOffset = decoding.decode(readBuffer, 0, bytesRead, completionCallback);
			
			shiftBytes(newOffset);
			
		} while(decodingComplete && (bytesRead >= size));
	}

	private void shiftBytes(int offset) {
		if(offset == 0) return;
		if(offset < bytesRead) {
			for(int i = 0; i < (bytesRead - offset); ++i) {
				readBuffer[i] = readBuffer[offset + i];
			}
		} else {
			bytesRead = 0;
		}
	}

	private void readBytesToBuffer(ByteBuf in) {
		int available = in.readableBytes();
		int space = readBuffer.length - bytesRead;
		int bytesToRead = 0;
		if(available > 0) {
			if(available > space) {
				bytesToRead = space;
			} else {
				bytesToRead = available;
			}
			in.readBytes(readBuffer, bytesRead, bytesToRead);
			bytesRead += bytesToRead;
			
		}
	}

	private void checkDecoding() throws CodecException {
		// cannot assuming that a request has been sent before,
		// since the protocol is request/response style.
		if(decoding == null) {
			decoding = Frame.createForDecoding();
		}
	}
}
