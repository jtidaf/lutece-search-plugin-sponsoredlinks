package fr.paris.lutece.plugins.sponsoredlinks.service;

import java.util.Locale;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;


public class SponsoredLinksGroupResourceIdService extends ResourceIdService
{
    public static final String PLUGIN_NAME = "sponsoredlinks";
	    
	/** Permission for managing topics */
    public static final String PERMISSION_CREATE_GROUP = "CREATE_GROUP";
    public static final String PERMISSION_MODIFY_GROUP = "MODIFY_GROUP";
    public static final String PERMISSION_DELETE_GROUP = "DELETE_GROUP";

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.group";
    private static final String PROPERTY_LABEL_CREATE_GROUP = "sponsoredlinks.permission.label.create_group";
    private static final String PROPERTY_LABEL_MODIFY_GROUP = "sponsoredlinks.permission.label.modify_group";
    private static final String PROPERTY_LABEL_DELETE_GROUP = "sponsoredlinks.permission.label.delete_group";

    public SponsoredLinksGroupResourceIdService(  )
    {
    	setPluginName( PLUGIN_NAME );
    }
    
	@Override
	public ReferenceList getResourceIdList(Locale arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle(String arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(  )
	{
		ResourceType rt = new ResourceType(  );
        rt.setResourceIdServiceClass( SponsoredLinksGroupResourceIdService.class.getName(  ) );
        rt.setPluginName( PLUGIN_NAME );
        rt.setResourceTypeKey( SponsoredLinkGroup.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );
        
        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_CREATE_GROUP );
        p.setPermissionTitleKey( PROPERTY_LABEL_CREATE_GROUP );
        rt.registerPermission( p );
        
        p = new Permission(  );
        p.setPermissionKey( PERMISSION_MODIFY_GROUP );
        p.setPermissionTitleKey( PROPERTY_LABEL_MODIFY_GROUP );
        rt.registerPermission( p );
        
        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DELETE_GROUP );
        p.setPermissionTitleKey( PROPERTY_LABEL_DELETE_GROUP );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
		
	}

}
