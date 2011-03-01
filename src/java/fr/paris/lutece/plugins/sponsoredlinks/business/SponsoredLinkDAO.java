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

import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * This class provides Data Access methods for SponsoredLink objects
 *
 */
public class SponsoredLinkDAO implements ISponsoredLinkDAO
{
	//Constants
	private static final String SQL_QUERY_SELECTALL_BY_SET = "SELECT id_template, url FROM sponsoredlinks_link WHERE id_set = ? ORDER BY id_template ASC";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_link ( id_set, id_template, url )  VALUES ( ?, ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sponsoredlinks_link WHERE id_set = ? and id_template = ? ";
    private static final String SQL_QUERY_DELETE_BY_SET = "DELETE FROM sponsoredlinks_link WHERE id_set = ? ";
    private static final String SQL_QUERY_DELETE_BY_TEMPLATE = "DELETE FROM sponsoredlinks_link WHERE id_template = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sponsoredlinks_link SET url = ? WHERE id_set = ? and id_template = ? ";
    
    /**
     * Insert a new record in the table.
     * @param setId The id of the owning SponsoredLinkSet
     * @param link The SponsoredLink object
     * @param plugin The plugin
     */
    public void insert( int setId, SponsoredLink link, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        daoUtil.setInt( 1, setId );
        daoUtil.setInt( 2, link.getOrder(  ) );
        daoUtil.setString( 3, link.getUrl(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /**
     * Delete a record from the table
     * @param setId The id of the owning SponsoredLinkSet
     * @param link The SponsoredLink object
     * @param plugin The plugin
     */
    public void delete( int setId, SponsoredLink link, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, setId );
        daoUtil.setInt( 2, link.getOrder(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /**
     * Delete links owned by the specified SponsoredLinkSet
     * @param setId The id of the set
     * @param plugin The plugin
     */
    public void deleteBySet( int setId, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_SET, plugin );
    	daoUtil.setInt( 1, setId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /**
     * Delete links matching the specified SponsoredLinkTemplate
     * @param templateId The id of the template
     * @param plugin The plugin
     */
    public void deleteByTemplate( int templateId, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_TEMPLATE, plugin );
    	daoUtil.setInt( 1, templateId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param setId The id of the SponsoredLinkSet
     * @param link The SponsoredLink object to store
     * @param plugin The Plugin object
     */
    public void store( int setId, SponsoredLink link, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        

        daoUtil.setString( 1, link.getUrl(  ) );
        daoUtil.setInt( 2, setId );
        daoUtil.setInt( 3, link.getOrder(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the list of link owned by the specified SponsoredLinkSet
     * @param setId The id of the set
     * @param plugin The Plugin object
     * @return The Collection of the SponsoredLink objects
     */
    public Collection<SponsoredLink> selectAllBySet( int setId, Plugin plugin )
    {
        Collection<SponsoredLink> linkList = new ArrayList<SponsoredLink>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_SET, plugin );
        daoUtil.setInt( 1, setId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            SponsoredLink link = new SponsoredLink(  );
            link.setOrder( daoUtil.getInt( 1 ) );
            link.setUrl( daoUtil.getString( 2 ) );
            linkList.add( link );
        }

        daoUtil.free(  );

        return linkList;
    }

}
