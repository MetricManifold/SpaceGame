package game.helpers;

public class Pointer<T>
{
	public T v;
	
	public Pointer(T v)
	{
		this.v = v;
	}

	public Pointer(Pointer<T> k)
	{
		this.v = k.v;
	}
}
