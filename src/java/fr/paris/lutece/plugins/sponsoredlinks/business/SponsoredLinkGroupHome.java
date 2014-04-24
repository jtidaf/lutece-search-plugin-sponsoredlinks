/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;


/**
 *
 * This class provides instances management methods (create, find, ...) for SponsoredLinkGroup objects
 *
 */
public final class SponsoredLinkGroupHome
{
    // Static variable pointed at the DAO instance
    private static ISponsoredLinkGroupDAO _dao = (ISponsoredLinkGroupDAO) SpringContextService.getPluginBean( SponsoredLinksPlugin.PLUGIN_NAME,
            ISponsoredLinkGroupDAO.SPRING_BEAN_ID );

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
    public static SponsoredLinkGroup create( SponsoredLinkGroup group, Plugin plugin )
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

    /**
     * Selects all group and put them in a ReferenceList
     * @param plugin the plugin using this data access service
     * @return a ReferenceList
     */
    public static ReferenceList findReferenceList( Plugin plugin )
    {
        ReferenceList referenceList = new ReferenceList(  );

        for ( SponsoredLinkGroup group : _dao.selectAll( plugin ) )
        {
            referenceList.addItem( group.getId(  ), group.getTitle(  ) );
        }

        return referenceList;
    }
}
