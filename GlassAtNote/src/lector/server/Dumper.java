package lector.server;

import java.io.InputStream;

public class Dumper extends Thread {
	private InputStream s;

	public Dumper(InputStream s) {
		super();
		this.s = s;
	}

	public void run() {
		int c;
		try {
			while ((c = s.read()) != -1)
				System.out.print((char) c);
		} catch (Exception e) {
		}
	}
}