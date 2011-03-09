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
package fr.paris.lutece.plugins.sponsoredlinks.web;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLink;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroupHome;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSetHome;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplateHome;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksGroupResourceIdService;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksSetResourceIdService;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksTemplateResourceIdService;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.insert.InsertService;
import fr.paris.lutece.portal.service.insert.InsertServiceManager;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class SponsoredLinksJspBean extends PluginAdminPageJspBean
{
    /** Unique name for the right to manage this plugin */
    public static final String RIGHT_MANAGE_SPONSOREDLINKS = "SPONSOREDLINKS_MANAGEMENT";

    //jsp definition
    private static final String JSP_DO_REMOVE_GROUP = "jsp/admin/plugins/sponsoredlinks/DoRemoveGroup.jsp";
    private static final String JSP_DO_REMOVE_SET = "jsp/admin/plugins/sponsoredlinks/DoRemoveSet.jsp";
    private static final String JSP_DO_REMOVE_TEMPLATE = "jsp/admin/plugins/sponsoredlinks/DoRemoveTemplate.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS = "ManageAdvancedParameters.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_GROUP = "ManageGroup.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_SET = "ManageSet.jsp";

    //markers
    private static final String MARK_INSERT_SERVICE_LIST = "insertservice_list";
    private static final String MARK_GROUP = "group";
    private static final String MARK_GROUP_LIST = "group_list";
    private static final String MARK_LINK_LIST = "link_list";
    private static final String MARK_LINK_TEMPLATE = "template";
    private static final String MARK_LINK_TEMPLATE_DESCRIPTION = "description";
    private static final String MARK_LINK_TEMPLATE_RESOURCE = "resource";
    private static final String MARK_LINK_URL = "url";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_PERMISSION_CREATE_GROUP = "permission_create_group";
    private static final String MARK_PERMISSION_CREATE_SET = "permission_create_set";
    private static final String MARK_PERMISSION_DELETE_GROUP = "permission_delete_group";
    private static final String MARK_PERMISSION_DELETE_SET = "permission_delete_set";
    private static final String MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS = "permission_manage_advanced_parameters";
    private static final String MARK_PERMISSION_MODIFY_GROUP = "permission_modify_group";
    private static final String MARK_PERMISSION_MODIFY_SET = "permission_modify_set";
    private static final String MARK_SET = "set";
    private static final String MARK_SET_GROUP = "group";
    private static final String MARK_SET_ID = "id";
    private static final String MARK_SET_LIST = "set_list";
    private static final String MARK_SET_TITLE = "title";
    private static final String MARK_TEMPLATE_LIST = "template_list";
    private static final String MARK_WEBAPP_URL = "webapp_url";

    //messages
    private static final String MESSAGE_CONFIRM_REMOVE_SET = "sponsoredlinks.message.confirmRemove.set";
    private static final String MESSAGE_CONFIRM_REMOVE_TEMPLATE = "sponsoredlinks.message.confirmRemove.template";
    private static final String MESSAGE_CONFIRM_REMOVE_UNUSED_GROUP = "sponsoredlinks.message.confirmRemove.group.unused";
    private static final String MESSAGE_CONFIRM_REMOVE_USED_GROUP = "sponsoredlinks.message.confirmRemove.group.used";

    //parameters
    private static final String PARAMETER_CANCEL = "cancel";
    private static final String PARAMETER_GROUP_ID = "id_group";
    private static final String PARAMETER_GROUP_TAGS = "tags";
    private static final String PARAMETER_GROUP_TITLE = "title";
    private static final String PARAMETER_SET_ID = "id_set";
    private static final String PARAMETER_SET_LINK_LIST = "link_list";
    private static final String PARAMETER_SET_TITLE = "title";
    private static final String PARAMETER_TEMPLATE_DESCRIPTION = "description";
    private static final String PARAMETER_TEMPLATE_ID = "id_template";
    private static final String PARAMETER_TEMPLATE_INSERTSERVICE_ID = "id_insertservice";
    private static final String PARAMETER_TEMPLATE_NEWORDER = "new_order";

    //properties
    private static final String PROPERTY_ITEM_PER_PAGE = "sponsoredlinks.itemsPerPage";
    private static final String PROPERTY_PAGE_TITLE_CREATE_GROUP = "sponsoredlinks.create_group.title";
    private static final String PROPERTY_PAGE_TITLE_CREATE_SET = "sponsoredlinks.create_set.title";
    private static final String PROPERTY_PAGE_TITLE_CREATE_TEMPLATE = "sponsoredlinks.create_template.title";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ADVANCED_PARAMETERS = "sponsoredlinks.manage_advanced_parameters.title";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_GROUP = "sponsoredlinks.manage_group.title";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_SET = "sponsoredlinks.manage_set.title";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_GROUP = "sponsoredlinks.modify_group.title";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_SET = "sponsoredlinks.modify_set.title";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_TEMPLATE = "sponsoredlinks.modify_template.title";
    private static final String PROPERTY_PAGE_TITLE_SHOW_GROUP = "sponsoredlinks.show_group.title";
    private static final String PROPERTY_PAGE_TITLE_SHOW_SET = "sponsoredlinks.show_set.title";

    //templates
    private static final String TEMPLATE_CREATE_GROUP = "admin/plugins/sponsoredlinks/create_group.html";
    private static final String TEMPLATE_CREATE_SET = "admin/plugins/sponsoredlinks/create_set.html";
    private static final String TEMPLATE_CREATE_TEMPLATE = "admin/plugins/sponsoredlinks/create_template.html";
    private static final String TEMPLATE_MANAGE_ADVANCED_PARAMETERS = "admin/plugins/sponsoredlinks/manage_advanced_parameters.html";
    private static final String TEMPLATE_MANAGE_GROUP = "admin/plugins/sponsoredlinks/manage_group.html";
    private static final String TEMPLATE_MANAGE_SET = "admin/plugins/sponsoredlinks/manage_set.html";
    private static final String TEMPLATE_MANAGE_SPONSOREDLINKS = "admin/plugins/sponsoredlinks/manage_sponsoredlinks.html";
    private static final String TEMPLATE_MODIFY_GROUP = "admin/plugins/sponsoredlinks/modify_group.html";
    private static final String TEMPLATE_MODIFY_SET = "admin/plugins/sponsoredlinks/modify_set.html";
    private static final String TEMPLATE_MODIFY_TEMPLATE = "admin/plugins/sponsoredlinks/modify_template.html";

    // other constants
    private static final String EMPTY_STRING = "";

    //session fields
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_ITEM_PER_PAGE, 50 );
    private String _strCurrentPageIndexSet;
    private int _nItemsPerPageSet;
    private String _strCurrentPageIndexGroup;
    private int _nItemsPerPageGroup;

    /// \Modèle de données
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the Manage Sponsoredlinks page
     * @param request The HTTP request
     * @return The HTML page
     */
    public String getManageSponsoredLinks( HttpServletRequest request )
    {
        setPageTitleProperty( EMPTY_STRING );

        Map<String, Object> model = new HashMap<String, Object>(  );

        boolean bPermissionAdvancedParameter = RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID,
                SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) );

        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS, bPermissionAdvancedParameter );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_SPONSOREDLINKS, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /// Set management

    /**
     * Returns the Manage Sponsoredlinks set page
     * @param request The HTTP request
     * @return The HTML page
     */
    public String getManageSet( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_SET );

        _strCurrentPageIndexSet = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexSet );
        _nItemsPerPageSet = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPageSet,
                _nDefaultItemsPerPage );

        Collection<SponsoredLinkSet> listSet = SponsoredLinkSetHome.findAll( getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        Map<String, Object>  bPermissionDeleteSetModel = new HashMap<String, Object>(  );
        for( SponsoredLinkSet set : listSet )
        {
        	bPermissionDeleteSetModel.put(String.valueOf( set.getId(  ) ), 
        			RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE,
        	                String.valueOf( set.getId(  ) ), SponsoredLinksSetResourceIdService.PERMISSION_DELETE_SET, getUser(  ) ));
        }
        LocalizedPaginator<SponsoredLinkSet> paginator = new LocalizedPaginator<SponsoredLinkSet>( (List<SponsoredLinkSet>) listSet,
                _nItemsPerPageSet, request.getRequestURI(  ), LocalizedPaginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexSet, getLocale(  ) );

        boolean bPermissionCreateSet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID, SponsoredLinksSetResourceIdService.PERMISSION_CREATE_SET, getUser(  ) );
//        boolean bPermissionDeleteSet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE,
//                RBAC.WILDCARD_RESOURCES_ID, SponsoredLinksSetResourceIdService.PERMISSION_DELETE_SET, getUser(  ) );

        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_PERMISSION_CREATE_SET, bPermissionCreateSet );
        model.put( MARK_PERMISSION_DELETE_SET, bPermissionDeleteSetModel );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPageSet );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_SET_LIST, paginator.getPageItems(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_SET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Returns the create Sponsoredlinks set page
     * @param request The HTTP request
     * @return The HTML page
     */
    public String getCreateSet( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksSetResourceIdService.PERMISSION_CREATE_SET, getUser(  ) ) )
        {
            return getManageSet( request );
        }

        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_SET );

        Collection<SponsoredLinkGroup> listUnusedGroup = SponsoredLinkGroupHome.findUnusedGroupList( getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LINK_LIST, computeLinkFormEntries(  ) );
        model.put( MARK_GROUP_LIST, listUnusedGroup );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_SET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new sponsoredlinks set
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateSet( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) != null ||
        	 !RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksSetResourceIdService.PERMISSION_CREATE_SET, getUser(  ) ) )
        {
        	return JSP_REDIRECT_TO_MANAGE_SET;
        }

        String strTitle = request.getParameter( PARAMETER_SET_TITLE );
        String strGroupId = request.getParameter( PARAMETER_GROUP_ID );
        String[] strArrayLinks = request.getParameterValues( PARAMETER_SET_LINK_LIST );

        // Mandatory fields
        if ( ( strTitle == null ) || strTitle.trim(  ).equals( "" ) || ( strGroupId == null ) ||
                strGroupId.trim(  ).equals( "" ) || ( strArrayLinks == null ) || ( strArrayLinks.length == 0 ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nGroupId = Integer.parseInt( strGroupId );

        SponsoredLinkSet set = new SponsoredLinkSet(  );
        List<SponsoredLink> linkList = new ArrayList<SponsoredLink>(  );
        SponsoredLink currentLink;

        for ( int i = strArrayLinks.length - 1; i >= 0; --i )
        {
            if ( !strArrayLinks[i].trim(  ).equals( "\u00A0" ) )
            {
                currentLink = new SponsoredLink(  );
                currentLink.setOrder( i + 1 );
                currentLink.setUrl( strArrayLinks[i] );
                linkList.add( currentLink );
            }
        }

        set.setTitle( strTitle );
        set.setGroupId( nGroupId );
        set.setSponsoredLinkList( linkList );

        SponsoredLinkSetHome.create( set, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of sets
        return JSP_REDIRECT_TO_MANAGE_SET;
    }

    /**
     * Returns the modify Sponsoredlinks set page
     * @param request The HTTP request
     * @return The HTML page
     */
    public String getModifySet( HttpServletRequest request )
    {
    	String strSetId = request.getParameter( PARAMETER_SET_ID );
    	boolean bPermissionModifySet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE,
                strSetId, SponsoredLinksSetResourceIdService.PERMISSION_MODIFY_SET, getUser(  ) );
        if ( bPermissionModifySet )
        {
        	setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_SET );
        }
        else
        {
        	setPageTitleProperty( PROPERTY_PAGE_TITLE_SHOW_SET );
        }

        int nSetId = Integer.parseInt( strSetId );
        SponsoredLinkSet set = SponsoredLinkSetHome.findByPrimaryKey( nSetId, getPlugin(  ) );

        SponsoredLinkGroup usedGroup = SponsoredLinkGroupHome.findByPrimaryKey( set.getGroupId(  ), getPlugin(  ) );

        Collection<SponsoredLinkGroup> listUnusedGroup = SponsoredLinkGroupHome.findUnusedGroupList( getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        Map<String, Object> modelSet = new HashMap<String, Object>(  );

        List<Map<String, Object>> listLinks = computeLinkFormEntries(  );

        for ( SponsoredLink link : set.getSponsoredLinkList(  ) )
        {
            listLinks.get( link.getOrder(  ) - 1 ).put( MARK_LINK_URL, link.getUrl(  ) );
        }
        
        modelSet.put( MARK_SET_ID, set.getId(  ) );
        modelSet.put( MARK_SET_TITLE, set.getTitle(  ) );
        modelSet.put( MARK_SET_GROUP, usedGroup );
        modelSet.put( MARK_LINK_LIST, listLinks );

        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_PERMISSION_MODIFY_SET, bPermissionModifySet );
        model.put( MARK_GROUP_LIST, listUnusedGroup );
        model.put( MARK_SET, modelSet );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_SET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a modified sponsoredlinks set
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doModifySet( HttpServletRequest request )
    {
    	String strSetId = request.getParameter( PARAMETER_SET_ID );
    	if ( request.getParameter( PARAMETER_CANCEL ) != null ||
    		 ( ( strSetId != null ) && !strSetId.trim(  ).equals( "" ) &&
    		   !RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, strSetId,
                    SponsoredLinksSetResourceIdService.PERMISSION_MODIFY_SET, getUser(  ) ) ) )
        {
    		return JSP_REDIRECT_TO_MANAGE_SET;
        }

        
        String strTitle = request.getParameter( PARAMETER_SET_TITLE );
        String strGroupId = request.getParameter( PARAMETER_GROUP_ID );
        String[] strArrayLinks = request.getParameterValues( PARAMETER_SET_LINK_LIST );

        // Mandatory fields
        if ( ( strSetId == null ) || strSetId.trim(  ).equals( "" ) || ( strTitle == null ) ||
                strTitle.trim(  ).equals( "" ) || ( strGroupId == null ) || strGroupId.trim(  ).equals( "" ) ||
                ( strArrayLinks == null ) || ( strArrayLinks.length == 0 ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nSetId = Integer.parseInt( strSetId );
        int nGroupId = Integer.parseInt( strGroupId );

        SponsoredLinkSet set = new SponsoredLinkSet(  );
        List<SponsoredLink> linkList = new ArrayList<SponsoredLink>(  );
        SponsoredLink currentLink;

        for ( int i = strArrayLinks.length - 1; i >= 0; --i )
        {
            currentLink = new SponsoredLink(  );
            currentLink.setOrder( i + 1 );

            if ( !strArrayLinks[i].trim(  ).equals( "\u00A0" ) )
            {
                currentLink.setUrl( strArrayLinks[i] );
            }

            linkList.add( currentLink );
        }

        set.setId( nSetId );
        set.setTitle( strTitle );
        set.setGroupId( nGroupId );
        set.setSponsoredLinkList( linkList );

        SponsoredLinkSetHome.update( set, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of sets
        return JSP_REDIRECT_TO_MANAGE_SET;
    }

    /**
     * Manages the removal form of a sponsoredlink set whose identifier is in the http request
    *
    * @param request The Http request
    * @return the html code to confirm
    */
    public String getConfirmRemoveSet( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_SET_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_SET );
        url.addParameter( PARAMETER_SET_ID, nId );

        Object[] args = { request.getParameter( PARAMETER_SET_TITLE ) };

        String strMessageKey = MESSAGE_CONFIRM_REMOVE_SET;

        return AdminMessageService.getMessageUrl( request, strMessageKey, args, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal form of a SponsoredLinkSet
     *
     * @param request The http request
     * @return The jsp URL to display the manage set page
     */
    public String doRemoveSet( HttpServletRequest request )
    {
        String strId = request.getParameter( PARAMETER_SET_ID );
    	if ( !RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, strId,
                    SponsoredLinksSetResourceIdService.PERMISSION_DELETE_SET, getUser(  ) ) )
        {
            // if the user is not authorized, redirects quietly towards the list of sets
            return JSP_REDIRECT_TO_MANAGE_SET;
        }

        int nId = Integer.parseInt( strId );

        SponsoredLinkSet set = SponsoredLinkSetHome.findByPrimaryKey( nId, getPlugin(  ) );
        SponsoredLinkSetHome.remove( set, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_SET;
    }

    /**
     * Processes link templates
     * @return the
     */
    private List<Map<String, Object>> computeLinkFormEntries(  )
    {
        List<Map<String, Object>> listLinks = new ArrayList<Map<String, Object>>(  );

        Map<String, Object> modelLink;
        Map<String, Object> modelLinkTemplate;
        InsertService insertService;

        Collection<SponsoredLinkTemplate> listTemplate = SponsoredLinkTemplateHome.findAll( getPlugin(  ) );

        for ( SponsoredLinkTemplate linkTemplate : listTemplate )
        {
            modelLinkTemplate = new HashMap<String, Object>(  );
            insertService = linkTemplate.getInsertService(  );

            insertService.setLocale( getLocale(  ) );
            modelLinkTemplate.put( MARK_LINK_TEMPLATE_RESOURCE, insertService );

            modelLinkTemplate.put( MARK_LINK_TEMPLATE_DESCRIPTION, linkTemplate.getDescription(  ) );

            modelLink = new HashMap<String, Object>(  );
            modelLink.put( MARK_LINK_TEMPLATE, modelLinkTemplate );
            listLinks.add( modelLink );
        }

        return listLinks;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /// Group management

    /**
     * Returns the Manage Sponsoredlinks group page
     * @param request The HTTP request
     * @return The HTML page
     */
    public String getManageGroup( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_GROUP );

        _strCurrentPageIndexGroup = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexGroup );
        _nItemsPerPageGroup = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE,
                _nItemsPerPageGroup, _nDefaultItemsPerPage );

        Collection<SponsoredLinkGroup> listGroup = SponsoredLinkGroupHome.findAll( getPlugin(  ) );
        
        Map<String, Object>  bPermissionDeleteGroupModel = new HashMap<String, Object>(  );
        for( SponsoredLinkGroup group : listGroup )
        {
        	bPermissionDeleteGroupModel.put(String.valueOf( group.getId(  ) ), 
        			RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE,
        	                String.valueOf( group.getId(  ) ), SponsoredLinksSetResourceIdService.PERMISSION_DELETE_SET, getUser(  ) ));
        }

        LocalizedPaginator<SponsoredLinkGroup> paginator = new LocalizedPaginator<SponsoredLinkGroup>( (List<SponsoredLinkGroup>) listGroup,
                _nItemsPerPageGroup, request.getRequestURI(  ), LocalizedPaginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexGroup, getLocale(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        boolean bPermissionCreateGroup = RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE,
                RBAC.WILDCARD_RESOURCES_ID, SponsoredLinksGroupResourceIdService.PERMISSION_CREATE_GROUP, getUser(  ) );

        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_PERMISSION_CREATE_GROUP, bPermissionCreateGroup );
        model.put( MARK_PERMISSION_DELETE_GROUP, bPermissionDeleteGroupModel );

        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPageGroup );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_GROUP_LIST, paginator.getPageItems(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_GROUP, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Returns the form to create a new sponsoredlink group
     *
     * @param request The Http request
     * @return The HTML creation form
     */
    public String getCreateGroup( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksGroupResourceIdService.PERMISSION_CREATE_GROUP, getUser(  ) ) )
        {
            return getManageGroup( request );
        }

        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_GROUP );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_GROUP, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new sponsoredlinks group
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateGroup( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) != null ||
        	 !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksGroupResourceIdService.PERMISSION_CREATE_GROUP, getUser(  ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_GROUP;
        }

        String strTitle = request.getParameter( PARAMETER_GROUP_TITLE );
        String strTags = request.getParameter( PARAMETER_GROUP_TAGS );

        // Mandatory fields
        if ( ( strTitle == null ) || strTitle.trim(  ).equals( "" ) || ( strTags == null ) ||
                strTags.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        SponsoredLinkGroup group = new SponsoredLinkGroup(  );

        group.setTitle( strTitle );
        group.setTags( strTags );

        SponsoredLinkGroupHome.create( group, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of groups
        return JSP_REDIRECT_TO_MANAGE_GROUP;
    }

    /**
     * Returns the form to update info about a group
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyGroup( HttpServletRequest request )
    {
    	String strId = request.getParameter( PARAMETER_GROUP_ID );
    	boolean bPermissionModifyGroup = RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE,
                strId, SponsoredLinksGroupResourceIdService.PERMISSION_MODIFY_GROUP, getUser(  ) );
    	if ( bPermissionModifyGroup )
        {
    		setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_GROUP );
        }
    	else
    	{
    		setPageTitleProperty( PROPERTY_PAGE_TITLE_SHOW_GROUP );
    	}

        int nId = Integer.parseInt( strId );

        SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( nId, getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        
        model.put( MARK_PERMISSION_MODIFY_GROUP, bPermissionModifyGroup );
        model.put( MARK_GROUP, group );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_GROUP, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a modified sponsoredlinks group
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doModifyGroup( HttpServletRequest request )
    {
    	String strId = request.getParameter( PARAMETER_GROUP_ID );
        if ( request.getParameter( PARAMETER_CANCEL ) != null ||
        	 ( ( strId != null ) && !strId.trim(  ).equals( "" ) && 
        	   !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, strId,
                    SponsoredLinksGroupResourceIdService.PERMISSION_MODIFY_GROUP, getUser(  ) ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_GROUP;
        }

        int nId = Integer.parseInt( strId );
        String strTitle = request.getParameter( PARAMETER_GROUP_TITLE );
        String strTags = request.getParameter( PARAMETER_GROUP_TAGS );

        // Mandatory fields
        if ( ( strTitle == null ) || strTitle.trim(  ).equals( "" ) || ( strTags == null ) ||
                strTags.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        SponsoredLinkGroup group = new SponsoredLinkGroup(  );
        group.setId( nId );
        group.setTitle( strTitle );
        group.setTags( strTags );

        SponsoredLinkGroupHome.update( group, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of groups
        return JSP_REDIRECT_TO_MANAGE_GROUP;
    }

    /**
     * Manages the removal form of a sponsoredlink group whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveGroup( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_GROUP_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_GROUP );
        url.addParameter( PARAMETER_GROUP_ID, nId );

        Object[] args = { request.getParameter( PARAMETER_GROUP_TITLE ) };

        String strMessageKey;

        if ( SponsoredLinkGroupHome.findUsedGroup( nId, getPlugin(  ) ) != null )
        {
            strMessageKey = MESSAGE_CONFIRM_REMOVE_USED_GROUP;
        }
        else
        {
            strMessageKey = MESSAGE_CONFIRM_REMOVE_UNUSED_GROUP;
        }

        return AdminMessageService.getMessageUrl( request, strMessageKey, args, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal form of a sponsoredlinkgroup
     *
     * @param request The http request
     * @return The jsp URL to display the manage group page
     */
    public String doRemoveGroup( HttpServletRequest request )
    {
    	String strId = request.getParameter( PARAMETER_GROUP_ID );
        if ( !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, strId,
                    SponsoredLinksGroupResourceIdService.PERMISSION_DELETE_GROUP, getUser(  ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_GROUP;
        }

        int nId = Integer.parseInt( strId );

        SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( nId, getPlugin(  ) );
        SponsoredLinkGroupHome.remove( group, getPlugin(  ) );

        Collection<SponsoredLinkSet> listSet = SponsoredLinkSetHome.findByGroupId( nId, getPlugin(  ) );

        for ( SponsoredLinkSet set : listSet )
        {
            SponsoredLinkSetHome.remove( set, getPlugin(  ) );
        }

        return JSP_REDIRECT_TO_MANAGE_GROUP;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /// Template management

    /**
     * Returns the page to manage advanced parameters of this plugin.
     * That is definition of templates for the links in sets.
     *
     * @param request The http request
     * @return The jsp URL to display the page
     */
    public String getManageAdvancedParameters( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return getManageSponsoredLinks( request );
        }

        setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_ADVANCED_PARAMETERS );

        Collection<SponsoredLinkTemplate> listLinkTemplate = SponsoredLinkTemplateHome.findAll( getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );

        for ( SponsoredLinkTemplate linkTemplate : listLinkTemplate )
        {
            try
            {
                linkTemplate.getInsertService(  ).setLocale( getLocale(  ) );
            }
            catch ( NullPointerException e )
            {
            }
        }

        model.put( MARK_LOCALE, getLocale(  ) );
        model.put( MARK_TEMPLATE_LIST, listLinkTemplate );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_ADVANCED_PARAMETERS, getLocale(  ),
                model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Returns the form to add a new link template
     * @param request The http request
     * @return The html page containing the form
     */
    public String getCreateTemplate( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return getManageSponsoredLinks( request );
        }

        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_TEMPLATE );

        Collection<InsertService> listInsertService = InsertServiceManager.getInsertServicesList(  );

        for ( InsertService insertService : listInsertService )
        {
            try
            {
                insertService.setLocale( getLocale(  ) );
            }
            catch ( NullPointerException e )
            {
                AppLogService.error( e );
            }
        }

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_LOCALE, getLocale(  ) );
        model.put( MARK_INSERT_SERVICE_LIST, listInsertService );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_TEMPLATE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Processes the data capture from the form to create a new link template
     * @param request the http request
     * @return the jsp url to return to the management page
     */
    public String doCreateTemplate( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) != null ||
        	 !RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
        }

        String strDescription = request.getParameter( PARAMETER_TEMPLATE_DESCRIPTION );
        String strInsertServiceId = request.getParameter( PARAMETER_TEMPLATE_INSERTSERVICE_ID );

        // Mandatory fields
        if ( ( strDescription == null ) || strDescription.trim(  ).equals( "" ) || ( strInsertServiceId == null ) ||
                strInsertServiceId.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        SponsoredLinkTemplate linkTemplate = new SponsoredLinkTemplate(  );
        linkTemplate.setDescription( strDescription );
        linkTemplate.setInsertService( strInsertServiceId );

        SponsoredLinkTemplateHome.create( linkTemplate, getPlugin(  ) );

        //TODO: Add a empty link in every set
        return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
    }

    /**
     * Returns the form to modify a link template
     * @param request The http request
     * @return The html page containing the form
     */
    public String getModifyTemplate( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return getManageSponsoredLinks( request );
        }

        String strTemplateId = request.getParameter( PARAMETER_TEMPLATE_ID );

        // Mandatory fields
        if ( ( strTemplateId == null ) || strTemplateId.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_TEMPLATE );

        SponsoredLinkTemplate linkTemplate = SponsoredLinkTemplateHome.findByPrimaryKey( Integer.parseInt( 
                    strTemplateId ), getPlugin(  ) );

        try
        {
            linkTemplate.getInsertService(  ).setLocale( getLocale(  ) );
        }
        catch ( NullPointerException e )
        {
            AppLogService.error( e );
        }

        Collection<InsertService> listInsertService = InsertServiceManager.getInsertServicesList(  );

        for ( InsertService insertService : listInsertService )
        {
            try
            {
                insertService.setLocale( getLocale(  ) );
            }
            catch ( NullPointerException e )
            {
                AppLogService.error( e );
            }
        }

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_LOCALE, getLocale(  ) );
        model.put( MARK_LINK_TEMPLATE, linkTemplate );
        model.put( MARK_INSERT_SERVICE_LIST, listInsertService );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_TEMPLATE, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Processes the data capture from the form to create a new link template
     * @param request the http request
     * @return the jsp url to return to the management page
     */
    public String doModifyTemplate( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) != null ||
        	 !RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
        }

        String strTemplateId = request.getParameter( PARAMETER_TEMPLATE_ID );
        String strDescription = request.getParameter( PARAMETER_TEMPLATE_DESCRIPTION );
        String strInsertServiceId = request.getParameter( PARAMETER_TEMPLATE_INSERTSERVICE_ID );

        // Mandatory fields
        if ( ( strTemplateId == null ) || strTemplateId.trim(  ).equals( "" ) || ( strDescription == null ) ||
                strDescription.trim(  ).equals( "" ) || ( strInsertServiceId == null ) ||
                strInsertServiceId.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        SponsoredLinkTemplate linkTemplate = SponsoredLinkTemplateHome.findByPrimaryKey( Integer.parseInt( 
                    strTemplateId ), getPlugin(  ) );
        //TODO: If InsertServiceId has changed delete matching link in sets
        linkTemplate.setDescription( strDescription );
        linkTemplate.setInsertService( strInsertServiceId );

        SponsoredLinkTemplateHome.update( linkTemplate, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
    }

    /**
     * Ask a confirmation for link template removal
     *
     * @param request The Http request
     * @return the html code of the confirmation page
     */
    public String getConfirmRemoveTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_TEMPLATE_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_TEMPLATE );
        url.addParameter( PARAMETER_TEMPLATE_ID, nId );

        Object[] args = { request.getParameter( PARAMETER_TEMPLATE_DESCRIPTION ) };

        String strMessageKey = MESSAGE_CONFIRM_REMOVE_TEMPLATE;

        return AdminMessageService.getMessageUrl( request, strMessageKey, args, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal of a sponsoredlinktemplate
     *
     * @param request The http request
     * @return The jsp URL to display the manage advanced parameters page
     */
    public String doRemoveTemplate( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
        }

        int nId = Integer.parseInt( request.getParameter( PARAMETER_TEMPLATE_ID ) );

        SponsoredLinkTemplate linkTemplate = SponsoredLinkTemplateHome.findByPrimaryKey( nId, getPlugin(  ) );

        //TODO: update sets
        SponsoredLinkTemplateHome.remove( linkTemplate, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
    }

    /**
     * Ask a confirmation for link template order modification
     *
     * @param request The Http request
     * @return the html code of the confirmation page
     */
    public String getConfirmChangeTemplateOrder( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_TEMPLATE_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_TEMPLATE );
        url.addParameter( PARAMETER_TEMPLATE_ID, nId );

        Object[] args = { request.getParameter( PARAMETER_TEMPLATE_DESCRIPTION ) };

        String strMessageKey = MESSAGE_CONFIRM_REMOVE_TEMPLATE;

        return AdminMessageService.getMessageUrl( request, strMessageKey, args, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal of a sponsoredlinktemplate
     *
     * @param request The http request
     * @return The jsp URL to display the manage advanced parameters page
     */
    public String doModifyTemplateOrder( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) ) )
        {
            return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
        }

        int nId = Integer.parseInt( request.getParameter( PARAMETER_TEMPLATE_ID ) );
        int nNeworder = Integer.parseInt( request.getParameter( PARAMETER_TEMPLATE_NEWORDER ) );

        SponsoredLinkTemplate linkTemplate = SponsoredLinkTemplateHome.findByPrimaryKey( nId, getPlugin(  ) );

        //TODO: update sets
        SponsoredLinkTemplateHome.updateOrder( linkTemplate, nNeworder, getPlugin(  ) );

        return JSP_REDIRECT_TO_MANAGE_ADVANCED_PARAMETERS;
    }
}
