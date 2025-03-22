package com.github.temasaur.callstat.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.github.temasaur.callstat.types.CsvStringer;

public class CsvWriter {
	public static void write(String filename, String header, List<CsvStringer> content) throws IOException, InterruptedException {
		java.io.File file = new java.io.File(filename);
		file.getParentFile().mkdirs();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			if (header != null) {
				writer.write(header);
				writer.newLine();
			}
			for (CsvStringer line : content) {
				// sleep to simulate slow writing
				Thread.sleep(100);
				writer.write(line.toString());
				writer.newLine();
			}
		}
	}
}
