package slaveboard.ctrlport;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class FrameHandler extends IoHandlerAdapter {
	
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
    	Frame f = (Frame)message;
    	switch(f.getType()) {
    	case 0x03:
    		Frame rf = new Frame((byte) 0x83, f.getData());
    		rf.getData()[rf.getData().length - 1] = 0x01;
    		session.write(rf);
    		break;
    	default:
    		break;
    	}
        session.write(f);
    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }

}
