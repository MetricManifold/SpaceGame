package game.helpers;

public interface AccumulateValue<T extends Number>
{
	public void sub(T sub);
	public void add(T add);
	public T get();
}
