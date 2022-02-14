package assignments2019.a2;
import java.math.BigInteger;
import java.util.ArrayList;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

public class TesterPosted {

	public static void gradeSchoolTester(int base, String s1, String s2) throws Exception {
		/*
		 *  We can test the correctness of the arithmetic operations in MyBigInteger class
		 *  by using Java's BigInteger class.
		 */

		BigInteger big1 = new BigInteger(s1, base);
		System.out.println("big1: " + "(" + big1.toString(base) + ")_"+ base + "     (BigInteger)");
		BigInteger big2 = new BigInteger(s2, base);
		System.out.println("big2: " + "(" + big2.toString(base) + ")_"+ base + "      (BigInteger)");
		System.out.println();

		MyBigInteger n1 = new MyBigInteger(s1, base);
		System.out.println("n1: " + n1 + "     (MyBigInteger)");
		MyBigInteger n2 = new MyBigInteger(s2, base);
		System.out.println("n2: " + n2 + "      (MyBigInteger)");
		System.out.println();

		String quo,rem;
		String quo_mybig, rem_mybig;

		// divide
		quo = big1.divide(big2).toString(base);
		quo = "(" +quo+ ")_" + base;
		System.out.print("divide: big1/big2     = ");              // BigInteger
		System.out.println(quo);

		long s_time = System.currentTimeMillis();
		System.out.print("divide: n1/n2         = ");                 // MyBigInteger
		try {
			quo_mybig = n1.dividedBy(n2).toString();
			System.out.println(quo_mybig);
			if (quo.contentEquals(quo_mybig))
				System.out.println("Test passed for dividedBy().");
			else
				System.out.println("Test failed for dividedBy().");
		} catch (Exception e) {
			System.out.println();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			System.out.println(s.toString());
		}
		System.out.println("Time Required (Divide): "+(System.currentTimeMillis()-s_time)+" ms\n");

		// mod
		System.out.print("big1 mod big2 = ");
		rem = big1.mod(big2).toString(base);
		rem = "(" + rem + ")_" + base;
		System.out.println(rem);
		System.out.print("n1 mod n2 = ");
		try {
			rem_mybig = n1.mod(n2).toString();
			System.out.println(rem_mybig);
			if (rem.contentEquals(rem_mybig))
				System.out.println("Test passed for mod().");
			else
				System.out.println("Test failed for mod().");

		} catch (Exception e) {
			System.out.println();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			System.out.println(s.toString());
		}
	}


	public static void convertTester(MyBigInteger number, int newBase, MyBigInteger expected) {
		System.out.println("Convert " + number.toString() + " to base " + newBase);
		System.out.println("Expected: " + expected.toString());
		try {
			long s_time = System.currentTimeMillis();
			MyBigInteger obtained = number.convert(newBase);
			long e_time = System.currentTimeMillis();

			System.out.println("Returned: " + obtained.toString());
			if (expected.getBase() == obtained.getBase() && expected.getCoefficients().equals(obtained.getCoefficients()))
				System.out.print("Correct");
			else
				System.out.print("Incorrect");
			System.out.println(", Time Required: " + (e_time-s_time) + " ms");
		} catch (Exception e) {
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			System.out.println(s.toString());
		}
	}
	public static void factorizationTester(MyBigInteger n1,  ArrayList<MyBigInteger> answer_expected) throws Exception{

		try{
			long s_time = System.currentTimeMillis();
			String ans_obtained;
			ans_obtained = Arrays.toString(n1.primeFactors().toArray());
			System.out.println("Testing primeFactors() method on : "+ n1.toString() + "\n");
			System.out.println("Expected answer : " +  Arrays.toString(answer_expected.toArray()));
			System.out.println("Answer obtained  : " + ans_obtained);
			System.out.println("Time Required (Prime Factorize): "+(System.currentTimeMillis()-s_time)+" ms");
			if (Arrays.toString(answer_expected.toArray()).contentEquals(ans_obtained))
				System.out.println("Test passed.\n");
			else
				System.out.println("Test failed.\n");


		}catch (Exception e){
			System.out.println();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			System.out.println(s.toString());
			System.out.println("Test failed.\n");
		}
		finally {
			System.out.println("#######################################################\n");
		}

	}
	public static void main(String[] args) throws Exception {
						
		/* *****************************************************
		 *Testing for dividedBy()
		 *  We can test the correctness of the dividedBy() method in MyBigInteger class 
		 *  by comapring the output to that of Java's BigInteger class.
		 *******************************************************
		 */
		/*
		 * Grade school algorithms - base 10
		 */
		gradeSchoolTester(10, "3956", "27");
		System.out.println("\n############################################\n");


		/*
		 * Grade school algorithms - base 7
		 * The majority of the marks for divide will be based on integers
		 * of this scale. (Note: The grader will timeout after 10000 ms
		 * of computation)
		 */
		gradeSchoolTester(7, "624261025332633", "3245");
		System.out.println("\n############################################\n");


		/*
		 * Grade school algorithms - base 4
		 * The few remaining marks for divide will be based on numbers
		 * of this scale. (Note: The grader will timeout after 10000 ms
		 * of computation)
		 */
		gradeSchoolTester(4, "123203120302301230120321030123012301230123"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301"
						+ "12332131230123012031203120301203120301203012030120301",
				"1231321021023012301203120301203012301203012301200202010");
		System.out.println("\n############################################\n");

		MyBigInteger n1;
		String s1;
		int base;


		//*********************************************************
		//   Here is a test for converting from one base to another.
		//**********************************************************

		/*
		 * Convert from base 10
		 */

		MyBigInteger number, expected;
		int newBase;


		/*
		 * Convert from base 10
		 */
		System.out.println("Testing convert method");
		number = new MyBigInteger("513", 10);
		expected = new MyBigInteger("1000000001", newBase = 2);
		convertTester(number, newBase, expected);
		System.out.println("\n############################################\n");


		/*
		 * Convert from base 5
		 * The majority of the marks for convert will be based on integers
		 * of this scale. (Note: The grader will timeout after 10000 ms
		 * of computation)
		 */
		System.out.println("Testing convert method");
		number = new MyBigInteger("412130", 5);
		expected = new MyBigInteger("200101212", newBase = 3);
		convertTester(number, newBase, expected);
		System.out.println("\n############################################\n");

		System.out.println("Testing convert method");
		number = new MyBigInteger("412130", 5);
		expected = new MyBigInteger("54053", newBase = 7);
		convertTester(number, newBase, expected);
		System.out.println("\n############################################\n");


		/*
		 * Convert from base 5
		 * The few remaining marks for convert will be based on integers
		 * of this scale. (Note: The grader will timeout after 10000 ms
		 * of computation)
		 */
		System.out.println("Testing convert method");
		number = new MyBigInteger("11234123401234012301423031243130420212343"
				+ "440230412341203341201341203", 5);
		expected = new MyBigInteger("111221221121111210001021100202100102201"
				+ "210100020201212101012200212020202121222022120122221002112112", newBase = 3);
		convertTester(number, newBase, expected);
		System.out.println("\n############################################\n");

		System.out.println("Testing convert method");
		number = new MyBigInteger("11234123401234012301423031243130420212343"
				+ "440230412341203341201341203", 5);
		expected = new MyBigInteger("264000010022003160046322104222120221566"
				+ "31626524400530340", newBase = 7);
		convertTester(number, newBase, expected);
		System.out.println("\n############################################\n");

		//*****************************************************************
		//   Testing for primeFactors()
		//*****************************************************************
		MyBigInteger m;

		//test case 1
		base = 10;
		ArrayList expected_factors;

		m = new MyBigInteger("5000000000", base);
		expected_factors = new ArrayList();
		for (int i = 0 ; i < 9 ; i++)
			Collections.addAll(expected_factors, new MyBigInteger("2",10));

		for (int i = 0 ; i < 10 ; i++)
			Collections.addAll(expected_factors, new MyBigInteger("5",10));

		factorizationTester(m, expected_factors);

		//test case 2

		base = 5;
		m = new MyBigInteger( "13143241", base);
		expected_factors = new ArrayList();

		Collections.addAll(expected_factors, new MyBigInteger("13143241", base));

		factorizationTester(m, expected_factors);

		///test 3

		base = 6;
		m = new MyBigInteger("1114510444205521", base);
		expected_factors = new ArrayList();

		Collections.addAll(expected_factors, new MyBigInteger("5", base));

		Collections.addAll(expected_factors, new MyBigInteger("11", base));
		Collections.addAll(expected_factors, new MyBigInteger("11", base));
		Collections.addAll(expected_factors, new MyBigInteger("11", base));
		Collections.addAll(expected_factors, new MyBigInteger("11", base));

		Collections.addAll(expected_factors, new MyBigInteger("15", base));

		Collections.addAll(expected_factors, new MyBigInteger("35", base));
		Collections.addAll(expected_factors, new MyBigInteger("35", base));

		Collections.addAll(expected_factors, new MyBigInteger("101531", base));
		factorizationTester(m, expected_factors);

	}
}
