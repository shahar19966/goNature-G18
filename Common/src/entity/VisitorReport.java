package entity;

import java.io.Serializable;

public class VisitorReport implements Serializable {

	private String namePark;
	private int countSubscriber,countGuid,countRegular;

	
	public VisitorReport(String namePark)
	{
		this.namePark=namePark;
		this.countSubscriber=0;
		this.countGuid=0;
		this.countRegular=0;
	
	}

	public String getNamePark()
	{
		return namePark;
	}
	public int getCountGuid()
	{
		return countGuid;
	}
	public void setCountGuid(int countGuid)
	{
		this.countGuid=countGuid;
	}
	public int getCountRegular()
	{
		return countRegular;
	}
	public void setCountRegular(int CountRegular)
	{
		this.countRegular=CountRegular;
	}
	public int getCountSubscriber()
	{
		return countSubscriber;
	}
	public void setCountSubscriber(int countSubscriber)
	{
		this.countSubscriber=countSubscriber;
	}
	@Override
	public String toString() {
		return namePark;
	}
	

}
