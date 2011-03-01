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
package fr.paris.lutece.plugins.sponsoredlinks.service;

import java.util.Locale;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * class SponsoredLinksSetResourceIdService
 *
 */
public class SponsoredLinksSetResourceIdService extends ResourceIdService
{
	
	/** Permission to create a set */
    public static final String PERMISSION_CREATE_SET = "CREATE_SET";
    /** Permission to modify a set */
    public static final String PERMISSION_MODIFY_SET = "MODIFY_SET";
    /** Permission to delete a set */
    public static final String PERMISSION_DELETE_SET = "DELETE_SET";
    

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.set";
    private static final String PROPERTY_LABEL_CREATE_SET = "sponsoredlinks.permission.label.create_set";
    private static final String PROPERTY_LABEL_MODIFY_SET = "sponsoredlinks.permission.label.modify_set";
    private static final String PROPERTY_LABEL_DELETE_SET = "sponsoredlinks.permission.label.delete_set";

    /** Creates a new instance of SponsoredLinksSetResourceIdService */
    public SponsoredLinksSetResourceIdService(  )
    {
    	setPluginName( SponsoredLinksPlugin.PLUGIN_NAME );
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

	/**
     * Initializes the service
     */
	public void register(  )
	{
		ResourceType rt = new ResourceType(  );
        rt.setResourceIdServiceClass( SponsoredLinksSetResourceIdService.class.getName(  ) );
        rt.setPluginName( SponsoredLinksPlugin.PLUGIN_NAME );
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
