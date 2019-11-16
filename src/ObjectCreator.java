import java.util.ArrayList;
import java.util.Scanner;

public class ObjectCreator {
	Scanner scanner;
	public ObjectCreator(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public Object createObject() {
		System.out.println("\nChoose from the following objects to create:");
		System.out.println("1) Object1 - A simple object");
		System.out.println("2) Object2 - An object that contains references to other objects");
		System.out.println("3) Object3 - An object that contains an array of five integers");
		System.out.println("4) Object4 - An object that contains an array of three Object1 references");
		System.out.println("5) Object5 - An object that contains an ArrayList of Object1 references");
		System.out.println("6) Object6 - A five-digit character array");
		System.out.println("Enter a digit between 1-6: ");
		int objectChoice = scanner.nextInt();
		scanner.nextLine();
		
		switch (objectChoice) {
			case 1:
				return createObject1();
			case 2:
				return createObject2();
			case 3:
				return createObject3();
			case 4:
				return createObject4();
			case 5:
				return createObject5();
			case 6:
				return createObject6();
			default:
				System.out.println("Invalid choice. Input must be a digit from 1-6.");
				System.exit(0);
		}
		
		return new Object();
	}
	
	private Object createObject1() {
		System.out.println("\nEnter an integer for Object1.num: ");
		int num = scanner.nextInt();
		return new Object1(num);
	}
	
	private Object createObject2() {
		System.out.println("\nEnter an double for Object2.d: ");
		double d = scanner.nextDouble();
		return new Object2(d, new Object2b(new Object2()));
	}
	
	private Object createObject3() {
		int[] nums = new int[5];
		for (int i = 0; i < nums.length; i++) {
			System.out.println("\nEnter an integer for Object3.num[" + i + "]: ");
			nums[i] = scanner.nextInt();
		}
		return new Object3(nums);
	}
	
	private Object createObject4() {
		Object1[] obj1s = new Object1[3];
		for (int i = 0; i < obj1s.length; i++) {
			System.out.println("\nEnter an integer for Object4.obj1s[" + i + "].num: ");
			obj1s[i] = new Object1(scanner.nextInt());
		}
		return new Object4(obj1s);
	}
	
	private Object createObject5() {
		System.out.println("\nEnter how many Object1 elements you want in the ArrayList: ");
		int length = scanner.nextInt();
		
		ArrayList<Object1> obj1sList = new ArrayList<Object1>();
		for (int i = 0; i < length; i++) {
			System.out.println("\nEnter an integer for Object5.obj1sList[" + i + "].num: ");
			int num = scanner.nextInt();
			obj1sList.add(new Object1(num));
		}
		return new Object5(obj1sList);
	}
	
	private Object createObject6() {
		char[] chars = new char[5];
		for (int i = 0; i < chars.length; i++) {
			System.out.println("\nEnter a single character for Object6[" + i + "]: ");
			String c = scanner.nextLine();
			chars[i] = c.charAt(0);
		}
		return chars;
	}
}
