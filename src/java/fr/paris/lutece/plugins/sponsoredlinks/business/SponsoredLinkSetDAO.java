package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public class SponsoredLinkSetDAO implements ISponsoredLinkSetDAO {
	
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
     * @param group The SponsoredLinkSet object
     * @param plugin The plugin
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
     * Load the data of SponsoredLinkSet from the table
     * @param nSetId The identifier of SponsoredLinkSet
     * @param plugin The plugin
     * @return the instance of the SponsoredLinkSet
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
     * Delete a record from the table
     * @param set The SponsoredLinkSet object
     * @param plugin The plugin
     */
    public void delete( SponsoredLinkSet set, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, set.getId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
    
    /**
     * Delete SponsoredLinkSet owned by the specified SponsoredLinkGroup
     * @param set The id of the owning group
     * @param plugin The plugin
     */
    public Collection<SponsoredLinkSet> selectByGroup( int groupId , Plugin plugin )
    {
    	Collection<SponsoredLinkSet> setList = new ArrayList<SponsoredLinkSet>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
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
    
    /**
     * Update the record in the table
     * @param set The reference of SponsoredLinkGroup
     * @param plugin The Plugin object
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
     * Load the list of groups
     * @param plugin The Plugin object
     * @return The Collection of the SponsoredLinkSet objects
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





}
