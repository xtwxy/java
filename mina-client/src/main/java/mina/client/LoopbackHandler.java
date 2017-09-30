package mina.client;

import java.io.FileInputStream;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopbackHandler extends IoHandlerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(LoopbackHandler.class);
	private byte[] initial = null;
	
	public LoopbackHandler(String file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			
			initial = new byte[fis.available()];
			fis.read(initial);
			fis.close();
		} catch (Exception e) {
			initial = "[Keep Alive] Are you OK?\n".getBytes(Charset.forName("UTF-8"));
		}
	}
	@Override
    public void sessionOpened(IoSession session) {
	   	session.write(initial);
    }
	 
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
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }  

}
