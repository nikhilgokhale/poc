package com.example.stats;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class StatsCalculator {
	public static void main(String[] args) throws Exception{
		Map<String, String> sourceData = readAsMap("e:/pubnub_source.csv");
		Map<String, String> destinationData = readAsMap("e:/pubnub_destination.csv");
		CSVWriter writer = new CSVWriter(new FileWriter("e:/pubnub_stats.csv"));
		for(Map.Entry<String, String> entry : sourceData.entrySet()){
			String destTime = destinationData.get(entry.getKey());
			if(destTime != null){
				long travelTime = Long.valueOf(destTime) - Long.valueOf(entry.getValue());
				writer.writeNext(new String[]{String.valueOf(travelTime), entry.getValue()});
			}
		}
		writer.flush();
		writer.close();
	}

	private static Map<String,String> readAsMap(String fileName)
			throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
		List<String[]> lines = reader.readAll();
		Map<String, String> data = new LinkedHashMap<String, String>();
		for(String[] line : lines){
			data.put(line[0], line[1]);
		}
		reader.close();
		return data;
	}
}
