package com.silber.helpers;

import com.silber.helpers.PointerDoubleMatrix;
import junit.framework.TestCase;

public class TestPointerDoubleMatrix extends TestCase
{
	protected PointerDoubleMatrix k;

	protected PointerDoubleMatrix m, n;
	final int mdimx = 5, mdimy = 8;
	final int ndimx = mdimy, ndimy = 6;

	protected PointerDoubleMatrix x, y;
	final int xdimx = 1, xdimy = 6;
	final int ydimx = xdimy, ydimy = xdimx;
	
	final String mStr = ""
			+ "1 2 3.0 2   4 3 2 2\n"
			+ "2 2 2.0 5   2 3 4 2\n"
			+ "3 5 2.0 1   1 2 1 1\n"
			+ "2 1 3   4.0 3 2 1 2\n"
			+ "2 3 4   3   3 2 2 3";
	
	final String nStr = ""
			+ "1.0 1 1 2 1 1\n"
			+ "2   3 1 2 2 1\n"
			+ "3   3 1 2 3 1\n"
			+ "1.0 3 1 3 2 2\n"
			+ "2.0 2 1 2 2 2\n"
			+ "3   1 1 1 1 3\n"
			+ "2   1 2 2 3 1\n"
			+ "3.0 2 1 1 2 3";
	
	final String xStr = "1 2 3 4 5 6";
	final String yStr = "2\n4\n2\n1\n3\n7";
	
	public void setUp() throws Exception
	{
		m = new PointerDoubleMatrix(mdimx, mdimy);
		n = new PointerDoubleMatrix(ndimx, ndimy);
		k = new PointerDoubleMatrix(mdimx, mdimy);
		x = new PointerDoubleMatrix(xdimx, xdimy);
		y = new PointerDoubleMatrix(ydimx, ydimy);

		k.set(mStr);
		n.set(nStr);
		x.set(xStr);
		y.set(yStr);

		int a = 0, b = 0;

		m.set(a, b++, 1d);
		m.set(a, b++, 2d);
		m.set(a, b++, 3d);
		m.set(a, b++, 2d);
		m.set(a, b++, 4d);
		m.set(a, b++, 3d);
		m.set(a, b++, 2d);
		m.set(a, b++, 2d);
		b %= mdimy;
		a++;

		m.set(a, b++, 2d);
		m.set(a, b++, 2d);
		m.set(a, b++, 2d);
		m.set(a, b++, 5d);
		m.set(a, b++, 2d);
		m.set(a, b++, 3d);
		m.set(a, b++, 4d);
		m.set(a, b++, 2d);
		b %= mdimy;
		a++;

		m.set(a, b++, 3d);
		m.set(a, b++, 5d);
		m.set(a, b++, 2d);
		m.set(a, b++, 1d);
		m.set(a, b++, 1d);
		m.set(a, b++, 2d);
		m.set(a, b++, 1d);
		m.set(a, b++, 1d);
		b %= mdimy;
		a++;

		m.set(a, b++, 2d);
		m.set(a, b++, 1d);
		m.set(a, b++, 3d);
		m.set(a, b++, 4d);
		m.set(a, b++, 3d);
		m.set(a, b++, 2d);
		m.set(a, b++, 1d);
		m.set(a, b++, 2d);
		b %= mdimy;
		a++;

		m.set(a, b++, 2d);
		m.set(a, b++, 3d);
		m.set(a, b++, 4d);
		m.set(a, b++, 3d);
		m.set(a, b++, 3d);
		m.set(a, b++, 2d);
		m.set(a, b++, 2d);
		m.set(a, b++, 3d);
		b %= mdimy;
		a++;

	}
	
	/**
	 * tests that setting the matrix by string yields the same results as manual input
	 */
	public void testSetString()
	{
		System.out.println("testSetString");
		
		System.out.println(m);
		System.out.println(k);
		
		assertTrue(m.equals(k));
	}

	/**
	 * tests the matrix multiplication
	 * @throws Exception
	 */
	public void testMul() throws Exception
	{
		System.out.println("testMul");

		final String expStr = ""
				+ "43  39  21  35  39  35\n"
				+ "44  44  26  44  45  39\n"
				+ "33  34  17  30  30  24\n"
				+ "37  39  19  36  36  33\n"
				+ "48  48  24  42  46  38\n";
		

		PointerDoubleMatrix result = m.mul(n);
		PointerDoubleMatrix expected = new PointerDoubleMatrix(mdimx, ndimy);
		expected.set(expStr);
		
		System.out.println(expected);
		System.out.println(result);
		
		assertTrue(expected.equals(result));
	}
	
	public void testDot() throws Exception
	{
		System.out.println("testDot");
		
		double result = x.dot(y);
		final double d = 77d;
		
		System.out.println(d);
		System.out.println(result);
		
		assertTrue(result == d);
	}
	
	public void testAdd() throws Exception
	{
		System.out.println("testAdd");
		
		final String addStr = ""
				+ "1 1 0 0 0 0 1 0\n"
				+ "0 1 1 1 1 1 0 0\n"
				+ "1 0 0 0 1 3 3 1\n"
				+ "9 1 2 1 1 1 1 2\n"
				+ "0 0 0 0 0 0 0 0";
		final String expStr = ""
				+ "2  3 3 2 4 3 3 2\n"
				+ "2  3 3 6 3 4 4 2\n"
				+ "4  5 2 1 2 5 4 2\n"
				+ "11 2 5 5 4 3 2 4\n"
				+ "2  3 4 3 3 2 2 3";
		
		PointerDoubleMatrix add = new PointerDoubleMatrix(mdimx, mdimy);
		PointerDoubleMatrix expected = new PointerDoubleMatrix(mdimx, mdimy);
		add.set(addStr);
		expected.set(expStr);
		
		PointerDoubleMatrix result = m.add(add);
		
		System.out.println(expected);
		System.out.println(result);
		
		assertTrue(expected.equals(result));
	}

}
