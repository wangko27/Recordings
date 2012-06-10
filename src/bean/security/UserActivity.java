package bean.security;

import java.util.Date;

public class UserActivity
{
	private int id; 
	//private UserActivityType activityType; 
	private String user; 
	private Date time; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*public UserActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(UserActivityType activityType) {
		this.activityType = activityType;
	}*/
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}	
	
}
