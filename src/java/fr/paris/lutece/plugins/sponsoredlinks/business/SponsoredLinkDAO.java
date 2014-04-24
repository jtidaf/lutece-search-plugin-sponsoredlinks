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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * This class provides Data Access methods for SponsoredLink objects
 *
 */
public class SponsoredLinkDAO implements ISponsoredLinkDAO
{
    //Constants
    private static final String SQL_QUERY_SELECTALL_BY_SET = "SELECT id_template, link FROM sponsoredlinks_link WHERE id_set = ? ORDER BY id_template ASC";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_link ( id_set, id_template, link )  VALUES ( ?, ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sponsoredlinks_link WHERE id_set = ? and id_template = ? ";
    private static final String SQL_QUERY_DELETE_BY_SET = "DELETE FROM sponsoredlinks_link WHERE id_set = ? ";
    private static final String SQL_QUERY_DELETE_BY_TEMPLATE = "DELETE FROM sponsoredlinks_link WHERE id_template = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sponsoredlinks_link SET link = ? WHERE id_set = ? and id_template = ? ";

    /**
     * {@inheritDoc}
     */
    public void insert( int setId, SponsoredLink link, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        daoUtil.setInt( 1, setId );
        daoUtil.setInt( 2, link.getOrder(  ) );
        daoUtil.setString( 3, link.getLink(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    public void deleteBySet( int setId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_SET, plugin );
        daoUtil.setInt( 1, setId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void deleteByTemplate( int templateId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_TEMPLATE, plugin );
        daoUtil.setInt( 1, templateId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void store( int setId, SponsoredLink link, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, link.getLink(  ) );
        daoUtil.setInt( 2, setId );
        daoUtil.setInt( 3, link.getOrder(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
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
            link.setLink( daoUtil.getString( 2 ) );
            linkList.add( link );
        }

        daoUtil.free(  );

        return linkList;
    }
}
