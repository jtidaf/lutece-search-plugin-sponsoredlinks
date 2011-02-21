package fr.paris.lutece.plugins.sponsoredlinks.business;

public  class SponsoredLinkGroup
{
	public static final String RESOURCE_TYPE = "SPONSOREDLINKS_TOPIC_TYPE";
	
	private int _nId;
	private String _strTitle;
	private String _strTags;
	
	
	public SponsoredLinkGroup(  )
	{
		
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

	public String getTags(  )
	{
		return _strTags;
	}

	public void setTags( String tags )
	{
		this._strTags = tags;
	}
}
