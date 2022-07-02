package restautotest;

import java.util.Scanner;

public class BaseURL {
	public static String BASEURI = "https://auctions-app-2.herokuapp.com/";
	
	public void selectURL() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input base url that need testing automated API");
		String baseURI = sc.nextLine();
		this.BASEURI = baseURI;
	}
}
