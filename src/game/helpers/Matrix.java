package game.helpers;

import java.lang.reflect.Array;

public class Matrix<T>
{
	final int row, col;
	protected T[] matrix;

	@SuppressWarnings("unchecked")
	public Matrix(int x, int y, T k)
	{
		this.row = x;
		this.col = y;
		this.matrix = (T[]) Array.newInstance(k.getClass(), x * y);
		
		for (int i = 0; i < x * y; i++)
		{
			this.matrix[i] = k;
		}
	}

	public T get(int x, int y)
	{
		return matrix[x * row + y];
	}
	
	public void set(int x, int y, T v)
	{
		matrix[x * row + y] = v;
	}

	
}
