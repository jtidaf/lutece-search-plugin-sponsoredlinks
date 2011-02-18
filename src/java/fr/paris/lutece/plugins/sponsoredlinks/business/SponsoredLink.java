package fr.paris.lutece.plugins.sponsoredlinks.business;

public class SponsoredLink
{
	private int _nOrder;
	private String _strUrl;
	
	public SponsoredLink( int order, String url)
	{
		this._nOrder = order;
		this._strUrl = url;
	}
	
	
	public int getOrder(  )
	{
		return _nOrder;
	}
	
	public void setOrder( int order )
	{
		_nOrder = order;
	}

	
	public String getUrl(  )
	{
		return _strUrl;
	}
	
	public void setUrl( String url )
	{
		this._strUrl = url;
	}
		
}