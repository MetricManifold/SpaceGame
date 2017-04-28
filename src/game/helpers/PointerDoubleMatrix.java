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
			this.matrix[i] = k;
		}
	}
	
	public PointerDoubleMatrix(PointerDoubleMatrix m) throws Exception
	{
		super(m);
	}
	
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
					v += get(i, n).v + m.get(n, j).v;
				}
				
				r.set(i, j, v);
			}
		}
		
		return r;
	}
	
	public PointerDoubleMatrix mul(double n) throws Exception
	{
		PointerDoubleMatrix r = new PointerDoubleMatrix(row, col, new Pointer<Double>(0d));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, n * r.get(i, j).v);
			}
		}
		
		return r;
	}


	public PointerDoubleMatrix add(PointerDoubleMatrix m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}
		
		PointerDoubleMatrix r = new PointerDoubleMatrix(row, m.col, new Pointer<Double>(0d));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, get(i, j).v + m.get(i, j).v);
			}
		}
		
		return r;
	}
	
	public PointerDoubleMatrix sub(PointerDoubleMatrix m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}
		
		PointerDoubleMatrix r = new PointerDoubleMatrix(row, m.col, new Pointer<Double>(0d));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, get(i, j).v - m.get(i, j).v);
			}
		}
		
		return r;
	}
	
	public void set(int x, int y, Double v)
	{
		matrix[x * row + y].v = v;
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
				if (get(i, j).v != m.get(i, j).v) r = false;
			}
		}

		return r;
	}

	public void set(int x, int y, Integer v)
	{
		set(x, y, (double) v);
	}
	
}
