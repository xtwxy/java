package mina.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class App {
	public static final int CONNECT_TIMEOUT = 10000;

	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

		connector.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(new ByteBufferCodecFactory()));

		connector.setHandler(new LoopbackHandler(args[2]));
		IoSession session;

		ConnectFuture future = connector.connect(new 
				InetSocketAddress(args[0], Integer.parseInt(args[1])));
		future.awaitUninterruptibly();
		session = future.getSession();

		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}

}
