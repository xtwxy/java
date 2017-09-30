package slaveboard.serialport;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class SerialPort {
	private final int PORT;

	public SerialPort(int port) {
		PORT = port;
	}
	
	public void start() throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();

		//acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new ByteBufferCodecFactory()));

		acceptor.setHandler(new LoopbackHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(PORT));
	}
}
