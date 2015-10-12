package com.danny.algorithms.string;


public class IsChangeOrderWord {
	public boolean isChangeOrderWord(String word1, String word2) {
		if (word1 == null || word2 == null)
			return false;
		if (word1.length() != word2.length())
			return false;
		
		int[] chars = new int[27];
		for (int i=0; i<word1.length(); i++) {
			char c = word1.charAt(i);
			if (c == '\'')
				chars[26]++;
			else {
				if (c >= 'A' && c <= 'Z') {
					c -= 'A';
					chars[c]++;
				} else if (c >= 'a' && c <= 'z') {
					c -= 'a';
					chars[c]++;
				}
				else throw new IllegalStateException("word1 is not a correct word");
			}
		}
		
		for (int i=0; i<word2.length(); i++) {
			char c = word2.charAt(i);
			if (c == '\'')
				chars[26]--;
			else {
				if (c >= 'A' && c <= 'Z') {
					c -= 'A';
					chars[c]--;
				} else if (c >= 'a' && c <= 'z') {
					c -= 'a';
					chars[c]--;
				}
				else throw new IllegalStateException("word2 is not a correct word");
			}
		}
		
		for (int c : chars) {
			if (c != 0)
				return false;
		}
		return true;
	}

	private void print(String word1, String word2) {
		System.out.println(word1 + " and " + word2 + " is same? :" + isChangeOrderWord(word1, word2));
	}
	
	public static void test() {
		IsChangeOrderWord i = new IsChangeOrderWord();
		String word1 = "oldsport";
		String word2 = "dtoopsrl";
		i.print(word1, word2);
		
		word1 = "sdf'";
		word2 = "f'ds";
		i.print(word1, word2);
		i.print("Sd", "DS");
		i.print("%", null);
		i.print("a", "A");
	}
}
