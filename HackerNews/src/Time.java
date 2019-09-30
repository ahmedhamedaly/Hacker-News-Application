
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import processing.core.*;
//Converts given time and creates an object of it, then returns whichever unit of time is requested.																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				`
public class Time extends PApplet{
	private int seconds;
	private int minutes;
	private int hours;
	private int days;
	private int month;
	private int year;
	private int weeks;
	private long timeInSeconds;
	public Time(long timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
		Date date = new Date(timeInSeconds);
		 days = (int) ((timeInSeconds % 604800) / 86400);
		 hours = (int) (((timeInSeconds % 604800) % 86400) / 3600);
		 minutes = (int) ((((timeInSeconds % 604800) % 86400) % 3600) / 60);
		 seconds = (int) ((((timeInSeconds % 604800) % 86400) % 3600) % 60);
		 LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 month = localDate.getMonthValue();
		 year = localDate.getYear();
		 weeks = 604800;
	}
	public  String getTimeString() {
		Date date = new Date (timeInSeconds*1000L); 
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1")); 
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	public Boolean isCurrentWeek() {
		if ((timeInSeconds-System.currentTimeMillis())<weeks)
			return true;
		else return false;
	}

	public  int getSeconds() {
		return seconds;
	}
	public  int getMinutes() {
		return minutes;
	}
	public  int getHours() {
		return hours;
	}
	public  int getDays() {
		return days;
	}
	public  int getMonthInt () {
		return month;
	}
	public  String getMonthString () {
		switch (month) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			return null;
		}
			
	}
	public  int getYear () {
		return year;
	}
	public  Boolean isLeapYear() {
		if (year % 4 != 0) {
		    return false;
		  } else if (year % 400 == 0) {
		    return true;
		  } else if (year % 100 == 0) {
		    return false;
		  } else {
		    return true;
		  }
	}
}