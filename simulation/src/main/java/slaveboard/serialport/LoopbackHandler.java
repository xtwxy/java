package slaveboard.serialport;

import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopbackHandler extends IoHandlerAdapter {
	
	Logger logger = LoggerFactory.getLogger(LoopbackHandler.class);
	
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
    	byte[] b = (byte[])message;
//    	StringBuffer sb = new StringBuffer();
//    	for ( byte c : b) {
//    		sb.append(Hex.encodeHexString(new byte[] {c})).append(" ");
//    	}
//    	logger.info(sb.toString());
    	
        session.write(b);
    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
    	//String s = "[Keep Alive] Are you OK?\n";
    	//session.write(s.getBytes(Charset.forName("UTF-8")));
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }

}
