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
import java.util.List;


/**
 *
 * This class provides instances management methods (create, find, ...) for SponsoredLinkSet objects
 *
 */
public final class SponsoredLinkSetHome
{
    // Static variables pointed at the DAO instances
    private static ISponsoredLinkSetDAO _daoSet = (ISponsoredLinkSetDAO) SpringContextService.getPluginBean( SponsoredLinksPlugin.PLUGIN_NAME,
            ISponsoredLinkSetDAO.SPRING_BEAN_ID );
    private static ISponsoredLinkDAO _daoLink = (ISponsoredLinkDAO) SpringContextService.getPluginBean( SponsoredLinksPlugin.PLUGIN_NAME,
            ISponsoredLinkDAO.SPRING_BEAN_ID );

    /**
    * Private constructor - this class need not be instantiated
    */
    private SponsoredLinkSetHome(  )
    {
    }

    /**
     * Creation of an instance of SponsoredLinkSet
     *
     * @param set The instance of SponsoredLinkSet which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkSet which has been created with its primary key
     */
    public static SponsoredLinkSet create( SponsoredLinkSet set, Plugin plugin )
    {
        _daoSet.insert( set, plugin );

        int nSetId = set.getId(  );
        Collection<SponsoredLink> linkList = set.getSponsoredLinkList(  );

        for ( SponsoredLink link : linkList )
        {
            _daoLink.insert( nSetId, link, plugin );
        }

        return set;
    }

    /**
     * Update of the specified SponsoredLinkSet
     *
     * @param set The instance of SponsoredLinkSet which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkSet which has been updated
     */
    public static SponsoredLinkSet update( SponsoredLinkSet set, Plugin plugin )
    {
        _daoSet.store( set, plugin );

        int nSetId = set.getId(  );
        Collection<SponsoredLink> linkList = set.getSponsoredLinkList(  );

        for ( SponsoredLink link : linkList )
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
        set.setSponsoredLinkList( (List<SponsoredLink>) _daoLink.selectAllBySet( nKey, plugin ) );

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
     * Returns sets that are linked to the specified group
     * @param groupId The id of the referenced SponsoredLinkGroup object
     * @param plugin The Plugin object
     * @return A collection of found SponsoredLinkSet objects
     */
    public static Collection<SponsoredLinkSet> findByGroupId( int groupId, Plugin plugin )
    {
        return _daoSet.selectByGroup( groupId, plugin );
    }

    /**
     * Selects all sets and put them in a ReferenceList
     * @param plugin the plugin using this access service
     * @return a ReferenceList of SponsoredLinkSet
     */
    public static ReferenceList findReferenceList( Plugin plugin )
    {
        ReferenceList referenceList = new ReferenceList(  );

        for ( SponsoredLinkSet set : _daoSet.selectAll( plugin ) )
        {
            referenceList.addItem( set.getId(  ), set.getTitle(  ) );
        }

        return referenceList;
    }
}
