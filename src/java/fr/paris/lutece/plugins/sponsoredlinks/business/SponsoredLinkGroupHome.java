package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class SponsoredLinkGroupHome
{
	 // Static variable pointed at the DAO instance
    private static ISponsoredLinkGroupDAO _dao = (ISponsoredLinkGroupDAO) SpringContextService.getPluginBean( "sponsoredlinks", "sponsoredLinkGroupDAO" );
    
    /**
     * Private constructor - this class need not be instantiated
     */
    private SponsoredLinkGroupHome(  )
    {
    }
    
    /**
     * Creation of an instance of SponsoredLinkGroup
     *  
     * @param group The instance of SponsoredLinkGroup which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkGroup which has been created with its primary key
     */
    public static SponsoredLinkGroup create( SponsoredLinkGroup group, Plugin plugin)
    {
    	_dao.insert( group, plugin );
    	
    	return group;
    }
	
    /**
     * Update of the specified SponsoredLinkGroup
     * 
     * @param group The instance of SposnsoredLinkGroup which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkGroup which has been updated
     */
    public static SponsoredLinkGroup update( SponsoredLinkGroup group, Plugin plugin )
    {
    	_dao.store( group, plugin );
    	
    	return group;
    }
    
    /**
     * Remove the specified SponsoredLinkGroup
     * 
     * @param group The SponsoredLinkGroup object to remove
     * @param plugin The Plugin object
     */
    public static void remove( SponsoredLinkGroup group, Plugin plugin )
    {
    	_dao.delete( group, plugin );
    	
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of the SponsoredLinkGroup whose identifier is specified in parameter
     * 
     * @param nKey The primary key of the group
     * @param plugin The Plugin object
     * @return An instance of the found group
     */
    public static SponsoredLinkGroup findByPrimaryKey( int nKey, Plugin plugin )
    {
    	return _dao.load( nKey, plugin );
    }
    
    /**
     * Returns all stored SponsoredLinkGroup objects
     * 
     * @param plugin The Plugin object
     * @return A collection of all groups
     */
    public static Collection<SponsoredLinkGroup> findAll( Plugin plugin )
    {
    	return _dao.selectAll( plugin );
    }
    
    /**
     * Returns all SponsoredLinkGroup objects referenced in a SponsoredLinkSet
     * 
     * @param plugin The Plugin object
     * @return A collection of used groups
     */
    public static Collection<SponsoredLinkGroup> findUsedGroupList( Plugin plugin )
    {
    	return _dao.selectUsedGroupList( plugin );
    }
    
    /**
     * Returns all SponsoredLinkGroup objects not referenced in a SponsoredLinkSet
     * 
     * @param plugin The Plugin object
     * @return A collection of unused groups
     */
    public static Collection<SponsoredLinkGroup> findUnusedGroupList( Plugin plugin )
    {
    	return _dao.selectUnusedGroupList( plugin );
    }
    
    /**
     * Returns the the SponsoredLinkGroup specified by its id if used in a set
     * 
     * @param nKey The primary key of the group
     * @param plugin The Plugin object
     * @return An instance of the group if it is used
     */
    public static SponsoredLinkGroup findUsedGroup( int nKey, Plugin plugin )
    {
    	return _dao.selectUsedGroup( nKey, plugin );
    }
    

}
