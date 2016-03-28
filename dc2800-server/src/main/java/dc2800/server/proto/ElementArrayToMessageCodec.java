package dc2800.server.proto;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import protocol.types.ElementArray;

public class ElementArrayToMessageCodec extends MessageToMessageCodec<ElementArray, Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ElementArray msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
