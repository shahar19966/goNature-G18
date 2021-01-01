package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class ReportDate implements Serializable{
	
	
	
	private String month;
	private String namePark;
	public ReportDate()
	{
		
	}
	public ReportDate(String namePark)
	{
		this.namePark=namePark;
	}
	public String getNamePark()
	{
	return namePark;
	}
	public String getMonth()
	{
		return month;
	}
	
	

	public void setEndDate(String month)
	{
		this.month=month;
	}
	
	public void setNamePark(String namePark)
	{
		this.namePark=namePark;
	}
	
	
	

}
