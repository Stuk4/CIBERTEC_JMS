package edu.cibertec.jaad.jms;

public class JMSSusbscribers {
	public static void main(String[] args) {
		int size = 3;
		for (int i = 0; i < size; i++) {
			JMSTopicSubscriber subs = new JMSTopicSubscriber("SUBS-" + i);
			subs.start();
		}
	}
}
