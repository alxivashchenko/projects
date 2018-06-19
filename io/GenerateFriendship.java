package net.devstudy.jse.lection08_io.home.extra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.mockito.cglib.transform.impl.AddDelegateTransformer;

public class GenerateFriendship {

	public static void main(String[] args) throws IOException {
		int minFriends = 4;
		int maxFriends = 16;

		List<String> maleAccounts = Files.readAllLines(Paths.get("files/people/male-accounts.txt"),
				StandardCharsets.UTF_8);
		List<String> femaleAccounts = Files.readAllLines(Paths.get("files/people/female-accounts.txt"),
				StandardCharsets.UTF_8);
		List<String> accounts = new ArrayList<>();
		accounts.addAll(maleAccounts);
		accounts.addAll(femaleAccounts);
		Collections.shuffle(accounts);
		System.out.println(accounts);

		Map<String, Collection<String>> accountsFriendship = createFriendship(accounts, minFriends, maxFriends);

		
		
		generate("files/people/friendship-accounts.txt", accountsFriendship);

		System.out.println(accountsFriendship);
	} // main

	private static Map<String, Collection<String>> createFriendship(List<String> accounts, int minFriends,
			int maxFriends) {
		Map<String, Collection<String>> accountsFriendship = new TreeMap<>();

		for (String account : accounts) {
			Collection<String> friendsForEachAccount = new HashSet<>();
			int friendsCount = friendsCount(minFriends, maxFriends);

			while (friendsForEachAccount.size() != friendsCount) {
				int index = new Random().nextInt(accounts.size() - 1);
				String friend = accounts.get(index);
				if (!friend.equals(account)) {
					friendsForEachAccount.add(accounts.get(index));
				}
			}
			accountsFriendship.put(account, friendsForEachAccount);
		}
		/*for (Map.Entry<String, Collection<String>> item : accountsFriendship.entrySet()) {
			Collection<String> friends = item.getValue();
			for (String friend : friends) {
				Collection<String> friends2 = accountsFriendship.get(friend);
				if (!friends2.contains(item.getKey())) {
					friends2.add(item.getKey());
				}
			}
		}*/
		
		int maxFactFriends = 0;
		String name = "";
		for (Map.Entry<String, Collection<String>> item2 : accountsFriendship.entrySet()) {
			
			int fact = item2.getValue().size();
			if (maxFactFriends < fact) {
				maxFactFriends = fact;
				name = item2.getKey();
			}
			
		}
		System.out.println("Max quantity of friends = " + maxFactFriends);
		System.out.println(name);
		return accountsFriendship;
	} // createFriendship

	private static void generate(String fileName, Map<String, Collection<String>> accountsFriendship)
			throws IOException {
		try (PrintWriter pw = new PrintWriter(new File(fileName))) {
			for (Map.Entry<String, Collection<String>> item : accountsFriendship.entrySet()) {
				pw.println(item.getKey() + ":");
				pw.println("->" + item.getValue());
				pw.println();
			}

			pw.flush();
		}
	} // generate

	private static int friendsCount(int minFriends, int maxFriends) {
		int friendsCount = minFriends + new Random().nextInt(maxFriends - minFriends);
		return friendsCount;
	} // friendsCount

}
