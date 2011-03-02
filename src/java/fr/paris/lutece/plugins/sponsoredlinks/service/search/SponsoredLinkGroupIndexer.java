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
package fr.paris.lutece.plugins.sponsoredlinks.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroupHome;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksPlugin;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.search.SearchIndexer;
import fr.paris.lutece.portal.service.search.SearchItem;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * 
 * Indexer Service for SponsoredLinks group of tags
 *
 */
public class SponsoredLinkGroupIndexer implements SearchIndexer
{
	public static final String SHORT_NAME = "slg";
	public static final String PROPERTY_INDEXER_NAME = "sponsoredLinksGroup.indexer.name";
	private static final String ENABLE_VALUE_TRUE = "1";
	
    private static final String PROPERTY_INDEXER_DESCRIPTION = "sponsoredLinksGroup.indexer.description";
    private static final String PROPERTY_INDEXER_VERSION = "sponsoredLinksGroup.indexer.version";
    private static final String PROPERTY_INDEXER_ENABLE = "sponsoredLinksGroup.indexer.enable";
    
    /**
     * Returns the indexer service description
     * 
     * @return The indexer service description
     */
	public String getDescription(  )
	{
		return AppPropertiesService.getProperty( PROPERTY_INDEXER_DESCRIPTION );
	}
	
	/**
     * {@inheritDoc}
     */
	public List<Document> getDocuments( String strId )
			throws IOException, InterruptedException, SiteMessageException
	{
		ArrayList<org.apache.lucene.document.Document> listDocuments = new ArrayList<Document>(  );
        Plugin plugin = PluginService.getPlugin( SponsoredLinksPlugin.PLUGIN_NAME );

        SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( Integer.parseInt( strId ), plugin );
        if( group != null )
        {
            
            org.apache.lucene.document.Document docGroup = getDocument( group );

            listDocuments.add( docGroup );
        }
        return listDocuments;
	}
	
    /**
     * {@inheritDoc}
     */
    public String getName(  )
    {
        return AppPropertiesService.getProperty( PROPERTY_INDEXER_NAME );
    }

    /**
     * {@inheritDoc}
     */
    public String getVersion(  )
    {
        return AppPropertiesService.getProperty( PROPERTY_INDEXER_VERSION );
    }

    /**
     * {@inheritDoc}
     */
	public void indexDocuments() 
			throws IOException, InterruptedException, SiteMessageException
	{
        Plugin plugin = PluginService.getPlugin( SponsoredLinksPlugin.PLUGIN_NAME );

        Collection<SponsoredLinkGroup> listGroups = SponsoredLinkGroupHome.findUsedGroupList( plugin );

        for ( SponsoredLinkGroup group : listGroups )
        {
            Document docGroup = getDocument( group );
            IndexationService.write( docGroup );
        }
		
	}
	
    /**
     * {@inheritDoc}
     */	
	public boolean isEnable(  )
	{
		boolean bReturn = false;
        String strEnable = AppPropertiesService.getProperty( PROPERTY_INDEXER_ENABLE );

        if ( ( strEnable != null ) &&
                ( strEnable.equalsIgnoreCase( Boolean.TRUE.toString(  ) ) || strEnable.equals( ENABLE_VALUE_TRUE ) ) &&
                PluginService.isPluginEnable( SponsoredLinksPlugin.PLUGIN_NAME ) )
        {
            bReturn = true;
        }

        return bReturn;
	}
	
	
	/**
	 * Builds an indexable document from the specified group (stores id indexes tags)
	 * @param group The SponsoredLinkGroup to turn into a document
	 * @return the built Document
	 */
	private Document getDocument( SponsoredLinkGroup group )
	{
		// make a new, empty document
		Document doc = new Document(  );
		
		// Add the uid as a field, so that index can be incrementally maintained.
        // Use an UnIndexed field, so that the uid is just stored with the 
		//question/answer, but is not searchable.
		String strUidValue = group.getId(  ) + "_" + SHORT_NAME;
		doc.add( new Field( SearchItem.FIELD_UID, strUidValue, Field.Store.YES, Field.Index.NOT_ANALYZED ) );
		doc.add( new Field( SearchItem.FIELD_CONTENTS, group.getTags(  ), Field.Store.NO, Field.Index.ANALYZED ) );
		
		//return the document	
		return doc;
	}
}
