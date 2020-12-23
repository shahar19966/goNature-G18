package entity;

import java.io.Serializable;

public class VisitorReport implements Serializable {

	private String namePark;
	private int countSubscriber=0,countGuid=0,countRegular=0;

	
	public VisitorReport(String namePark,int countSubscriber, int countGuid,int countRegular)
	{
		this.namePark=namePark;
		this.countGuid=countGuid;
		this.countRegular=countRegular;
		this.countSubscriber=countSubscriber;
	}

	public String getNamePark()
	{
		return namePark;
	}
	public int getCountGuid()
	{
		return countGuid;
	}
	public int getCountRegular()
	{
		return countRegular;
	}
	public int getCountSubscriber()
	{
		return countSubscriber;
	}
	@Override
	public String toString() {
		return namePark;
	}
	

}
