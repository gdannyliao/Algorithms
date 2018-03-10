package com.ggdsn.algorithms.sort;

import java.util.Random;

public class SortByAge {
	void sort(Person[] arr) {
		if (arr == null || arr.length == 0)
			return;
		
		int[] ages = new int[100];
		
		for (int i=0; i<arr.length; i++) {
			if (arr[i].age >= 100 || arr[i].age < 0)
				throw new IllegalStateException("illegal age");
			ages[arr[i].age]++;
		}
		
		int[] agesBegin = new int[100];
		int lastPos = 0;
		for (int i=1; i<100; i++) {
			agesBegin[i] = lastPos + ages[i-1];
			lastPos = agesBegin[i];
		}
		Person p;
		p = arr[0];
		int curAge = p.age;
		int index = 0;
		int lastIndex = 0;
		for (int i=0; i<arr.length; i++) {
			index = agesBegin[curAge];
			if (arr[index] == p) {
				//FIXME 找到两个对象相同时，该与谁交换
//				++lastIndex;
//				if (lastIndex >= arr.length)
//					break;
//				else p = arr[lastIndex];
//				i--;
			} else {
				while (arr[index].age != 0 && arr[index].age == curAge)
					index++;
				arr[lastIndex] = arr[index];
				arr[index] = p;
				p = arr[lastIndex];
			}
			curAge = p.age;
		}
		
	}
	
	public static void testSort() {
		Random rand = new Random();
//		Person[] arr = new Person[] {
//			new Person(95), new Person(89), new Person(86), new Person(82), new Person(44), new Person(24),new Person(35),
//			new Person(39),new Person(44),new Person(55),new Person(59),new Person(76)
//		};
		Person[] arr = new Person[rand.nextInt(100)];
		for (int i=0; i<arr.length; i++) {
			Person p = new Person();
			p.age = rand.nextInt(100);
			arr[i] = p;
		}
		SortByAge s = new SortByAge();
		s.sort(arr);
		for (Person p : arr) {
			System.out.print(p.age + " ");
		}
		System.out.println();
	}

}
class Person {
	String name;
	int age;
	Person(int age) {
		this.age = age;
	}
	Person() {
		
	}
	@Override
	public String toString() {
		return String.valueOf(age);
	}
}
