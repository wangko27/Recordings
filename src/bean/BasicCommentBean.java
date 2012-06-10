package bean;

import java.util.Date;

/**
 * describes all of our comment beans: RecordingComments, SongComments, etc.
 * 
 *
 */
public abstract class BasicCommentBean implements Comparable<BasicCommentBean>
{
	private int id;
	private String username; 
	private String text; 
	private Date timestamp; 
	private int bob;
	private int voteRank; 
	
	public BasicCommentBean() {
	}
	
	protected BasicCommentBean(String userName, String text, Date time, int bob)
	{
		this.username = userName; 
		this.text = text; 
		this.timestamp = time; 
		this.bob = bob; 
	}
	
	public int getVoteRank()
	{
		return voteRank; 
	}
	
	public void setVoteRank(int voteRank)
	{
		this.voteRank = voteRank; 
	}
	
	public void changeVoteRank(int dv)
	{
		this.voteRank += dv; 
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date time) {
		this.timestamp = time;
	}
	public int getBob() {
		return bob;
	}
	public void setBob(int bob) {
		this.bob = bob;
	} 
	
	public int compareTo(BasicCommentBean bean)
	{
		if(getTimestamp().after(bean.getTimestamp()))
			return 1; 
		if(getTimestamp().before(bean.getTimestamp()))
			return -1; 
		return 0; 
	} 
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof BasicCommentBean))
			throw new ClassCastException(); 
		
		BasicCommentBean bean = (BasicCommentBean) obj; 
		
		return bean.getTimestamp().equals(this.getTimestamp()) && bean.getUsername().equals(this.getUsername()) && bean.getText().equals(this.getText()) && bean.getBob() == this.getBob();
	}
	
	public int hashCode()
	{
		return getTimestamp().hashCode() ^ getUsername().hashCode() ^ getText().hashCode(); 
	}
	
}
