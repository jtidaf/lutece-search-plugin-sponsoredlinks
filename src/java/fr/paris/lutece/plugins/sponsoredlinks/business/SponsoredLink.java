package fr.paris.lutece.plugins.sponsoredlinks.business;

public class SponsoredLink implements java.lang.Comparable
{
	private int _nOrder;
	private String _strUrl;
	
	public SponsoredLink(  )
	{
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

	/**
	 * @param other the Object to be compared. Has to be of type SponsoredLink
	 */
	public int compareTo( Object other )
	{
		int otherOrder = ((SponsoredLink) other).getOrder(  );
		if ( otherOrder > _nOrder )  return -1; 
	    else if( otherOrder == _nOrder ) return 0; 
	    else return 1;
	}
		
}