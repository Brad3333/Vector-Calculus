package main;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String A = "(5 , 0, 0)";
		String B = "(0, 0, 1)";
		String C = "(2, 1, 0)";
		int[] vec1 = {1, 1, 1};
		int[] vec2 = {1, -6, 0};
		VectorMethods v = new VectorMethods();
		System.out.println(Arrays.toString(v.line(B, C)));
		//System.out.println(v.plane(A, B, C));
		//System.out.println(v.angle(vec1, vec2));
		//System.out.println(v.print(v.line(A, C)));
		//System.out.println(v.distancePointToLine(v.line(A, C), P));
//		System.out.println(v.plane(A, B, C));
//		v.pointOnPlane(v.plane(A, B, C));
//		//v.normalVectorOfPlane(v.plane(s, s2, s3));
//		//System.out.println(v.angle(v.create(A, B), v.create(A, C)));
//		System.out.println(v.distancePointToPlane(v.plane(A, B, C), P));
	
		
	}

}
