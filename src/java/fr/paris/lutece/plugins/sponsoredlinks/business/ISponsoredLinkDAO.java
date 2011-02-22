package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface ISponsoredLinkDAO {

	/**
	 * Insert a new record in the table.
	 * @param setId The id of the owning SponsoredLinkSet
	 * @param link The SponsoredLink object
	 * @param plugin The plugin
	 */
	void insert(int setId, SponsoredLink link, Plugin plugin);

	/**
	 * Delete a record from the table
	 * @param setId The id of the owning SponsoredLinkSet
	 * @param link The SponsoredLink object
	 * @param plugin The plugin
	 */
	void delete(int setId, SponsoredLink link, Plugin plugin);

	/**
	 * Delete links owned by the specified SponsoredLinkSet
	 * @param setId The id of the set
	 * @param plugin The plugin
	 */
	void deleteBySet(int setId, Plugin plugin);

	/**
	 * Delete links matching the specified SponsoredLinkTemplate
	 * @param templateId The id of the template
	 * @param plugin The plugin
	 */
	void deleteByTemplate(int templateId, Plugin plugin);

	/**
	 * Update the record in the table
	 * @param setId The id of the SponsoredLinkSet
	 * @param link The SponsoredLink object to store
	 * @param plugin The Plugin object
	 */
	void store(int setId, SponsoredLink link, Plugin plugin);

	/**
	 * Load the list of link owned by the specified SponsoredLinkSet
	 * @param setId The id of the set
	 * @param plugin The Plugin object
	 * @return The Collection of the SponsoredLink objects
	 */
	Collection<SponsoredLink> selectAllBySet(int setId, Plugin plugin);

}