package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class SponsoredLinkSetHome
{
	// Static variable pointed at the DAO instance
	private static ISponsoredLinkSetDAO _daoSet = (ISponsoredLinkSetDAO) SpringContextService.getPluginBean( "sponsoredlinks", "sponsoredLinkSetDAO" );
	private static ISponsoredLinkDAO _daoLink = (ISponsoredLinkDAO) SpringContextService.getPluginBean( "sponsoredlinks", "sponsoredLinkDAO" );

	/**
     * Private constructor - this class need not be instantiated
     */
    private SponsoredLinkSetHome(  )
    {
    }
    
    /**
     * Creation of an instance of SponsoredLinkGroup
     *  
     * @param set The instance of SponsoredLinkSet which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkset which has been created with its primary key
     */
    public static SponsoredLinkSet create( SponsoredLinkSet set, Plugin plugin)
    {
    	_daoSet.insert( set, plugin );
    	
    	int nSetId = set.getId(  );
    	Collection<SponsoredLink> linkList = set.getSponsoredLinkSet(  );
    	
    	for( SponsoredLink link : linkList )
    	{
    		_daoLink.insert( nSetId, link, plugin );
    	}
    	
    	return set;
    }
    
    /**
     * Update of the specified SponsoredLinkSet
     * 
     * @param set The instance of SposnsoredLinkSet which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkSet which has been updated
     */
    public static SponsoredLinkSet update( SponsoredLinkSet set, Plugin plugin )
    {
    	_daoSet.store( set, plugin );
    	
    	int nSetId = set.getId(  );
    	Collection<SponsoredLink> linkList = set.getSponsoredLinkSet(  );
    	
    	for( SponsoredLink link : linkList )
    	{
    		_daoLink.store( nSetId, link, plugin );
    	}
    	
    	return set;
    }
    
    /**
     * Remove the specified SponsoredLinkSet
     * 
     * @param set The SponsoredLinkSet object to remove
     * @param plugin The Plugin object
     */
    public static void remove( SponsoredLinkSet set, Plugin plugin )
    {
    	_daoSet.delete( set, plugin );
    	_daoLink.deleteBySet( set.getId(  ), plugin );
    	
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of the SponsoredLinkSet whose identifier is specified in parameter
     * 
     * @param nKey The primary key of the set
     * @param plugin The Plugin object
     * @return An instance of the found set
     */
    public static SponsoredLinkSet findByPrimaryKey( int nKey, Plugin plugin )
    {
    	SponsoredLinkSet set = _daoSet.load( nKey, plugin );
    	set.addAllLink( _daoLink.selectAllBySet( nKey, plugin ) );
    	return set;
    }
    
    /**
     * Returns all stored SponsoredLinkSet objects
     * 
     * @param plugin The Plugin object
     * @return A collection of all sets
     */
    public static Collection<SponsoredLinkSet> findAll( Plugin plugin )
    {
    	return _daoSet.selectAll( plugin );
    }
    
    /**
     * Returns all stored SponsoredLinkSet objects
     * 
     * @param plugin The Plugin object
     * @return A collection of all sets
     */
    public static Collection<SponsoredLinkSet> findByGroupId( int groupId, Plugin plugin )
    {
    	return _daoSet.selectByGroup( groupId, plugin );
    }
}
