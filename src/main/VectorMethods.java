package main;

import java.util.Arrays;

public class VectorMethods {
	
	public VectorMethods() {
		
	}
	
	public int[] convert(String point) {
		int[] points = new int[3];
		//(0,0,0)
		point = point.substring(1, point.length() - 1);
		String[] arr = point.split(",");
		for(int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
			points[i] = Integer.parseInt(arr[i]);
		} return points;
	}
	
	public int[] create(String point1, String point2) {
		int[] vector = new int[3];
		int[] p1 = convert(point1);
		int[] p2 = convert(point2);
		for(int i = 0; i < vector.length; i++) {
			vector[i] = p2[i] - p1[i];
		} 
		//System.out.println(Arrays.toString(vector));
		return vector;
	}
	
	public int[] create(int[] p1, int[] p2) {
		int[] vector = new int[3];
		for(int i = 0; i < vector.length; i++) {
			vector[i] = p2[i] - p1[i];
		} 
		//System.out.println(Arrays.toString(vector));
		return vector;
	}
	
	public int dotProduct(int[] v1, int[]v2) {
		int product = 0;
		for(int i = 0; i < v1.length; i++) {
			product += v1[i] * v2[i];
		} 
		//System.out.println(product);
		return product;
	}
	
//	i j k |
//	a b c | (b(f) - c(e))i - (a(f) - c(d))j + (a(e) - b(d))k
//	d e f |
	
	public int[] crossProduct(int[] v1, int[]v2) {
		int[] vector = new int[3];
		vector[0] = (v1[1] * v2[2]) - (v1[2] * v2[1]); //i
		vector[1] = -((v1[0] * v2[2]) - (v1[2] * v2[0])); //j
		vector[2] = (v1[0] * v2[1]) - (v1[1] * v2[0]); //k
		return vector;
	}
	
	public double magnitude(int[] vector) {
		return Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
	}
	
	public double angle(int[] vector1, int[] vector2) {
		//System.out.println(magnitude(vector1) + " " + magnitude(vector2));
		return Math.acos(Math.abs(dotProduct(vector1, vector2)) / (magnitude(vector1) * magnitude(vector2)));
	}
	
	public double distancePointToPlane(String equation, String point) {
		int[] normal = normalVectorOfPlane(equation);
		int[] PR = create(pointOnPlane(equation), convert(point));
		return Math.abs(dotProduct(normal, PR)) / magnitude(normal);
	}
	
	public double distancePointToLine(String[] line, String point) {
		int[] direction = directionVectorOfLine(line);
		int[] PR = create(pointOnLine(line), convert(point));
		return magnitude(crossProduct(direction, PR)) / magnitude(direction);
	}
	
	public String[] line(String point1, String point2) {
		int[] A = convert(point1);
		int[] AB = create(point1, point2);
		String[] line = new String[3];
		for(int i = 0; i < line.length; i++) {
			line[i] = A[i] >= 0 ? AB[i] + "t + " + A[i] : AB[i] + "t " + A[i]; 
		} 
		for(int i = 0; i < line.length; i++) {
			line[i] = fixEquation(line[i]);
		}
		return line;
	}
	
	public String plane(String point1, String point2, String point3) {
		String equation = "";
		int[] A = convert(point1);
		int[] AB = create(point1, point2);
		//System.out.println(Arrays.toString(AB));
		int[] AC = create(point1, point3);	
		//System.out.println(Arrays.toString(AC));
		int[] normal = crossProduct(AB, AC);
		//System.out.println(Arrays.toString(normal));
		String x = A[0] < 0 ? normal[0] + "(x + " + A[0]*-1 + ")" : normal[0] + "(x - " + A[0] + ")";
		String y = A[1] < 0 ? normal[1] + "(y + " + A[1]*-1 + ")" : normal[1] + "(y - " + A[1] + ")";
		String z = A[2] < 0 ? normal[2] + "(z + " + A[2]*-1 + ")" : normal[2] + "(z - " + A[2] + ")";
		if(y.substring(0, 1).equals("-") && z.substring(0, 1).equals("-")) {
			equation = x + " " + y + " " + z;
		}
		else if(y.substring(0, 1).equals("-")) {
			equation = x + " " + y + " + " + z;
		}
		else if(z.substring(0, 1).equals("-")) {
			equation = x + " + " + y + " " + z;
		}
		else {
			equation = x + " + " + y + " + " + z;
		}
		return equation + " = 0";
	}
	
	public int[] normalVectorOfPlane(String equation) {
		int[] normal = new int[3];
		normal[0] = Integer.parseInt(equation.substring(0, equation.indexOf("x") - 1));
		equation = equation.substring(equation.indexOf(")") + 2);
		if(equation.substring(0, 1).equals("+")) equation = equation.substring(2);
		normal[1] = Integer.parseInt(equation.substring(0, equation.indexOf("y") - 1));
		equation = equation.substring(equation.indexOf(")") + 2);
		if(equation.substring(0, 1).equals("+")) equation = equation.substring(2);
		normal[2] = Integer.parseInt(equation.substring(0, equation.indexOf("z") - 1));
		System.out.println(Arrays.toString(normal));
		return normal;
	}
	
	public int[] pointOnPlane(String equation) {
		int[] points = new int[3];
		for(int i = 0; i < points.length; i++) {
			equation = equation.substring(equation.indexOf("(") + 3);
			if(equation.substring(0, 1).equals("-")) {
				points[i] = Integer.parseInt(equation.substring(2, equation.indexOf(")")));
			} else points[i] = Integer.parseInt("-" + equation.substring(2, equation.indexOf(")")));
		}
		System.out.println(Arrays.toString(points));
		return points;
	}
	
	public int[] directionVectorOfLine(String[] line) {
		int[] normal = new int[3];
		for(int i = 0; i < line.length; i++) {
			String newE = line[i].substring(0, 1).equals("-") ? line[i].substring(1) : line[i];
			String[] parts;
			if(newE.contains("-")) {
				parts = newE.split("-");
				parts[0] = line[i].substring(0, 1).equals("-") ? "-" + parts[0] : parts[0];
				parts[1] = "-" + parts[1];
			} else if(newE.contains("+")){
				parts = new String[2];
				parts[0] = line[i].substring(0, line[i].indexOf("+"));
				parts[1] = line[i].substring(line[i].indexOf("+") + 1);
			} else {
				parts = new String[1];
				parts[0] = line[i];
			}
			for(int j = 0; j < parts.length; j++) {
				//System.out.println(parts[j]);
				parts[j] = parts[j].trim();
				if(parts[j].equals("t")) {
					normal[i] = 1;
					break;
				}
				else if(parts[j].equals("-t")) {
					normal[i] = -1;
					break;
				}
				else if(parts[j].equals("- t")) {
					normal[i] = -1;
					break;
				}
				else if(parts[j] != null && parts[j].contains("t")) {
					parts[j] = parts[j].length() > 2 && parts[j].charAt(0) == '-' && parts[j].substring(1, 2).equals(" ")
							? parts[j].replace(' ', '-').substring(1) : parts[j];
					normal[i] = Integer.parseInt(parts[j].substring(0, parts[j].indexOf("t")));
					break;
				}
			}
		}
		System.out.println(Arrays.toString(normal));
		return normal;
	}
	
	public int[] pointOnLine(String[] line) {
		int[] points = new int[3];
		for(int i = 0; i < line.length; i++) {
			String newE = line[i].substring(0, 1).equals("-") ? line[i].substring(1) : line[i];
			String[] parts;
			if(newE.contains("-")) {
				parts = newE.split("-");
				parts[0] = line[i].substring(0, 1).equals("-") ? "-" + parts[0] : parts[0];
				parts[1] = "-" + parts[1];
			} else if(newE.contains("+")){
				parts = new String[2];
				parts[0] = line[i].substring(0, line[i].indexOf("+"));
				parts[1] = line[i].substring(line[i].indexOf("+") + 1);
			} else {
				parts = new String[1];
				parts[0] = line[i];
			}
			for(int j = 0; j < parts.length; j++) {
				//System.out.println(parts[j]);
				parts[j] = parts[j].trim();
				if(parts[j] != null && !parts[j].contains("t")) {
					parts[j] = parts[j].length() > 2 && parts[j].charAt(0) == '-' && parts[j].substring(1, 2).equals(" ")
							? parts[j].replace(' ', '-').substring(1) : parts[j];
					points[i] = Integer.parseInt(parts[j]);
					break;
				}
			}
		}
		System.out.println(Arrays.toString(points));
		return points;
	}
	
	public String fixEquation(String e) {
		String equation = "";
		String newE = e.substring(0, 1).equals("-") ? e.substring(1) : e;
		String[] parts;
		if(newE.contains("-")) {
			parts = newE.split("-");
			parts[0] = e.substring(0, 1).equals("-") ? "-" + parts[0] : parts[0];
			parts[1] = "-" + parts[1];
		} else if(newE.contains("+")){
			parts = new String[2];
			parts[0] = e.substring(0, e.indexOf("+"));
			parts[1] = e.substring(e.indexOf("+") + 1);
		} else parts = new String[2];
		//part 1 involves t
		parts[0] = parts[0].trim();
		int num1 = Integer.parseInt(parts[0].substring(0, parts[0].indexOf("t")));
		if(num1 == 0) {
			parts[0] = "";
		} else if(num1 == 1) {
			parts[0] = "t";
		} else if(num1 == -1) {
			parts[0] = "-t";
		}
		parts[1] = parts[1].trim();
		int num2 = Integer.parseInt(parts[1]);
		if(num2 == 0) {
			parts[1] = "";
		}
		if(parts[0].equals("") && parts[1].equals("")) {
			equation = "0";
		}
		else if(parts[0].equals("")) {
			equation = parts[1];
		}
		else if(parts[1].equals("")) {
			equation = parts[0];
		}
		else if(!parts[0].contains("-") && !parts[1].contains("-")) {
			equation = parts[0] + " + " + parts[1]; 
		}
		else if(parts[0].contains("-") && parts[1].contains("-")) {
			equation = parts[0] + " - " + parts[1].substring(1);
		}
		else if(parts[0].contains("-")) {
			equation = parts[1] + " - " + parts[0].substring(1);
		}
		else if(parts[1].contains("-")) {
			equation = parts[0] + " - " + parts[1].substring(1);
		}
		return equation;
	}
	
	public String print(int[] vector) {
		return "<" + vector[0] + ", " + vector[1] + ", " + vector[2] + ">";
	}
	
	public String print(String[] vector) {
		return "r(t) = " + "<" + vector[0] + ", " + vector[1] + ", " + vector[2] + ">";
	}
	
}
