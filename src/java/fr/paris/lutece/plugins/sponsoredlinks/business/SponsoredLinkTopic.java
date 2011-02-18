package fr.paris.lutece.plugins.sponsoredlinks.business;

public  class SponsoredLinkTopic
{
	public static final String RESOURCE_TYPE = "SPONSOREDLINKS_TOPIC_TYPE";
	public static final SponsoredLinkTopic UNDEFINED_TOPIC = new SponsoredLinkTopic( 0, "", "" );
	
	private static int _nGlobalId = 0;
	private int _nId;
	private String _strTitle;
	private String _strTags;
	
	private SponsoredLinkTopic( int id, String title, String tags )
	{
		_nId = id;
		_strTitle = title;
		_strTags = tags;
	}
	
	public SponsoredLinkTopic( String title, String tags )
	{
		_nId = ++_nGlobalId;
		_strTitle = title;
		_strTags = tags;
	}
	
	public SponsoredLinkTopic(  )
	{
		this( "", "" );
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

	public String getags(  )
	{
		return _strTags;
	}

	public void setTags( String tags )
	{
		this._strTags = tags;
	}
}
