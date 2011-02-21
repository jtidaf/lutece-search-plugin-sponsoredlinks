package fr.paris.lutece.plugins.sponsoredlinks.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroupHome;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinkPlugin;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.search.SearchIndexer;
import fr.paris.lutece.portal.service.search.SearchItem;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

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
     * Return a list of lucene document for incremental indexing
     * @param strId the uid of the document
     * @return listDocuments the document list
     */
	public List<Document> getDocuments( String strId )
			throws IOException, InterruptedException, SiteMessageException
	{
		ArrayList<org.apache.lucene.document.Document> listDocuments = new ArrayList<Document>(  );
        Plugin plugin = PluginService.getPlugin( SponsoredLinkPlugin.PLUGIN_NAME );

        SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( Integer.parseInt( strId ), plugin );
        if( group != null )
        {
            
            org.apache.lucene.document.Document docGroup = getDocument( group, plugin );

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
    
	public void indexDocuments() 
			throws IOException, InterruptedException, SiteMessageException
	{
        Plugin plugin = PluginService.getPlugin( SponsoredLinkPlugin.PLUGIN_NAME );

        Collection<SponsoredLinkGroup> listGroups = SponsoredLinkGroupHome.findUsedGroupList( plugin );

        for ( SponsoredLinkGroup group : listGroups )
        {
            Document docGroup = getDocument( group, plugin );
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
                PluginService.isPluginEnable( SponsoredLinkPlugin.PLUGIN_NAME ) )
        {
            bReturn = true;
        }

        return bReturn;
	}
	
	private Document getDocument( SponsoredLinkGroup group, Plugin plugin)
	{
		// make a new, empty document
		Document doc = new Document(  );
		
		// Add the uid as a field, so that index can be incrementally maintained.
        // Use an UnIndexed field, so that the uid is just stored with the 
		//question/answer, but is not searchable.
		String strUidValue = String.valueOf( group.getId(  ) ) + "_" + SHORT_NAME;
		doc.add( new Field( SearchItem.FIELD_UID, strUidValue, Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		doc.add( new Field( SearchItem.FIELD_CONTENTS, group.getTags(  ), Field.Store.NO, Field.Index.ANALYZED ) );
		
		//return the document	
		return doc;
	}
}
