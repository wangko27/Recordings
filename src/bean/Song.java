package bean;

import java.util.ArrayList;
import java.util.List;

public class Song extends SimpleBean implements Comparable<SimpleBean> 
{
	private String alias;
	private List<SongComment> comments;
	private int upvotes; 
	private int downvotes; 
	
	public Song() {
		super();
		
		comments = new ArrayList<SongComment>();
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public List<SongComment> getComments() {
		return comments;
	}
	public void setComments(List<SongComment> comments) {
		this.comments = comments;
	}

	public String toString()
	{
		return this.value; 
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}
	
	public int getVotes()
	{
		return upvotes - downvotes; 	
	}
	
	
}
