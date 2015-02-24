package com.example.stats;

import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import au.com.bytecode.opencsv.CSVWriter;

public class FileLogger {
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private Thread writerThread = null;
	private CSVWriter writer = null;

	public FileLogger(String fileName) {
		try {
			writer = new CSVWriter(new FileWriter(fileName));
			writerThread = new Thread() {
				public void run() {
					while (!isInterrupted()) {
						String msg = null;
						try {
							msg = queue.take();

							if (msg != null) {
								writer.writeNext(new String[] {
										msg,
										String.valueOf(System
												.currentTimeMillis()) });
								writer.flush();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
			};
			writerThread.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public void log(String message) {
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() throws Exception {
		writerThread.interrupt();
		writer.close();
	}
}
