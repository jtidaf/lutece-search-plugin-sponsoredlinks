/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplateHome;
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
 * class SponsoredLinksTemplateResourceIdService
 *
 */
public class SponsoredLinksTemplateResourceIdService extends ResourceIdService
{
    /** Permission for managing advanced parameters */
    public static final String PERMISSION_MANAGE_ADVANCED_PARAMETERS = "MANAGE_ADVANCED_PARAMETERS";

    /**Labels*/
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "sponsoredlinks.permission.label.resourceType.template";
    private static final String PROPERTY_LABEL_MANAGE_ADVANCED_PARAMETERS = "sponsoredlinks.permission.label.manage_advanced_parameters";

    /** Creates a new instance of SponsoredLinksTemplateResourceIdService with initialized plugin name */
    public SponsoredLinksTemplateResourceIdService(  )
    {
        setPluginName( SponsoredLinksPlugin.PLUGIN_NAME );
    }

    /**
     * Returns a list of template resource ids
     * @param locale the current locale
     * @return A ReferenceList of template resource ids
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        return SponsoredLinkTemplateHome.findReferenceList( PluginService.getPlugin( getPluginName(  ) ) );
    }

    /**
     * Returns the title of a given template resource
     * @param strId the id of the requested template resource
     * @param locale  the current locale
     * @return the title of the requested resource
     */
    public String getTitle( String strId, Locale locale )
    {
        int nTemplateId;

        try
        {
            nTemplateId = Integer.parseInt( strId );
        }
        catch ( NumberFormatException ne )
        {
            AppLogService.error( ne );

            return null;
        }

        SponsoredLinkTemplate template = SponsoredLinkTemplateHome.findByPrimaryKey( nTemplateId,
                PluginService.getPlugin( getPluginName(  ) ) );

        if ( template == null )
        {
            return null;
        }
        else
        {
            return template.getDescription(  );
        }
    }

    /**
    * Initializes the service
    */
    public void register(  )
    {
        ResourceType rt = new ResourceType(  );
        rt.setResourceIdServiceClass( SponsoredLinksTemplateResourceIdService.class.getName(  ) );
        rt.setPluginName( SponsoredLinksPlugin.PLUGIN_NAME );
        rt.setResourceTypeKey( SponsoredLinkTemplate.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_MANAGE_ADVANCED_PARAMETERS );
        p.setPermissionTitleKey( PROPERTY_LABEL_MANAGE_ADVANCED_PARAMETERS );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }
}
