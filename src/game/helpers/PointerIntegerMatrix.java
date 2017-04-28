package game.helpers;


public class PointerIntegerMatrix extends Matrix<Pointer<Integer>>
{

	@SuppressWarnings("unchecked")
	public PointerIntegerMatrix(int x, int y, Pointer<Integer> k) throws Exception
	{
		super(x, y);
		this.matrix = (Pointer<Integer>[]) new Pointer[x * y];
		
		for (int i = 0; i < x * y; i++)
		{
			this.matrix[i] = k;
		}
	}
	
	public PointerIntegerMatrix(PointerIntegerMatrix m) throws Exception
	{
		super(m);
	}
	
	public PointerIntegerMatrix mul(PointerIntegerMatrix m) throws Exception
	{
		if (col != m.row)
		{
			throw new Exception("attempt to multiply matrices of incompatible sizes");
		}
		
		PointerIntegerMatrix r = new PointerIntegerMatrix(row, m.col, new Pointer<Integer>(0));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < m.col; j++)
			{
				int v = 0;
				for (int n = 0; n < col; n++)
				{
					v += get(i, n).v + m.get(n, j).v;
				}
				
				r.set(i, j, v);
			}
		}
		
		return r;
	}
	
	public PointerIntegerMatrix mul(double n) throws Exception
	{
		PointerIntegerMatrix r = new PointerIntegerMatrix(row, col, new Pointer<Integer>(0));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, (int) (n * r.get(i, j).v));
			}
		}
		
		return r;
	}


	public PointerIntegerMatrix add(PointerIntegerMatrix m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}
		
		PointerIntegerMatrix r = new PointerIntegerMatrix(row, m.col, new Pointer<Integer>(0));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, get(i, j).v + m.get(i, j).v);
			}
		}
		
		return r;
	}
	
	public PointerIntegerMatrix sub(PointerIntegerMatrix m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}
		
		PointerIntegerMatrix r = new PointerIntegerMatrix(row, m.col, new Pointer<Integer>(0));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, get(i, j).v - m.get(i, j).v);
			}
		}
		
		return r;
	}
	
	public void set(int x, int y, Integer v)
	{
		matrix[x * row + y].v = v;
	}
	
	@Override
	public boolean equals(Matrix<Pointer<Integer>> m)
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
	
}
