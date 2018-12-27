package game.helpers;

public class PointerDoubleMatrix extends Matrix<Pointer<Double>>
{

	@SuppressWarnings("unchecked")
	public PointerDoubleMatrix(int x, int y, Pointer<Double> k) throws Exception
	{
		super(x, y);
		this.matrix = (Pointer<Double>[]) new Pointer[x * y];

		for (int i = 0; i < x * y; i++)
		{
			this.matrix[i] = new Pointer<Double>(k);
		}
	}

	public PointerDoubleMatrix(int x, int y, Double k) throws Exception
	{
		this(x, y, new Pointer<Double>(k));
	}

	public PointerDoubleMatrix(int x, int y) throws Exception
	{
		this(x, y, new Pointer<Double>(0d));
	}

	public PointerDoubleMatrix(PointerDoubleMatrix m) throws Exception
	{
		super(m);
	}

	/**
	 * multiplies two matrices together
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public PointerDoubleMatrix mul(PointerDoubleMatrix m) throws Exception
	{
		if (col != m.row)
		{
			throw new Exception("attempt to multiply matrices of incompatible sizes");
		}

		PointerDoubleMatrix r = new PointerDoubleMatrix(row, m.col, new Pointer<Double>(0d));

		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < m.col; j++)
			{
				double v = 0d;
				for (int n = 0; n < col; n++)
				{
					v += get(i, n).v * m.get(n, j).v;
				}

				r.set(i, j, v);
			}
		}

		return r;
	}

	/**
	 * multiplies each element of the matrix with given parameter
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public PointerDoubleMatrix mul(double n) throws Exception
	{
		PointerDoubleMatrix r = new PointerDoubleMatrix(row, col, new Pointer<Double>(0d));

		for (int i = 0; i < row * col; i++)
		{
			r.matrix[i].v = matrix[i].v * n;
		}

		return r;
	}

	/**
	 * adds two matrices element by element
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public PointerDoubleMatrix add(PointerDoubleMatrix m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}

		PointerDoubleMatrix r = new PointerDoubleMatrix(row, col, new Pointer<Double>(0d));

		for (int i = 0; i < row; i++)
		{
			r.matrix[i].v = matrix[i].v + m.matrix[i].v;
		}

		return r;
	}

	/**
	 * subtracts two matrices element by element
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public PointerDoubleMatrix sub(PointerDoubleMatrix m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}

		PointerDoubleMatrix r = new PointerDoubleMatrix(row, m.col, new Pointer<Double>(0d));

		for (int i = 0; i < row; i++)
		{
			r.matrix[i].v = matrix[i].v - m.matrix[i].v;
		}

		return r;
	}

	/**
	 * sets an element in this matrix
	 * 
	 * @param x
	 * @param y
	 * @param v
	 */
	public void set(int x, int y, Double v)
	{
		matrix[x * row + y].v = v;
	}

	/**
	 * sets an element in this matrix
	 * 
	 * @param x
	 * @param y
	 * @param v
	 */
	public void set(int x, int y, Integer v)
	{
		set(x, y, (double) v);
	}

	@Override
	public boolean equals(Matrix<Pointer<Double>> m)
	{
		if (m.row != row || m.col != col) return false;

		boolean r = true;
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				if (!get(i, j).v.equals(m.get(i, j).v)) r = false;
			}
		}

		return r;
	}

}
