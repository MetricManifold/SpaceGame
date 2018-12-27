package game.helpers;

public class AccumulateInteger implements AccumulateValue<Integer>
{
	int acc = 0;
	
	@Override
	public void sub(Integer sub)
	{
		acc -= sub;
	}

	@Override
	public void add(Integer add)
	{
		acc += add;
	}

	@Override
	public Integer get()
	{
		return acc;
	}

	@Override
	public void mul(Double mul)
	{
		acc *= mul;
	}

	@Override
	public void div(Double div)
	{
		acc /= div;
	}
}
