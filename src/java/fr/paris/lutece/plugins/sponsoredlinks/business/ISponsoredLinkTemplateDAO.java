package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface ISponsoredLinkTemplateDAO {

	/**
     * Generates a new primary key
     * @param plugin the Plugin using this data access service
     * @return the new primary key
     */
	int newPrimaryKey( Plugin plugin );
	
	////////////////////////////////////////////////////////////////////////
	// Methods using a dynamic pool
	/**
	 * Insert a new record in the table.
	 * @param template the SponsoredLinkTemplate object to insert
	 * @param plugin the Plugin using this data access service
	 */
	void insert(SponsoredLinkTemplate template, Plugin plugin);

	/**
	 * Load the data of the template whose id is specified
	 * @param nId the identifier of the SponsoredLinkTemplate object to load
	 * @param plugin the Plugin using this data access service
	 * @return the instance of the loaded SponsoredLinkTemplate object
	 */
	SponsoredLinkTemplate load(int nId, Plugin plugin);

	/**
	 * Delete a record from the table
	 * @param template the SponsoredLinkTemplate object to delete form table
	 * @param plugin the Plugin using this data access service
	 */
	void delete(SponsoredLinkTemplate template, Plugin plugin);

	/**
	 * Update the record in the table
	 * @param template the reference of the SponsoredLinkTemplate object to store
	 * @param plugin The Plugin object
	 */
	void store(SponsoredLinkTemplate template, Plugin plugin);

	/**
	 * Load the list of templates
	 * @param plugin The Plugin object
	 * @return A Collection of SponsoredLinkTemplate objects
	 */
	Collection<SponsoredLinkTemplate> selectAll(Plugin plugin);

	/**
	 * Select template linked to the specified resource type
	 * @param strResourceType The id of the linked resource type
	 * @param plugin The Plugin
	 * @return A Collection of found SponsoredLinkTemplate objects
	 */
	Collection<SponsoredLinkTemplate> selectByResourceType(
			String strResourceType, Plugin plugin);

}