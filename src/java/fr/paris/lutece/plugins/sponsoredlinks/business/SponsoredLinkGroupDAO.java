package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public class SponsoredLinkGroupDAO implements ISponsoredLinkGroupDAO {
	
	// Constants
    private static final String SQL_QUERY_NEWPK = "SELECT max( id_group ) FROM sponsoredlinks_group ";
    private static final String SQL_QUERY_SELECT = "SELECT id_group, title, tags FROM sponsoredlinks_group WHERE id_group = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_group, title, tags FROM sponsoredlinks_group ORDER BY title, id_group DESC";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_group ( id_group, title, tags )  VALUES ( ?, ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sponsoredlinks_group WHERE id_group = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sponsoredlinks_group SET title = ? , tags = ?  WHERE id_group = ? ";
    
    private static final String SQL_QUERY_SELECT_USED_LIST = "SELECT id_group, title, tags FROM sponsoredlinks_group a WHERE EXISTS ( SELECT 1 FROM sponsoredlinks_set b WHERE a.id_group = b.id_group )";
    private static final String SQL_QUERY_SELECT_UNUSED_LIST = "SELECT id_group, title, tags FROM sponsoredlinks_group a WHERE NOT EXISTS ( SELECT 1 FROM sponsoredlinks_set b WHERE a.id_group = b.id_group )";
    private static final String SQL_QUERY_SELECT_USED = "SELECT id_group, title, tags FROM sponsoredlinks_group a WHERE EXISTS ( SELECT 1 FROM sponsoredlinks_set b WHERE b.id_group = ? )";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * Generates a new primary key
     * @param plugin The plugin
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
     * Insert a new record in the table.
     * @param group The SponsoredLinkGroup object
     * @param plugin The plugin
     */
    public void insert( SponsoredLinkGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        group.setId( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, group.getId(  ) );
        daoUtil.setString( 2, group.getTitle(  ) );
        daoUtil.setString( 3, group.getTags(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of SponsoredLinkGroup from the table
     * @param nGroupId The identifier of SponsoredLinkGroup
     * @param plugin The plugin
     * @return the instance of the SponsoredLinkGroup
     */
    public SponsoredLinkGroup load( int nGroupId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nGroupId );
        daoUtil.executeQuery(  );

        SponsoredLinkGroup group = null;

        if ( daoUtil.next(  ) )
        {
            group = new SponsoredLinkGroup(  );
            group.setId( daoUtil.getInt( 1 ) );
            group.setTitle( daoUtil.getString( 2 ) );
            group.setTags( daoUtil.getString( 3 ) );
        }

        daoUtil.free(  );

        return group;
    }

    /**
     * Delete a record from the table
     * @param group The SponsoredLinkGroup object
     * @param plugin The plugin
     */
    public void delete( SponsoredLinkGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, group.getId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param group The reference of SponsoredLinkGroup
     * @param plugin The Plugin object
     */
    public void store( SponsoredLinkGroup group, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, group.getTitle(  ) );
        daoUtil.setString( 2, group.getTags(  ) );
        daoUtil.setInt( 3, group.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the list of groups
     * @param plugin The Plugin object
     * @return The Collection of the SponsoredLinkGroup objects
     */
    public Collection<SponsoredLinkGroup> selectAll( Plugin plugin )
    {
        Collection<SponsoredLinkGroup> groupList = new ArrayList<SponsoredLinkGroup>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            SponsoredLinkGroup group = new SponsoredLinkGroup(  );
            group.setId( daoUtil.getInt( 1 ) );
            group.setTitle( daoUtil.getString( 2 ) );
            group.setTags( daoUtil.getString( 3 ) );
            groupList.add( group );
        }

        daoUtil.free(  );

        return groupList;
    }
    
    /**
     * Load the list of SponsoredLinkGroup object referenced in a SponsoredLinkSet
     * @param plugin The Plugin object
     * @return The Collection of found SponsoredLinkGroup
     */
    public Collection<SponsoredLinkGroup> selectUsedGroupList( Plugin plugin )
    {
    	Collection<SponsoredLinkGroup> groupList = new ArrayList<SponsoredLinkGroup>(  );
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_USED_LIST, plugin );
    	daoUtil.executeQuery(  );
    	
    	while ( daoUtil.next(  ) )
        {
            SponsoredLinkGroup group = new SponsoredLinkGroup(  );
            group.setId( daoUtil.getInt( 1 ) );
            group.setTitle( daoUtil.getString( 2 ) );
            group.setTags( daoUtil.getString( 3 ) );
            groupList.add( group );
        }

        daoUtil.free(  );

        return groupList;
    }
    
    /**
     * Load the list of SponsoredLinkGroup object not referenced in a SponsoredLinkSet
     * @param plugin The Plugin object
     * @return The Collection of found SponsoredLinkGroup
     */
    public Collection<SponsoredLinkGroup> selectUnusedGroupList( Plugin plugin )
    {
    	Collection<SponsoredLinkGroup> groupList = new ArrayList<SponsoredLinkGroup>(  );
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UNUSED_LIST, plugin );
    	daoUtil.executeQuery(  );
    	
    	while ( daoUtil.next(  ) )
        {
            SponsoredLinkGroup group = new SponsoredLinkGroup(  );
            group.setId( daoUtil.getInt( 1 ) );
            group.setTitle( daoUtil.getString( 2 ) );
            group.setTags( daoUtil.getString( 3 ) );
            groupList.add( group );
        }

        daoUtil.free(  );

        return groupList;
    }
    
    /**
     * Load the SponsoredLinkGroup specified by its id if used in a set
     * @param nGroupId The SponsoredLinkGroup id
     * @param plugin The Plugin object
     * @return The SponsoredLink group if used
     */
    public SponsoredLinkGroup selectUsedGroup( int nGroupId, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_USED, plugin );
    	daoUtil.setInt( 1, nGroupId );
    	daoUtil.executeQuery(  );
    	
    	SponsoredLinkGroup group = null;
    	
    	if( daoUtil.next(  ) )
    	{
    		group = new SponsoredLinkGroup(  );
    		group.setId( daoUtil.getInt( 1 ) );
    		group.setTitle( daoUtil.getString( 2 ) );
    		group.setTags( daoUtil.getString( 3 ) );
    	}
    	
    	return group;
    }
}
