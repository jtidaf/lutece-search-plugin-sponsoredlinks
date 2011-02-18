package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

public class SponsoredLinkGroupDAO {
	
	// Constants
    private static final String SQL_QUERY_NEWPK = "SELECT max( id_group ) FROM sponsoredlinks_group ";
    private static final String SQL_QUERY_SELECT = "SELECT id_group, title, tags FROM sponsoredlinks_group WHERE id_group = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_group, title, tags FROM sponsoredlinks_group ORDER BY title, id_group DESC";
    private static final String SQL_QUERY_INSERT = "INSERT INTO sponsoredlinks_group ( id_group, title, tags )  VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM sponsoredlinks_group WHERE id_group = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE sponsoredlinks_group SET title = ? , tags = ?  WHERE id_group = ?  ";

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

}
