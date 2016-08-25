package com.example;

public class Sample {

	public static void main(String[] args) {
		// single-line comment
		for (String arg : args) {
			if (arg.length() != 0)
				System.out.println(arg);
			else
				System.err.println("Warning: empty string as argument");
		}
	}

}