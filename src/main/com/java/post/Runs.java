package java.post;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Runs implements Runnable {
	private ConcurrentLinkedQueue<String> messages;

	public Runs(ConcurrentLinkedQueue<String> messages) {
		this.messages = messages;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int length = messages.size();
		for (int i = 0; i < length; i++) {
			System.out.println(messages.poll());
		}
	}

}