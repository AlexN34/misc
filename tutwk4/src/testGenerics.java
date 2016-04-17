import java.util.List;
import java.util.ArrayList;



public class testGenerics {
	/*
	 * // Code without generics List box = ...; Apple apple = (Apple)
	 * box.get(0); // Have to cast box items as apple // Code with generics
	 * List<Apple> box = ...; Apple apple = box.get(0); // Compiler keeps track
	 * of types because we told it // to make sure box only has apples
	 * 
	 * // Type variables delimited by angle brackets, follow // class/interface
	 * name declaration public interface List<T> extends Collection<T>{ ... }
	 * 
	 * // new generic List interface get method: T get(int index);
	 * 
	 * // Generic methods and constructures possible // use <> notation to
	 * specify. // Below accepts ref to List<T> and return a T public static <t>
	 * T getFirst(List<T> list)
	 * 
	 * // Covariance example List<Apple> apples = new ArrayList<Apple>(); List<?
	 * extends Fruit> fruits = apples; // Contravariance example
	 * List<Fruit>fruits = new ArrayList<Fruit>(); List<? super Apple> = fruits;
	 */
	public static void main(String[] args) {
		List<Integer> li = new ArrayList<Integer>();
		List<Integer> ln = li; // illegal
		ln.add(3);
		System.out.println(ln.get(0));
	}
}
