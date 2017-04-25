package game.helpers;

import java.lang.reflect.Array;

public class Matrix<T>
{
	final int row, col;
	protected T[] matrix;

	@SuppressWarnings("unchecked")
	public Matrix(int x, int y, T k)
	{
		this(x, y);
		this.matrix = (T[]) Array.newInstance(k.getClass(), x * y);

		for (int i = 0; i < x * y; i++)
		{
			this.matrix[i] = k;
		}
	}

	protected Matrix(int x, int y)
	{
		this.row = x;
		this.col = y;
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
