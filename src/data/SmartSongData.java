package data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import util.StringUtil;
import bean.SmartSong;
import bean.Song;
import data.common.ISmartSong;

public class SmartSongData extends BasicHibernateDataSource implements ISmartSong {
	
	public List<SmartSong> getAllSmartSongs() 
	{
		return super.queryDatabaseForList(SmartSong.class, "from SmartSong"); 		
	}
	
	public List<SmartSong> getAllSmartSongs( SmartSong searchBean ) 
	{
		List<SmartSong> smartSongs = null;
		Session session = DataHelper.getOpenSession(); 
		
		Example smartSongExample = Example.create(searchBean)
										  .excludeZeroes()
										  .excludeProperty("album1")
										  .excludeProperty("album2")
										  .excludeProperty("album3");
		
		Criteria crit = session.createCriteria(SmartSong.class);
		
		crit.add(smartSongExample);
		
		// if any of the album fields have values
		if( StringUtil.hasValue(searchBean.getAlbum1()) || StringUtil.hasValue(searchBean.getAlbum2()) || StringUtil.hasValue(searchBean.getAlbum3()) )
		{
			// Need to check if ANY of the album members have ANY other of the album fields
			crit.add ( 
				Restrictions.disjunction()
			    			.add(Restrictions.eq("album1", searchBean.getAlbum1()))
			    			.add(Restrictions.eq("album2", searchBean.getAlbum1()))
			    			.add(Restrictions.eq("album3", searchBean.getAlbum1()))
			    			
			    			.add(Restrictions.eq("album1", searchBean.getAlbum2()))
			    			.add(Restrictions.eq("album2", searchBean.getAlbum2()))
			    			.add(Restrictions.eq("album3", searchBean.getAlbum2()))
			    			
			    			.add(Restrictions.eq("album1", searchBean.getAlbum3()))
			    			.add(Restrictions.eq("album2", searchBean.getAlbum3()))
			    			.add(Restrictions.eq("album3", searchBean.getAlbum3()))
			);
		}	
		
        smartSongs = (List<SmartSong>) crit.list();
		
		return smartSongs;
	}
	
	@Override 
	public void saveOrUpdateSmartSong(SmartSong smartSong)
	{		
		Song correspondingSong = super.queryDatabaseForItem(Song.class, "from Song where id=" + smartSong.getId()); 
		correspondingSong.setDownvotes(smartSong.getDownvotes()); 
		correspondingSong.setUpvotes(smartSong.getUpvotes()); 
		
		super.saveOrUpdateItem(Song.class, correspondingSong); 
	}

	@Override
	public SmartSong getSmartSong(int id)
	{
		return super.queryDatabaseForItem(SmartSong.class, "from SmartSong where id=" + id); 
	}

}
