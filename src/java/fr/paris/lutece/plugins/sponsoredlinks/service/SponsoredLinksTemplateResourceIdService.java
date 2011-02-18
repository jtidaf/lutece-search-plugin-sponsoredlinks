package fr.paris.lutece.plugins.sponsoredlinks.service;

import java.util.Locale;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;


public class SponsoredLinksTemplateResourceIdService extends ResourceIdService
{
    public static final String PLUGIN_NAME = "sponsoredlinks";
    
	/** Permission for managing advanced parameters */
    public static final String PERMISSION_MANAGE_ADVANCED_PARAMETERS = "MANAGE_ADVANCED_PARAMETERS";

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.template";
    private static final String PROPERTY_LABEL_MANAGE_ADVANCED_PARAMETERS = "sponsoredlinks.permission.label.manage_advanced_parameters";


    public SponsoredLinksTemplateResourceIdService(  )
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
        rt.setResourceIdServiceClass( SponsoredLinksTemplateResourceIdService.class.getName(  ) );
        rt.setPluginName( PLUGIN_NAME );
        rt.setResourceTypeKey( SponsoredLinkTemplate.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );
        
        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_MANAGE_ADVANCED_PARAMETERS );
        p.setPermissionTitleKey( PROPERTY_LABEL_MANAGE_ADVANCED_PARAMETERS );
        rt.registerPermission( p );
        
        ResourceTypeManager.registerResourceType( rt );
		
	}

}
