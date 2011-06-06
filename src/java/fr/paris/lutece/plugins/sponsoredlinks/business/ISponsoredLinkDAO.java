/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;


/**
 *
 * ISponsoredLinkDAO
 *
 */
public interface ISponsoredLinkDAO
{
    /** Unique id for spring bean declaration in the plugin context */
    String SPRING_BEAN_ID = "sponsoredlinks.sponsoredLinkDAO";

    /**
     * Insert a new record in the table.
     * @param setId The id of the owning SponsoredLinkSet
     * @param link The SponsoredLink object
     * @param plugin The plugin
     */
    void insert( int setId, SponsoredLink link, Plugin plugin );

    /**
     * Delete a record from the table
     * @param setId The id of the owning SponsoredLinkSet
     * @param link The SponsoredLink object
     * @param plugin The plugin
     */
    void delete( int setId, SponsoredLink link, Plugin plugin );

    /**
     * Delete links owned by the specified SponsoredLinkSet
     * @param setId The id of the set
     * @param plugin The plugin
     */
    void deleteBySet( int setId, Plugin plugin );

    /**
     * Delete links matching the specified SponsoredLinkTemplate
     * @param templateId The id of the template
     * @param plugin The plugin
     */
    void deleteByTemplate( int templateId, Plugin plugin );

    /**
     * Update the record in the table
     * @param setId The id of the SponsoredLinkSet
     * @param link The SponsoredLink object to store
     * @param plugin The Plugin object
     */
    void store( int setId, SponsoredLink link, Plugin plugin );

    /**
     * Load the list of link owned by the specified SponsoredLinkSet
     * @param setId The id of the set
     * @param plugin The Plugin object
     * @return The Collection of the SponsoredLink objects
     */
    Collection<SponsoredLink> selectAllBySet( int setId, Plugin plugin );
}
