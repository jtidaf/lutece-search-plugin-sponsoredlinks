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

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLink;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroupHome;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSetHome;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplateHome;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksPlugin;
import fr.paris.lutece.plugins.sponsoredlinks.service.sponsoredlinkssearch.SponsoredLinksSearchItem;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.search.SearchIndexer;
import fr.paris.lutece.portal.service.search.SearchItem;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.IOException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * Indexer Service for SponsoredLinks group of tags
 *
 */
public class SponsoredLinksIndexer implements SearchIndexer
{
	/** Unique name for this indexer */
	public static final String PROPERTY_INDEXER_NAME = "sponsoredlinks.indexer.name";
	/** Unique type for the index */
	public static final String INDEX_TYPE_SPONSOREDLINKS = "sponsoredlinks";
	/** id key for sets */
	public static final String SET_SHORT_NAME = AppPropertiesService.getProperty( "sponsoredlinks.indexer.short_name.set", "set" );
	/** id key for links */
	public static final String LINK_SHORT_NAME = AppPropertiesService.getProperty( "sponsoredlinks.indexer.short_name.link", "ord" );
	
	public static final String GROUP_SHORT_NAME = AppPropertiesService.getProperty( "sponsoredlinks.indexer.short_name.group", "grp" );
	
	//Regex for doc UID
	private static final String SPONSOREDLINK_ID_REGEX = 
		"(\\d+)_" + SET_SHORT_NAME +":(\\d+)_" + LINK_SHORT_NAME;

	//Pre-compiled representation of the regex.
	private static final Pattern _pattern = Pattern.compile( SPONSOREDLINK_ID_REGEX );
	
    private static final String ENABLE_VALUE_TRUE = "1";
    private static final String PROPERTY_INDEXER_DESCRIPTION = "sponsoredlinks.indexer.description";
    private static final String PROPERTY_INDEXER_VERSION = "sponsoredlinks.indexer.version";
    private static final String PROPERTY_INDEXER_ENABLE = "sponsoredlinks.indexer.enable";

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

        Matcher matcher = _pattern.matcher( strId );
        if( !matcher.matches(  ) )
        {
        	AppLogService.error( new InvalidParameterException( strId + "is not a valid id for sponsoredlinks document" ) );
        	return listDocuments;
        }
        
        int nSetId = Integer.valueOf( matcher.group( 1 ) );
        int nLinkOrder = Integer.valueOf( matcher.group( 2 ) );
        
        SponsoredLinkSet set = SponsoredLinkSetHome.findByPrimaryKey( nSetId, plugin );
        if( set == null )
        {
        	AppLogService.error( new InvalidParameterException( "Can't find set with id :" + nLinkOrder ) );
        	return listDocuments;
        }
        
        SponsoredLink link = null;
        for( SponsoredLink currentLink : set.getSponsoredLinkList(  ) )
        {
        	if( currentLink.getOrder(  ) == nLinkOrder )
        	{
        		link = currentLink;
        		break;
        	}
        }
        if( link == null )
        {
        	AppLogService.error( new InvalidParameterException( "Can't find link at order :" + nLinkOrder ) );
        	return listDocuments;
        }
        
        SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( set.getGroupId(  ), plugin );
        SponsoredLinkTemplate template = SponsoredLinkTemplateHome.findByPrimaryKey( nLinkOrder, plugin );

        org.apache.lucene.document.Document docGroup = getDocument( group, link, set, template );
        listDocuments.add( docGroup );

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
    public void indexDocuments(  ) throws IOException, InterruptedException, SiteMessageException
    {
        Plugin plugin = PluginService.getPlugin( SponsoredLinksPlugin.PLUGIN_NAME );

        Collection<SponsoredLinkSet> listSets = SponsoredLinkSetHome.findAll( plugin );
        ArrayList<SponsoredLinkTemplate> listTemplate = 
        	(ArrayList<SponsoredLinkTemplate>) SponsoredLinkTemplateHome.findAll( plugin );

        for ( SponsoredLinkSet currentSet : listSets )
        {
        	SponsoredLinkSet set = SponsoredLinkSetHome.findByPrimaryKey( currentSet.getId(  ), plugin );
        	SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( set.getGroupId(  ), plugin );
        	
        	for( SponsoredLink link : set.getSponsoredLinkList(  ) )
        	{
        		org.apache.lucene.document.Document document = getDocument( group, link, set, listTemplate.get( link.getOrder(  ) - 1 ) );
                IndexationService.write( document );
        	}
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
     * Builds an indexable document from the specified sponsored link (stores ids indexes tags)
     * @param group the SponsoredLinkGroup to containing the tag to index and an id to store
     * @param link the link containing the html link to parse and store and its order in the set
     * @param set the set containing the id to concat to link order to form the id of the doc
     * @param template the template from which one can resolve the type of document has to be stored
     * @return the built Document
     */
    private Document getDocument( SponsoredLinkGroup group, SponsoredLink link, SponsoredLinkSet set, SponsoredLinkTemplate template )
    {
        // make a new, empty document
        Document doc = new Document(  );

        // Add the uid as a field, so that index can be incrementally maintained.
        // Use an UnIndexed field, so that the uid is just stored with the 
        //question/answer, but is not searchable.
        String strUID = set.getId(  ) + "_" + SET_SHORT_NAME + ":" + link.getOrder(  ) + "_" + LINK_SHORT_NAME;
        doc.add( new Field( SearchItem.FIELD_UID, strUID, Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        doc.add( new Field( SearchItem.FIELD_TYPE, INDEX_TYPE_SPONSOREDLINKS, Field.Store.YES, Field.Index.NOT_ANALYZED ) );
        doc.add( new Field( SearchItem.FIELD_TITLE, link.getLinkAttribute( SponsoredLink.CONTENT ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );
        doc.add( new Field( SearchItem.FIELD_URL, link.getLinkAttribute( SponsoredLink.HREF ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );
        String strSummary = link.getLinkAttribute( SponsoredLink.ALT );
        if( ( strSummary == null ) || strSummary.equals( "" ) )
        {
        	strSummary = link.getLinkAttribute( SponsoredLink.TITLE );
        }
        if( ( strSummary != null ) && !strSummary.equals( "" ) )
        {
        	doc.add( new Field( SearchItem.FIELD_SUMMARY, strSummary, Field.Store.YES, Field.Index.NOT_ANALYZED ) );
        }
        doc.add( new Field( SearchItem.FIELD_CONTENTS, group.getTags(  ), Field.Store.NO, Field.Index.ANALYZED ) );
        
        //specific field for sponsored links
        doc.add( new Field( SponsoredLinksSearchItem.FIELD_TARGET_TYPE, template.getDescription(  ), Field.Store.YES, Field.Index.ANALYZED ) );

        //return the document	
        return doc;
    }
}
