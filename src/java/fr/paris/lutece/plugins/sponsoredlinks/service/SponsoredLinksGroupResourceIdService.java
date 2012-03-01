/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroupHome;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Locale;


/**
 *
 * class SponsoredLinksGroupResourceIdService
 *
 */
public class SponsoredLinksGroupResourceIdService extends ResourceIdService
{
    /** Permission to create a group */
    public static final String PERMISSION_CREATE_GROUP = "CREATE_GROUP";

    /** Permission to modify a group */
    public static final String PERMISSION_MODIFY_GROUP = "MODIFY_GROUP";

    /** Permission to delete a group */
    public static final String PERMISSION_DELETE_GROUP = "DELETE_GROUP";

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.group";
    private static final String PROPERTY_LABEL_CREATE_GROUP = "sponsoredlinks.permission.label.create_group";
    private static final String PROPERTY_LABEL_MODIFY_GROUP = "sponsoredlinks.permission.label.modify_group";
    private static final String PROPERTY_LABEL_DELETE_GROUP = "sponsoredlinks.permission.label.delete_group";

    /** Creates a new instance of SponsoredLinksGroupResourceIdService with initialized plugin name */
    public SponsoredLinksGroupResourceIdService(  )
    {
        setPluginName( SponsoredLinksPlugin.PLUGIN_NAME );
    }

    /**
     * Returns a list of group resource ids
     * @param locale the current locale
     * @return a RerefenceList of group resource ids
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        return SponsoredLinkGroupHome.findReferenceList( PluginService.getPlugin( getPluginName(  ) ) );
    }

    /**
     * Returns the title of the given resource
     * @param strId the id of the requested resource
     * @param locale the current locale
     * @return the requested title
     */
    public String getTitle( String strId, Locale locale )
    {
        int nGroupId;

        try
        {
            nGroupId = Integer.parseInt( strId );
        }
        catch ( NumberFormatException ne )
        {
            AppLogService.error( ne );

            return null;
        }

        SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( nGroupId,
                PluginService.getPlugin( getPluginName(  ) ) );

        if ( group == null )
        {
            return null;
        }
        else
        {
            return group.getTitle(  );
        }
    }

    /**
    * Initializes the service
    */
    public void register(  )
    {
        ResourceType rt = new ResourceType(  );
        rt.setResourceIdServiceClass( SponsoredLinksGroupResourceIdService.class.getName(  ) );
        rt.setPluginName( SponsoredLinksPlugin.PLUGIN_NAME );
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
