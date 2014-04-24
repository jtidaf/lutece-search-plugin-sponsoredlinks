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
 * This class provides Data Access methods for SponsoredLinkSet objects
 *
 */
public class SponsoredLinkSetDAO implements ISponsoredLinkSetDAO
{
    // Constants 
    private static final String SQL_QUERY_NEWPK = "SELECT max( id_set ) FROM sponsoredlinks_set";
    private static final String SQL_QUERY_SELECT = "SELECT id_set, title, id_group FROM sponsoredlinks_set WHERE id_set = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_set, title, id_group FROM sponsoredlinks_set ORDER BY title, id_set DESC";
    private static final String SQL_QUERY_SELECT_BY_GROUP = "SELECT id_set, title, id_group FROM sponsoredlinks_set WHERE id_group = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_set ( id_set, title, id_group )  VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sponsoredlinks_set WHERE id_set = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sponsoredlinks_set SET title = ? , id_group = ?  WHERE id_set= ?  ";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * Generates a new primary key
     * @param plugin the plugin
     * @return The new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEWPK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;

        daoUtil.free(  );

        return nKey;
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods using a dynamic pool
    /**
     * {@inheritDoc}
     */
    public void insert( SponsoredLinkSet set, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        set.setId( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, set.getId(  ) );
        daoUtil.setString( 2, set.getTitle(  ) );
        daoUtil.setInt( 3, set.getGroupId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public SponsoredLinkSet load( int nSetId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nSetId );
        daoUtil.executeQuery(  );

        SponsoredLinkSet set = null;

        if ( daoUtil.next(  ) )
        {
            set = new SponsoredLinkSet(  );
            set.setId( daoUtil.getInt( 1 ) );
            set.setTitle( daoUtil.getString( 2 ) );
            set.setGroupId( daoUtil.getInt( 3 ) );
        }

        daoUtil.free(  );

        return set;
    }

    /**
     * {@inheritDoc}
     */
    public void delete( SponsoredLinkSet set, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, set.getId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void store( SponsoredLinkSet set, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nSetId = set.getId(  );

        daoUtil.setString( 1, set.getTitle(  ) );
        daoUtil.setInt( 2, set.getGroupId(  ) );
        daoUtil.setInt( 3, nSetId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public Collection<SponsoredLinkSet> selectAll( Plugin plugin )
    {
        Collection<SponsoredLinkSet> setList = new ArrayList<SponsoredLinkSet>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            SponsoredLinkSet set = new SponsoredLinkSet(  );
            set.setId( daoUtil.getInt( 1 ) );
            set.setTitle( daoUtil.getString( 2 ) );
            set.setGroupId( daoUtil.getInt( 3 ) );
            setList.add( set );
        }

        daoUtil.free(  );

        return setList;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<SponsoredLinkSet> selectByGroup( int groupId, Plugin plugin )
    {
        Collection<SponsoredLinkSet> setList = new ArrayList<SponsoredLinkSet>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_GROUP, plugin );
        daoUtil.setInt( 1, groupId );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            SponsoredLinkSet set = new SponsoredLinkSet(  );
            set.setId( daoUtil.getInt( 1 ) );
            set.setTitle( daoUtil.getString( 2 ) );
            set.setGroupId( daoUtil.getInt( 3 ) );
            setList.add( set );
        }

        daoUtil.free(  );

        return setList;
    }
}
