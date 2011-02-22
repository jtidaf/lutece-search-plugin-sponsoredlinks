package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface ISponsoredLinkGroupDAO {

	/**
	 * Insert a new record in the table.
	 *
	 * @param group The SponsoredLinkGroup object
	 * @param plugin The plugin
	 */
	void insert(SponsoredLinkGroup group, Plugin plugin);

	/**
	 * Load the data of SponsoredLinkGroup from the table
	 * @param nGroupId The identifier of SponsoredLinkGroup
	 * @param plugin The plugin
	 * @return the instance of the SponsoredLinkGroup
	 */
	SponsoredLinkGroup load(int nGroupId, Plugin plugin);

	/**
	 * Delete a record from the table
	 * @param group The SponsoredLinkGroup object
	 * @param plugin The plugin
	 */
	void delete(SponsoredLinkGroup group, Plugin plugin);

	/**
	 * Update the record in the table
	 * @param htmlpage The reference of htmlpage
	 * @param plugin The plugin
	 */
	void store(SponsoredLinkGroup group, Plugin plugin);

	/**
	 * Load the list of groups
	 *
	 * @param plugin The plugin
	 * @return The Collection of the SponsoredLinkGroup
	 */
	Collection<SponsoredLinkGroup> selectAll(Plugin plugin);
	
	/**
     * Load the list of SponsoredLinkGroup object referenced in a SponsoredLinkSet
     * @param plugin The Plugin object
     * @return The Collection of found SponsoredLinkGroup
     */
    Collection<SponsoredLinkGroup> selectUsedGroupList( Plugin plugin );
    
    /**
     * Load the list of SponsoredLinkGroup object not referenced in a SponsoredLinkSet
     * @param plugin The Plugin object
     * @return The Collection of found SponsoredLinkGroup
     */
    Collection<SponsoredLinkGroup> selectUnusedGroupList( Plugin plugin );
    
    /**
     * Load the SponsoredLinkGroup specified by its id if used in a set
     * @param nGroupId The SponsoredLinkGroup id
     * @param plugin The Plugin object
     * @return The SponsoredLink group if used
     */
    SponsoredLinkGroup selectUsedGroup( int nGroupId, Plugin plugin );

}