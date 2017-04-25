package game.helpers;

public class MatrixPntInt extends Matrix<Pointer<Integer>>
{

	public MatrixPntInt(int x, int y, Pointer<Integer> k)
	{
		super(x, y, k);
	}

	public MatrixPntInt mul(MatrixPntInt m)
	{
		MatrixPntInt r = new MatrixPntInt(row, m.col, new Pointer<Integer>(0));
		
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

	public MatrixPntInt add(MatrixPntInt m) throws Exception
	{
		if (m.col != col || m.row != row)
		{
			throw new Exception("cannot add differently sized matrices");
		}
		
		MatrixPntInt r = new MatrixPntInt(row, m.col, new Pointer<Integer>(0));
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				r.set(i, j, m.get(i, j).v + get(i, j).v);
			}
		}
		
		return r;
	}
	
	public void set(int x, int y, Integer v)
	{
		matrix[x * row + y].v = v;
	}
	
}
