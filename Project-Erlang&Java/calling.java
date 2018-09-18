import java.util.List;

public class calling extends Thread {
	List<String> Receiver = null;
	Master master;
	public calling(List<String> Receiver,Master mast) {
		// TODO Auto-generated constructor stub
		this.Receiver = Receiver;
		this.master = mast;
	}

	public void run(){
		
		String I = "intro";
		for (int i = 0;i<Receiver.size();i++) {
			try {
				Thread.sleep((long)(Math.random() * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		long time = System.currentTimeMillis();
		introMessage(this.getName(),Receiver.get(i),I,time);
			
		}

		try
		{
			Thread.sleep((long)(Math.random() * 1000));
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		try {
			master.terminate(this.getName());
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
	}

	private synchronized void introMessage(String call, String rec, String i, long time) {
		
		String msg = rec+" received "+i+" message from "+call;
		master.printMsg(msg, time);
		String message = getReplyMessage(call,rec,time);
		master.printMsg(message, time);
	}

	private synchronized String getReplyMessage(String call, String rec, long time) {

		String R = "reply";
		try {
			Thread.sleep((long)(Math.random() * 1000));
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		String message =  call+" received "+R+" message from "+rec;
		return message;
	}
	}
	

