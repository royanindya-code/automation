package com.qa.ndtv.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

public class CommonHelper {

	// Read locators stored in the yaml file and return as a Map<String, Object>
	public Map<String, Object> readLocators(String path) {
		try {
			List<File> files = readFiles(path);
			for(File file: files) {
				Yaml yaml = new Yaml();
				FileInputStream fileInputStream = new FileInputStream(file);
				Map<String, Object> obj =  yaml.load(fileInputStream);
			    return obj;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<File> readFiles(String path) throws IOException {
		return Files.walk(Paths.get(path))
			    .filter(Files::isRegularFile)
			    .map(Path::toFile)
			    .collect(Collectors.toList());
	}
	
	// Returns true if it is present within the list, otherwise return false
	public boolean isPresent(List<String> list, String value) {
		return list.stream()
				.anyMatch(v1 -> v1.replaceAll("[^a-zA-Z0-9]", "")
				.equalsIgnoreCase(value));
	}
	
	/**
	 * Compare values based on the variance. Returns true if the difference within the variance
	 * otherwise return false;
	 * @param value Value
	 * @param value1 Value1
	 * @param variance permissable viriance between value1 and value
	 * @return true if its within range otherwise return false
	 */
	public boolean compare(double value, double value1, double variance) {
		if(Math.abs(value - value1) <= variance) {
			return true;
		}
		return false;
	}
}
