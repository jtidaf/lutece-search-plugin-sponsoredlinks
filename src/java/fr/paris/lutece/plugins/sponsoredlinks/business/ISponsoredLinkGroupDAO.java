/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
 * ISponsoredLinkGroupDAO
 *
 */
public interface ISponsoredLinkGroupDAO
{
    /** Unique id for spring bean declaration in the plugin context */
    String SPRING_BEAN_ID = "sponsoredlinks.sponsoredLinkGroupDAO";

    /**
     * Insert a new record in the table.
     * @param group The SponsoredLinkGroup object
     * @param plugin The plugin
     */
    void insert( SponsoredLinkGroup group, Plugin plugin );

    /**
     * Load the data of SponsoredLinkGroup from the table
     * @param nGroupId The identifier of SponsoredLinkGroup
     * @param plugin The plugin
     * @return the instance of the SponsoredLinkGroup
     */
    SponsoredLinkGroup load( int nGroupId, Plugin plugin );

    /**
     * Delete a record from the table
     * @param group The SponsoredLinkGroup object
     * @param plugin The plugin
     */
    void delete( SponsoredLinkGroup group, Plugin plugin );

    /**
    * Update the record in the table
    * @param group The reference of SponsoredLinkGroup
    * @param plugin The Plugin object
    */
    void store( SponsoredLinkGroup group, Plugin plugin );

    /**
     * Load the list of groups
     * @param plugin The plugin
     * @return The Collection of the SponsoredLinkGroup
     */
    Collection<SponsoredLinkGroup> selectAll( Plugin plugin );

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
