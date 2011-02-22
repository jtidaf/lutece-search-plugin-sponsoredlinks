package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public class SponsoredLinkDAO implements ISponsoredLinkDAO
{
	//Constants
	private static final String SQL_QUERY_SELECTALL_BY_SET = "SELECT id_set, id_template, url FROM sponsoredlinks_link WHERE id_set = ? ORDER BY id_template ASC";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_link ( iid_set, id_template, url )  VALUES ( ?, ?, ? )";
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
