package net.devstudy.jse.lection08_io.home.extra;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class FileSearcherMy {
	private final Path rootDir;
	private final String pattern;

	public FileSearcherMy(Path rootDir, String pattern) {
		super();
		validateRootDir(rootDir);
		this.rootDir = rootDir;
		validatePattern(pattern);
		this.pattern = pattern.toLowerCase();
	}

	private void validateRootDir(Path rootDir) {
		Objects.requireNonNull(rootDir);
		if (!Files.exists(rootDir)) {
			throw new IllegalArgumentException("rootDir not found: " + rootDir);
		}
		if (!Files.isDirectory(rootDir)) {
			throw new IllegalArgumentException("rootDir is not directory: " + rootDir);
		}

	}

	private void validatePattern(String pattern) {
		Objects.requireNonNull(pattern);
		if (pattern.contains("*") && pattern.contains("?")) {
			throw new IllegalArgumentException("Invalid pattern: " + pattern);
		}

	}

	public List<Path> find() throws IOException {
		final List<Path> result = new ArrayList<>();
		Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (match(file)) {
					result.add(file);
				}
				return FileVisitResult.CONTINUE;
			}
		});
		return result;
	}

	protected boolean match(Path file) {
		String fileName = file.getFileName().toString().toLowerCase();
		if (pattern.contains("?")) {
			return matchBySymbols(fileName);
		} else if (pattern.contains("*")) {
			return matchByAsterisk(fileName);
		} else {
			return pattern.equals(fileName);
		}

	}

	protected List<String> splitPatternByAsterisk() {
		List<String> res = new ArrayList<>();
		StringBuilder part = new StringBuilder();
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			if (ch == '*') {
				res.add(part.toString());
				part = new StringBuilder();
			} else {
				part.append(ch);
			}
		}
		if (part.length() > 0) {
			res.add(part.toString());
		}
		if (pattern.endsWith("*")) {
			res.add("");
		}

		return res;
	}

	protected boolean matchByAsterisk(String fileName) {
		List<String> parts = splitPatternByAsterisk();
		//условия для начального символа
		if (!parts.get(0).isEmpty()) {
			if (!fileName.startsWith(parts.get(0))) {
				return false;
			}
		}
		
		//условия для конечного символа
		if (!parts.get(parts.size()-1).isEmpty()) {
			if (!fileName.endsWith(parts.get(parts.size()-1))) {
				return false;
			}
		}
		
		// удалить все пустые части
		Iterator<String> it = parts.iterator();
		while (it.hasNext()) {
			if (it.next().isEmpty()) {
				it.remove();
			}
		}
		
		// найти вхождения символов
		int index = -1;
		for (String part : parts) {
			index = fileName.indexOf(part, index + 1);
			if (index == -1) {
				return false;
			}
		}
		
		return true; // если выполнены все условия и есть все вхождения, значит файл найден
	}

	protected boolean matchBySymbols(String fileName) {
		if (fileName.length() != pattern.length()) {
			return false;
		}
		for (int i = 0; i < fileName.length(); i++) {
			char chFile = fileName.charAt(i);
			char chPattern = pattern.charAt(i);
			if (chPattern != '?' && chFile != chPattern) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		//FileSearcherMy fileSearcher = new FileSearcherMy(Paths.get("d:/workspace/jse/bin/net/devstudy/jse."), "*$*.*");
		//FileSearcherMy fileSearcher = new FileSearcherMy(Paths.get("d:/workspace/jse/bin/net/devstudy/jse."), "*$*.cl*ss");
		//????.java 
		//FileSearcherMy fileSearcher = new FileSearcherMy(Paths.get("d:/workspace/jse/src/net/devstudy/jse."), "????.java");
		FileSearcherMy fileSearcher = new FileSearcherMy(Paths.get("d:/workspace/jse/bin/net/devstudy/jse."), "LabelFrame.class");
			
		List<Path> result = fileSearcher.find();
		for (Path path : result) {
			System.out.println(path.getFileName().toString());
		}
	}
}