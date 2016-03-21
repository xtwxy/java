package slaveboard;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
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
    		session.write(rf);
    		break;
    	default:
    		break;
    	}
        session.write(f);
//        session.closeOnFlush().addListener(new IoFutureListener<IoFuture>() {
//
//			@Override
//			public void operationComplete(IoFuture f) {
//		        System.out.println("Message written...");
//			}
//        	
//        });
    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }

}
