package fr.paris.lutece.plugins.sponsoredlinks.service;

import java.util.Locale;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTopic;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;


public class SponsoredLinksTopicResourceIdService extends ResourceIdService
{
    public static final String PLUGIN_NAME = "sponsoredlinks";
	    
	/** Permission for managing topics */
    public static final String PERMISSION_CREATE_TOPIC = "CREATE_TOPIC";
    public static final String PERMISSION_MODIFY_TOPIC = "MODIFY_TOPIC";
    public static final String PERMISSION_DELETE_TOPIC = "DELETE_TOPIC";

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.topic";
    private static final String PROPERTY_LABEL_CREATE_TOPIC = "sponsoredlinks.permission.label.create_topic";
    private static final String PROPERTY_LABEL_MODIFY_TOPIC = "sponsoredlinks.permission.label.modify_topic";
    private static final String PROPERTY_LABEL_DELETE_TOPIC = "sponsoredlinks.permission.label.delete_topic";

    public SponsoredLinksTopicResourceIdService(  )
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
        rt.setResourceIdServiceClass( SponsoredLinksTopicResourceIdService.class.getName(  ) );
        rt.setPluginName( PLUGIN_NAME );
        rt.setResourceTypeKey( SponsoredLinkTopic.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );
        
        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_CREATE_TOPIC );
        p.setPermissionTitleKey( PROPERTY_LABEL_CREATE_TOPIC );
        rt.registerPermission( p );
        
        p = new Permission(  );
        p.setPermissionKey( PERMISSION_MODIFY_TOPIC );
        p.setPermissionTitleKey( PROPERTY_LABEL_MODIFY_TOPIC );
        rt.registerPermission( p );
        
        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DELETE_TOPIC );
        p.setPermissionTitleKey( PROPERTY_LABEL_DELETE_TOPIC );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
		
	}

}
