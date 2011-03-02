/*
 * Copyright (c) 2002-2010, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;

public interface ISponsoredLinkTemplateDAO
{
	/** Unique id for spring bean declaration in the plugin context */
	public static final String SPRING_BEAN_ID = "sponsoredLinkTemplateDAO";
	
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
	void insert( SponsoredLinkTemplate template, Plugin plugin );

	/**
	 * Load the data of the template whose id is specified
	 * @param nId the identifier of the SponsoredLinkTemplate object to load
	 * @param plugin the Plugin using this data access service
	 * @return the instance of the loaded SponsoredLinkTemplate object
	 */
	SponsoredLinkTemplate load( int nId, Plugin plugin );

	/**
	 * Delete a record from the table
	 * @param template the SponsoredLinkTemplate object to delete form table
	 * @param plugin the Plugin using this data access service
	 */
	void delete( SponsoredLinkTemplate template, Plugin plugin );

	/**
	 * Update the record in the table
	 * @param template the reference of the SponsoredLinkTemplate object to store
	 * @param plugin The Plugin object
	 */
	void store( SponsoredLinkTemplate template, Plugin plugin );

	/**
	 * Load the list of templates
	 * @param plugin The Plugin object
	 * @return A Collection of SponsoredLinkTemplate objects
	 */
	Collection<SponsoredLinkTemplate> selectAll( Plugin plugin );

	/**
	 * Select template linked to the specified resource type
	 * @param strResourceType The id of the linked resource type
	 * @param plugin The Plugin
	 * @return A Collection of found SponsoredLinkTemplate objects
	 */
	Collection<SponsoredLinkTemplate> selectByResourceType(
			String strResourceType, Plugin plugin );

}