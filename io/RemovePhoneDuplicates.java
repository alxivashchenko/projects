package net.devstudy.jse.lection08_io.home.extra;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



public class RemovePhoneDuplicates {

	public static void main(String[] args) throws IOException  {
		List<String> phones = Files.readAllLines(Paths.get("files/phones.txt"), StandardCharsets.UTF_8);
		Set<String> phonesWithoutDuplicates = new LinkedHashSet<>(phones);
		Files.write(Paths.get("files/unique-phones.txt"), phonesWithoutDuplicates, StandardCharsets.UTF_8);	
		
	}

}
