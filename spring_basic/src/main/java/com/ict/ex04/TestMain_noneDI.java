package com.ict.ex04;

public class TestMain_noneDI {
	public static void main(String[] args) {
		Age age = new Age();
		MyProcess mp = new MyProcess("고예찬", "930703", "1", age);
		mp.prn();
		
		MyProcess mp2 = new MyProcess();
		mp2.setName("마이콜");
		mp2.setJumin("031023");
		mp2.setGender("3");
		mp2.setAge(age);
		mp2.prn();
		
	}
}
