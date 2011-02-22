package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SponsoredLinkSet
{
	public static final String RESOURCE_TYPE = "SPONSOREDLINKS_SET_TYPE";
	

	private int _nId;
	private String _strTitle;
	private List<SponsoredLink> _sponsoredLinkSet;
	private int _nGroupId;
	
	public SponsoredLinkSet(  )
	{
		_sponsoredLinkSet = new ArrayList<SponsoredLink>(  );
	}
	
	
	/**
	 * Appends a list of SponsoredLink in the set
	 * @param linkList The list of link
	 * @return true if this set changed as a result of the call
	 */
	public boolean addAllLink( Collection<SponsoredLink> linkList )
	{
		boolean bResult = false; 
		if ( _sponsoredLinkSet.addAll( linkList ) )
		{
			Collections.sort(_sponsoredLinkSet);
			bResult = true;
		}
		
		return bResult;
	}
		
	public int getId(  )
	{
		return _nId;
	}
	
	public void setId( int id )
	{
		_nId = id;
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

	public int getGroupId(  )
	{
		return _nGroupId;
	}

	public void setGroupId( int groupId )
	{
		this._nGroupId = groupId;
	}

}