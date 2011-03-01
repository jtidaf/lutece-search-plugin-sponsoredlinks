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

import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * This class provides Data Access methods for SponsoredLinkTemplate objects
 *
 */
public class SponsoredLinkTemplateDAO implements ISponsoredLinkTemplateDAO
{
	//Constants
	private static final String SQL_QUERY_NEWPK = "SELECT max( id_template ) FROM sponsoredlinks_template";
	private static final String SQL_QUERY_SELECT = "SELECT id_template, description, id_resource FROM sponsoredlinks_template WHERE id_template = ? ";
	private static final String SQL_QUERY_SELECTALL = "SELECT id_template, description, id_resource FROM sponsoredlinks_template ORDER BY id_template ASC";
    private static final String SQL_QUERY_SELECT_BY_RESOURCE_TYPE = "SELECT id_template, description, id_resource FROM sponsoredlinks_template WHERE id_resource = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_template ( id_template, description, id_resource )  VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sponsoredlinks_template WHERE id_template = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sponsoredlinks_template SET description = ? , id_resource = ?  WHERE id_template= ?  ";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#newPrimaryKey(fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public int newPrimaryKey( Plugin plugin )
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
    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#insert(fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate, fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public void insert( SponsoredLinkTemplate template, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        template.setOrder( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, template.getOrder(  ) );
        daoUtil.setString( 2, template.getDescription(  ) );
        daoUtil.setString( 3, template.getLinkedResourceType(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#load(int, fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public SponsoredLinkTemplate load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        SponsoredLinkTemplate template = null;

        if ( daoUtil.next(  ) )
        {
            template = new SponsoredLinkTemplate(  );
            template.setOrder( daoUtil.getInt( 1 ) );
            template.setDescription( daoUtil.getString( 2 ) );
            template.setLinkedResourceType( daoUtil.getString( 3 ) );
        }

        daoUtil.free(  );

        return template;
    }
    
    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#delete(fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate, fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public void delete( SponsoredLinkTemplate template, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, template.getOrder(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#store(fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate, fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public void store( SponsoredLinkTemplate template, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nSetId = template.getOrder(  );

        daoUtil.setString( 1, template.getDescription(  ) );
        daoUtil.setString( 2, template.getLinkedResourceType(  ) );
        daoUtil.setInt( 3, nSetId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#selectAll(fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public Collection<SponsoredLinkTemplate> selectAll( Plugin plugin )
    {
        Collection<SponsoredLinkTemplate> templateList = new ArrayList<SponsoredLinkTemplate>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            SponsoredLinkTemplate template = new SponsoredLinkTemplate(  );
            template.setOrder( daoUtil.getInt( 1 ) );
            template.setDescription( daoUtil.getString( 2 ) );
            template.setLinkedResourceType( daoUtil.getString( 3 ) );
            templateList.add( template );
        }

        daoUtil.free(  );

        return templateList;
    }

    /* (non-Javadoc)
	 * @see fr.paris.lutece.plugins.sponsoredlinks.business.ISponsoredLinkTemplateDAO#selectByResourceType(java.lang.String, fr.paris.lutece.portal.service.plugin.Plugin)
	 */
    public Collection<SponsoredLinkTemplate> selectByResourceType( String strResourceType , Plugin plugin )
    {
    	Collection<SponsoredLinkTemplate> templateList = new ArrayList<SponsoredLinkTemplate>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_RESOURCE_TYPE, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            SponsoredLinkTemplate template = new SponsoredLinkTemplate(  );
            template.setOrder( daoUtil.getInt( 1 ) );
            template.setDescription( daoUtil.getString( 2 ) );
            template.setLinkedResourceType( daoUtil.getString( 3 ) );
            templateList.add( template );
        }

        daoUtil.free(  );
        
        return templateList;
    }
    
}
