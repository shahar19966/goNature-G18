package entity;

import java.io.Serializable;
/*
 * ParkCapactiyReport is for a park capacity report that is sent between the client and server
 */
public class ParkCapacityReport implements Serializable { 
	
	private String date;
	private String time;
	
	public ParkCapacityReport(String date,String time)
	{
		this.date=date;
		this.time=time;
	}
	
	public String getDate()
	{
		return date;
	}
	public String getTime() {
		return time;
		
	}

}
