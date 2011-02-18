package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SponsoredLinkSet
{
	public static final String RESOURCE_TYPE = "SPONSOREDLINKS_SET_TYPE";
	
	private static int _nGlobalId = 0;
		
	private int _nId;
	private String _strTitle;
	private List<SponsoredLink> _sponsoredLinkSet;
	private int _nTopicId;
	
	public SponsoredLinkSet( String title, int topicId )
	{
		this._nId = ++_nGlobalId;
		this._strTitle = title;
		this._nTopicId = topicId;
		this._sponsoredLinkSet = new ArrayList<SponsoredLink>(  );
		
	}
	
	
	public void addSponsoredLink( String url )
	{
		SponsoredLink sponsoredLink = new SponsoredLink( _sponsoredLinkSet.size(), url );
		_sponsoredLinkSet.add( sponsoredLink );
	}
	
	public void removeSponsoredLink( int index )
	{
		this._sponsoredLinkSet.remove( index );
	}
	
	
	public int getId(  )
	{
		return _nId;
	}

	
	public String getTitle(  )
	{
		return _strTitle;
	}

	public void setTitle( String title )
	{
		this._strTitle = title;
	}

	public Collection<SponsoredLink> getSponsoredLinkSet(  )
	{
		return _sponsoredLinkSet;
	}

	public void setSponsoredLinkSet( Collection<SponsoredLink> sponsoredLinkSet )
	{
		this._sponsoredLinkSet = (List<SponsoredLink>)sponsoredLinkSet;
	}

	public int getTopicId(  )
	{
		return _nTopicId;
	}

	public void setTopicId( int topicId )
	{
		this._nTopicId = topicId;
	}

}