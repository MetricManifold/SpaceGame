package com.silber.helpers;

public interface AccumulateValue<T extends Number>
{
	public void sub(T sub);
	public void add(T add);
	public void mul(Double mul);
	public void div(Double div);
	public T get();
}
