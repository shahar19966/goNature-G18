package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class ReportDate implements Serializable{
	
	
	
	private String month;
	private String year;
	private String namePark;
	
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
	public String getYear()
	{
		return year;
	}
	
	public void setYear(String year)
	{
		this.year=year;
	}

	public void setMonth(String month)
	{
		this.month=month;
	}
	
	public void setNamePark(String namePark)
	{
		this.namePark=namePark;
	}
	
	
	

}
