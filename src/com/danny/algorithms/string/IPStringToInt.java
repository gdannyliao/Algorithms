package com.danny.algorithms.string;

public class IPStringToInt {
	public static boolean isCorrectIp(String ip) {
		if (ip == null || ip.length() == 0)
			return false;
		//记录当前状态
		int status = 0;
		//遍历每个字符的指针
		int i = 0;
		//section 记录当前处于ip地址的第几段
		int section = 0;
		while (i < ip.length()) {
			char c = ip.charAt(i);
			switch (status) {
			case 0:
				if (c == '0' || c == '1') {
					status = 1;
				} else if (c == '2') {
					status = 5;
				} else if (c >= '3' && c <= '9') {
					status = 2;
				}
				else return false;
				break;
			case 1:
				if (c >= '0' && c <= '9') {
					status = 2;
				} else if (c == '.') {
					status = 4;
				} else return false;
				break;
			case 2:
				if (c >= '0' && c <= '9') {
					status = 3;
				} else if (c == '.') {
					status = 4;
				} else return false;
				break;
			case 3:
				if (c == '.' && section < 3) {
					status = 4;
				} else return false;
				break;
			case 4:
				//状态4段落数递增1，i不递增
				--i;
				if (section < 3) {
					++section;
					status = 0;
				} 
				else return false;
				break;
			case 5:
				if (c >= '0' && c <= '4') {
					status = 2;
				} else if (c == '5') {
					status = 6;
				} else if (c == '.') {
					status = 4;
				} else return false;
				break;
			case 6:
				if (c >= '0' && c <= '5') {
					status = 3;
				} else if (c == '.')
					status = 4;
				else return false;
				break;
			default:
				break;
			}
			++i;
		}
		if (section != 3)
			return false;
		return true;
	}
	
	public static int toInt(String ip) {
		//当前指向的字符
		int i = ip.length() - 1;
		//当前所在段落
		int section = 0;
		//当前段落的十进制值
		int sectionValue = 0;
		//结果
		int result = 0;
		//段落中的位指针，指向个十百位
		int current = 0;
		
		while (i >= 0) {
			char c = ip.charAt(i);
			//遇到点就把该节的值合并如结果
			if (c == '.') {
				int k = section;
				while (k > 0) {
					sectionValue <<= 8;
					k--;
				}
				result |= sectionValue;
				sectionValue = 0;
				section++;
				current = 0;
			}
			else {
				//计算每个节点的十进制值
				int value = c - '0';
				int j = current;
				while (j > 0) {
					value *= 10;
					j--;
				}
				sectionValue += value;
				++current;
			}
			--i;
		}
		//上面的循环结束后，最后一段还未合并进结果
		while (section > 0) {
			sectionValue <<= 8;
			section--;
		}
		result |= sectionValue;
		
		return result;
	}
	public static void printIsCorrect(String ip) {
		System.out.println("is ip " + ip + " correct:" + IPStringToInt.isCorrectIp(ip));
	}
	public static void test() {
		String ip = "19.2.3";
		printIsCorrect(null);
		printIsCorrect("");
		printIsCorrect(ip);
        printIsCorrect("001.2.003.04");
		printIsCorrect(".2.3.4");
		printIsCorrect("1111.2.3.4");
		printIsCorrect("256.2.2.0");
		printIsCorrect("2..4.5");
		printIsCorrect("0");
		printIsCorrect("0.");
		printIsCorrect("2.3.4.");
		printIsCorrect("23.#.3.4");
		printIsCorrect("sd");
		printIsCorrect("111.111.111.");
		printIsCorrect("111.111.22.33.");
		printIsCorrect("255.255.255.255");

		printIsCorrect("0.0.0.0");
		printIsCorrect("192.168.0.3");
		printIsCorrect("8.8.8.8");
		printIsCorrect("100.100.0.99");
		
		System.out.println(toInt("192.45.3.2"));
		System.out.println(toInt("0.0.0.1"));
		System.out.println(toInt("0.0.1.1"));
		System.out.println(toInt("0.0.2.0"));
		System.out.println(toInt("0.0.10.0"));
		System.out.println(toInt("0.0.100.0"));
		System.out.println(toInt("0.0.255.0"));
		System.out.println(toInt("0.0.255.255"));
		System.out.println(toInt("0.255.0.0"));
		System.out.println(toInt("255.0.0.0"));


	}
}
