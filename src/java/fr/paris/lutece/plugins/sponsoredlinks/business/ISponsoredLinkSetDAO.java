package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface ISponsoredLinkSetDAO {

	/**
	 * Insert a new record in the table.
	 * @param group The SponsoredLinkSet object
	 * @param plugin The plugin
	 */
	void insert( SponsoredLinkSet set, Plugin plugin );

	/**
	 * Load the data of SponsoredLinkSet from the table
	 * @param nSetId The identifier of SponsoredLinkSet
	 * @param plugin The plugin
	 * @return the instance of the SponsoredLinkSet
	 */
	SponsoredLinkSet load( int nSetId, Plugin plugin );

	/**
	 * Delete a record from the table
	 * @param set The SponsoredLinkSetp object
	 * @param plugin The plugin
	 */
	void delete( SponsoredLinkSet set, Plugin plugin );
	

	/**
	 * Update the record in the table
	 * @param set The reference of SponsoredLinkGroup
	 * @param plugin The Plugin object
	 */
	void store( SponsoredLinkSet set, Plugin plugin );

	/**
	 * Load the list of set
	 * @param plugin The Plugin object
	 * @return The Collection of the SponsoredLinkSet objects
	 */
	Collection<SponsoredLinkSet> selectAll( Plugin plugin );
	
	/**
	 * Load the list of set linked to the specified SponsoredLinkGroup
	 * @param idGroup The id of the linked group
	 * @param plugin The Plugin object
	 * @return The Collection of the SponsoredLinkSet objects
	 */
	Collection<SponsoredLinkSet> selectByGroup( int groupId, Plugin plugin );
}