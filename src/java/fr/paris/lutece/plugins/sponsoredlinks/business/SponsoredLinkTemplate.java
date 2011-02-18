package fr.paris.lutece.plugins.sponsoredlinks.business;

public class SponsoredLinkTemplate
{
	public static final String RESOURCE_TYPE = "SPONSOREDLINKS_TEMPLATE_TYPE";
	
	private int _nOrder;
	private String _strDescription;
	private String _strLinkedResourceType;
	
	public SponsoredLinkTemplate( int order, String description, String linkedResourceType )
	{
		_nOrder = order;
		_strDescription = description;
		_strLinkedResourceType = linkedResourceType;
	}

	public int getOrder(  )
	{
		return _nOrder;
	}

	public void setOrder( int order )
	{
		_nOrder = order;
	}

	public String getDescription(  )
	{
		return _strDescription;
	}

	public void setDescription( String description )
	{
		_strDescription = description;
	}

	public String getLinkedResourceType(  )
	{
		return _strLinkedResourceType;
	}

	public void setLinkedResourceType( String linkedResourceType )
	{
		_strLinkedResourceType = linkedResourceType;
	}

}
