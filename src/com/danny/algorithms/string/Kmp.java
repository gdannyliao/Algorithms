package com.danny.algorithms.string;


public class Kmp {
	int[] getNextArray(String p) {
		int[] next = new int[p.length()];
		next[0] = -1;
		int k = -1;
		//k表示前缀字符，j表示后缀字符
		/*
		 * 拿第一个字符（前缀）跟当前j所指的字符（后缀）比较，相等则拿前缀下一个字符与下一个后缀
		 * 比较，不相等则前缀回退
		 * 
		 */
		int j=0;
		char texti = 0;
		char textj = 0;
		
		while (j<p.length()-1) {
			if (k==-1 || (texti = p.charAt(j)) == (textj = p.charAt(k))) {
				++j;
				++k;
				if (texti != textj)
					next[j] = k;
				else next[j] = next[k];
			} else k = next[k];
		}
//		for (int j=0; j<p.length()-1;) {
//			if (k==-1 || p.charAt(j) == p.charAt(k)) {
//				++j;
//				++k;
//				if (p.charAt(j) != p.charAt(k)) {
//					next[j] = k;
//				} else {
//					next[j] = next[k];
//				}
//			} else {
//				k = next[k];
//			}
//		}
		
		return next;
	}
	int kmp1(String s, String p) {
		int sLen = s.length();
		int pLen = p.length();
		int i=0;
		int j=0;
		
		while (i<sLen && j<pLen) {
			if (j == -1 || s.charAt(i) == p.charAt(j)) {
				j++;
				i++;
			} else {
				j = next[j];
			}
			if (j == pLen)
				return i-j;
		}
		return -1;
	}
	
	int bruteForceMatch(String s, String p) {
		int sLen = s.length();
		int pLen = p.length();
		
		int i = 0, j = 0;
		//终点:当j走到尽头时,说明匹配成功.i走到尽头时, 说明匹配失败 
		while (i<sLen && j<pLen) {
			if (s.charAt(i) == p.charAt(j)) {
				++i;
				++j;
			} else {
				//匹配失败, i倒退回i的后一个
				i = i - j + 1;
				j = 0;
			}
		}
		//匹配成功, 返回i的起始值
		if (j == pLen)
			return i - j;
		else return -1;
	}
	private int[] next;
	

	
	public void testKmp() {
		kmp1("ABCDABDABCDABC", "ABCABABC");
	}
}
