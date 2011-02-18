package fr.paris.lutece.plugins.sponsoredlinks.service;

import java.util.Locale;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;


public class SponsoredLinksSetResourceIdService extends ResourceIdService
{
    public static final String PLUGIN_NAME = "sponsoredlinks";
	
	/** Permission for managing sets */
    public static final String PERMISSION_CREATE_SET = "CREATE_SET";
    public static final String PERMISSION_MODIFY_SET = "MODIFY_SET";
    public static final String PERMISSION_DELETE_SET = "DELETE_SET";
    

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.set";
    private static final String PROPERTY_LABEL_CREATE_SET = "sponsoredlinks.permission.label.create_set";
    private static final String PROPERTY_LABEL_MODIFY_SET = "sponsoredlinks.permission.label.modify_set";
    private static final String PROPERTY_LABEL_DELETE_SET = "sponsoredlinks.permission.label.delete_set";


    public SponsoredLinksSetResourceIdService(  )
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
        rt.setResourceIdServiceClass( SponsoredLinksSetResourceIdService.class.getName(  ) );
        rt.setPluginName( PLUGIN_NAME );
        rt.setResourceTypeKey( SponsoredLinkSet.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );
        
        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_CREATE_SET );
        p.setPermissionTitleKey( PROPERTY_LABEL_CREATE_SET );
        rt.registerPermission( p );
        
        p = new Permission(  );
        p.setPermissionKey( PERMISSION_MODIFY_SET );
        p.setPermissionTitleKey( PROPERTY_LABEL_MODIFY_SET );
        rt.registerPermission( p );
        
        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DELETE_SET );
        p.setPermissionTitleKey( PROPERTY_LABEL_DELETE_SET );
        rt.registerPermission( p );
        
        ResourceTypeManager.registerResourceType( rt );
		
	}

}
