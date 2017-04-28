package game.helpers;

import java.lang.reflect.Array;

public class Matrix<T>
{
	final int row, col;
	protected T[] matrix;

	@SuppressWarnings("unchecked")
	public Matrix(int x, int y, T k) throws Exception
	{
		this(x, y);
		this.matrix = (T[]) Array.newInstance(k.getClass(), x * y);

		for (int i = 0; i < x * y; i++)
		{
			this.matrix[i] = k;
		}
	}

	protected Matrix(int x, int y) throws Exception
	{
		if (x <= 0 || y <= 0)
		{
			throw new Exception("matrix is of incorrect size");
		}
		
		this.row = x;
		this.col = y;
	}
	
	@SuppressWarnings("unchecked")
	public Matrix(Matrix<T> m) throws Exception
	{
		this(m.row, m.col);
		
		Class<?> t = m.get(0, 0).getClass();
		this.matrix = (T[]) Array.newInstance(t, row * col);
		
		
		for (int i = 0; i < row * col; i++)
		{
			matrix[i] = (T) t.getConstructor(t).newInstance(m.matrix[i]);
		}
		
	}


	/**
	 * return the value in the x-th row and y-th column
	 * @param x
	 * @param y
	 * @return
	 */
	public T get(int x, int y)
	{
		return matrix[x * row + y];
	}

	/**
	 * set the value in the x-th row and y-th column
	 * @param x
	 * @param y
	 * @param v
	 */
	public void set(int x, int y, T v)
	{
		matrix[x * row + y] = v;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;

		@SuppressWarnings("unchecked")
		Matrix<T> m = (Matrix<T>) o;
		return equals(m);
	}

	/**
	 * check if this matrix's values equal to another
	 * @param m
	 * @return
	 */
	public boolean equals(Matrix<T> m)
	{

		if (m.row != row || m.col != col) return false;

		boolean r = true;
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				if (m.get(i, j) != get(i, j)) r = false;
			}
		}

		return r;
	}
}
