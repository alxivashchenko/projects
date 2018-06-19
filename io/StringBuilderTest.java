package net.devstudy.jse.lection08_io.home.extra;

public class StringBuilderTest {

	public static void main(String[] args) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("test");
		System.out.println(stringBuilder);
		stringBuilder.append('W');
		System.out.println(stringBuilder);
		stringBuilder.append(2);
		stringBuilder.append(false);
		System.out.println(stringBuilder);
		char[] array5 = { 'H', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd' };
		stringBuilder.append(array5);
		System.out.println(stringBuilder);
		System.out.println(stringBuilder.charAt(2));
		stringBuilder.insert(3, "String");
		System.out.println(stringBuilder);
		System.out.println(stringBuilder.codePointAt(2));
		System.out.println((char)115);
		System.out.println(stringBuilder.reverse());
		System.out.println(stringBuilder.length());
		stringBuilder.setLength(35);
		System.out.println(stringBuilder.length());
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Java core");
		System.out.println(stringBuffer);
		System.out.println(stringBuffer.length());
		

	}

}
