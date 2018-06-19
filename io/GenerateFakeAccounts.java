package net.devstudy.jse.lection08_io.home.extra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GenerateFakeAccounts {

	public static void main(String[] args) throws IOException {
		int countMale = 100;
		int countFemale = 100;

		List<String> maleNames = normalizeData(
				Files.readAllLines(Paths.get("files/people/male.txt"), StandardCharsets.UTF_8));
		List<String> femaleNames = normalizeData(
				Files.readAllLines(Paths.get("files/people/female.txt"), StandardCharsets.UTF_8));
		List<String> surnames = normalizeData(
				Files.readAllLines(Paths.get("files/people/surname.txt"), StandardCharsets.UTF_8));

		Collections.shuffle(maleNames);
		Collections.shuffle(femaleNames);
		Collections.shuffle(surnames);

		generate("files/people/male-accounts.txt", countMale, maleNames, surnames);
		generate("files/people/female-accounts.txt", countFemale, femaleNames, surnames);

		System.out.println("main");
	} // main

	private static void generate(String fileName, int maxCount, List<String> names, List<String> surnames)
			throws IOException {
		try (PrintWriter pw = new PrintWriter(new FileWriter(new File(fileName)))) {
			for (int i = 0; i < maxCount && i < names.size() && i < surnames.size() && !names.isEmpty()
					&& !surnames.isEmpty(); i++) {
				pw.println(names.remove(0) + " " + surnames.remove(0));
			}
			pw.flush();
		}

	} // generate

	private static List<String> normalizeData(List<String> source) {
		Set<String> set = new LinkedHashSet<>();
		for (String item : source) {
			item = item.toLowerCase().trim();
			item = toNameFormat(item);
			if (item != null) {
				set.add(item);
			}
		}
		List<String> rightFormat = new LinkedList<>(set);
		return rightFormat;
	} // normalizeData

	private static String toNameFormat(String itemToLowerCase) {
		
		if (itemToLowerCase.length() > 1) {
			return Character.toUpperCase(itemToLowerCase.charAt(0)) + itemToLowerCase.substring(1);
		} else {
			return itemToLowerCase.toUpperCase();
		}
		
	} // toNameFormat

}
