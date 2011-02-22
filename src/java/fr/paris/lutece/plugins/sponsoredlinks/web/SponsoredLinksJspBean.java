package fr.paris.lutece.plugins.sponsoredlinks.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroupHome;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSetHome;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksSetResourceIdService;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksTemplateResourceIdService;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksGroupResourceIdService;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

public class SponsoredLinksJspBean extends PluginAdminPageJspBean
{
    public static final String RIGHT_MANAGE_SPONSOREDLINKS = "SPONSOREDLINKS_MANAGEMENT";

    //jsp definition
    private static final String JSP_DO_REMOVE_SET = "jsp/admin/plugins/sponsoredlinks/DoRemoveSet.jsp";
    private static final String JSP_DO_REMOVE_GROUP = "jsp/admin/plugins/sponsoredlinks/DoRemoveGroup.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_SPONSOREDLINKS = "ManageSponsoredLinks.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_SET = "ManageSet.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_GROUP = "ManageGroup.jsp";
    
    //markers
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS = "permission_manage_advanced_parameters";
    private static final String MARK_PERMISSION_CREATE_SET = "permission_create_set";
    private static final String MARK_PERMISSION_MODIFY_SET = "permission_modify_set";
    private static final String MARK_PERMISSION_DELETE_SET = "permission_delete_set";
    private static final String MARK_PERMISSION_CREATE_GROUP = "permission_create_group";
    private static final String MARK_PERMISSION_MODIFY_GROUP = "permission_modify_group";
    private static final String MARK_PERMISSION_DELETE_GROUP = "permission_delete_group";
    private static final String MARK_SET_LIST = "set_list";
    private static final String MARK_GROUP_LIST = "group_list";
    private static final String MARK_TEMPLATE_LINK_LIST = "template_link_list";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_SPONSOREDLINKGROUP = "group";
    
    //messages
    private static final String MESSAGE_CONFIRM_REMOVE_USED_GROUP = "sponsoredlinks.message.confirmRemove.group.used";
    private static final String MESSAGE_CONFIRM_REMOVE_UNUSED_GROUP = "sponsoredlinks.message.confirmRemove.group.unused";
    private static final String MESSAGE_CONFIRM_REMOVE_SET = "sponsoredlinks.message.confirmRemove.set";
    private static final String MESSAGE_CONFIRM_REMOVE_TEMPLATE = "sponsoredlinks.message.confirmRemove.template";
    
    //parameters
    private static final String PARAMETER_SPONSOREDLINKGROUP_ID = "id_group";
    private static final String PARAMETER_SPONSOREDLINKGROUP_TITLE = "title";
    private static final String PARAMETER_SPONSOREDLINKGROUP_TAGS = "tags";

    //properties
    private static final String PROPERTY_ITEM_PER_PAGE = "sponsoredlinks.itemsPerPage";
	private static final String PROPERTY_PAGE_TITLE_MANAGE_SPONSOREDLINKS = "sponsoredlinks.manage_sponsoredlinks.title";
	private static final String PROPERTY_PAGE_TITLE_MANAGE_SET = "sponsoredlinks.manage_set.title";
	private static final String PROPERTY_PAGE_TITLE_CREATE_SET = "sponsoredlinks.create_set.title";
	private static final String PROPERTY_PAGE_TITLE_MODIFY_SET = "sponsoredlinks.modify_set.title";
	private static final String PROPERTY_PAGE_TITLE_MANAGE_GROUP = "sponsoredlinks.manage_group.title";
	private static final String PROPERTY_PAGE_TITLE_CREATE_GROUP = "sponsoredlinks.create_group.title";
	private static final String PROPERTY_PAGE_TITLE_MODIFY_GROUP = "sponsoredlinks.modify_group.title";
	private static final String PROPERTY_PAGE_TITLE_MANAGE_ADVANCES_PARAMETERS = "sponsoredlinks.manage_advanced_parameters.title";
        
    //templates
    private static final String TEMPLATE_CREATE_SET = "admin/plugins/sponsoredlinks/create_set.html";
    private static final String TEMPLATE_CREATE_GROUP = "admin/plugins/sponsoredlinks/create_group.html";
    private static final String TEMPLATE_MANAGE_ADVANCED_PARAMETERS = "admin/plugins/sponsoredlinks/manage_advanced_parameters.html";
    private static final String TEMPLATE_MANAGE_SET = "admin/plugins/sponsoredlinks/manage_set.html";
    private static final String TEMPLATE_MANAGE_SPONSOREDLINKS = "admin/plugins/sponsoredlinks/manage_sponsoredlinks.html";
    private static final String TEMPLATE_MANAGE_GROUP = "admin/plugins/sponsoredlinks/manage_group.html";
    private static final String TEMPLATE_MODIFY_SET = "admin/plugins/sponsoredlinks/modify_set.html";
    private static final String TEMPLATE_MODIFY_GROUP = "admin/plugins/sponsoredlinks/modify_group.html";

    // other constants
    private static final String EMPTY_STRING = "";
     
    //session fields
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_ITEM_PER_PAGE, 50 );
    private String _strCurrentPageIndexSet;
    private int _nItemsPerPageSet;
    private String _strCurrentPageIndexGroup;
    private int _nItemsPerPageGroup;
    
///////////////////////////////////////////////////////////////////////////////////////////////////
/// Modèle de données
    //TODO: Remplacer les donnees brutes par le modele dynamique
    private static List<SponsoredLinkTemplate> listTemplate;
    
    // Initialisation du modèle
    public void init( HttpServletRequest request, String strRight )
	    throws AccessDeniedException
	{
	    super.init( request, strRight );
	    listTemplate = new ArrayList<SponsoredLinkTemplate>(  );
	    
	    listTemplate.add( new SponsoredLinkTemplate( listTemplate.size(), "fiche élu", "document/fiche_elu" ) );
	    listTemplate.add( new SponsoredLinkTemplate( listTemplate.size(), "comarquage", "comarquage" ) );
	    listTemplate.add( new SponsoredLinkTemplate( listTemplate.size(), "article", "document/article" ) );
	}
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
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) );
    	
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
        _nItemsPerPageSet = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE,
                _nItemsPerPageSet, _nDefaultItemsPerPage );
        
        Collection<SponsoredLinkSet> listSet = SponsoredLinkSetHome.findAll( getPlugin(  ) );
        
        Map<String, Object> model = new HashMap<String, Object>(  );
        
        LocalizedPaginator paginator = new LocalizedPaginator( (List<SponsoredLinkSet>) listSet, _nItemsPerPageSet, getHomeUrl( request ),
                LocalizedPaginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndexSet, getLocale(  ) );
        
        boolean bPermissionCreateSet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksSetResourceIdService.PERMISSION_CREATE_SET, getUser(  ) );
        boolean bPermissionModifySet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksSetResourceIdService.PERMISSION_MODIFY_SET, getUser(  ) );
        boolean bPermissionDeleteSet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksSetResourceIdService.PERMISSION_DELETE_SET, getUser(  ) );
        
        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_PERMISSION_CREATE_SET, bPermissionCreateSet );
        model.put( MARK_PERMISSION_MODIFY_SET, bPermissionModifySet );
        model.put( MARK_PERMISSION_DELETE_SET, bPermissionDeleteSet );
        
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
    	
    	model.put( MARK_TEMPLATE_LINK_LIST, listTemplate );
    	//listGroup.add( SponsoredLinkGroup.UNDEFINED_TOPIC );
    	model.put( MARK_GROUP_LIST, listUnusedGroup );
    	
    	HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_SET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    	
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
    	setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_SET );
    	
    	_strCurrentPageIndexGroup = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexGroup );
        _nItemsPerPageGroup = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE,
                _nItemsPerPageGroup, _nDefaultItemsPerPage );
        
        Collection<SponsoredLinkGroup> listGroup = SponsoredLinkGroupHome.findAll( getPlugin(  ) );
    	
        LocalizedPaginator paginator = new LocalizedPaginator( (List<SponsoredLinkGroup>) listGroup, _nItemsPerPageGroup, request.getRequestURI(  ),
                LocalizedPaginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndexSet, getLocale(  ) );
    	
        Map<String, Object> model = new HashMap<String, Object>(  );
        
        
        
        boolean bPermissionCreateGroup = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksGroupResourceIdService.PERMISSION_CREATE_GROUP, getUser(  ) );
        boolean bPermissionModifyGroup = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksGroupResourceIdService.PERMISSION_MODIFY_GROUP, getUser(  ) );
        boolean bPermissionDeleteGroup = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksGroupResourceIdService.PERMISSION_DELETE_GROUP, getUser(  ) );
       
        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_PERMISSION_CREATE_GROUP, bPermissionCreateGroup );
        model.put( MARK_PERMISSION_MODIFY_GROUP, bPermissionModifyGroup );
        model.put( MARK_PERMISSION_DELETE_GROUP, bPermissionDeleteGroup );
        
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
    		return getManageSet( request );
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
        String strTitle = request.getParameter( PARAMETER_SPONSOREDLINKGROUP_TITLE );
        String strTags = request.getParameter( PARAMETER_SPONSOREDLINKGROUP_TAGS );

        // Mandatory fields
        if ( ( strTitle == null ) || strTitle.trim(  ).equals( "" ) || ( strTags == null ) || strTags.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        SponsoredLinkGroup group = new SponsoredLinkGroup(  );

        group.setTitle( strTitle );
        group.setTags( strTags );

        SponsoredLinkGroupHome.create( group, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the HtmlPages
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
    	if ( !RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID, 
    			SponsoredLinksGroupResourceIdService.PERMISSION_CREATE_GROUP, getUser(  ) ) )
    	{
    		return getManageSet( request );
    	}
    	
    	setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY_GROUP );
    	
    	int nId =  Integer.parseInt( request.getParameter( PARAMETER_SPONSOREDLINKGROUP_ID ) );
    	
    	SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( nId, getPlugin(  ) );
    	
    	Map<String, Object> model = new HashMap<String, Object>(  );
    	
    	model.put( MARK_SPONSOREDLINKGROUP , group );
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
    	int nId = Integer.parseInt( request.getParameter( PARAMETER_SPONSOREDLINKGROUP_ID ) );
        String strTitle = request.getParameter( PARAMETER_SPONSOREDLINKGROUP_TITLE );
        String strTags = request.getParameter( PARAMETER_SPONSOREDLINKGROUP_TAGS );

        // Mandatory fields
        if ( ( strTitle == null ) || strTitle.trim(  ).equals( "" ) || ( strTags == null ) || strTags.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        SponsoredLinkGroup group = new SponsoredLinkGroup(  );
        group.setId( nId );
        group.setTitle( strTitle );
        group.setTags( strTags );

        SponsoredLinkGroupHome.update( group, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the HtmlPages
        return JSP_REDIRECT_TO_MANAGE_GROUP;
    }
    
    /**
     * Manages the removal form of a sponsoredlink group whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveGroup ( HttpServletRequest request )
    {
    	int nId = Integer.parseInt( request.getParameter( PARAMETER_SPONSOREDLINKGROUP_ID ) );
    	
    	UrlItem url = new UrlItem( JSP_DO_REMOVE_GROUP );
    	url.addParameter( PARAMETER_SPONSOREDLINKGROUP_ID, nId );
    	
    	Object[] args = { request.getParameter( PARAMETER_SPONSOREDLINKGROUP_TITLE ) };
    	
    	String strMessageKey;
    	
    	if( SponsoredLinkGroupHome.findUsedGroup( nId, getPlugin( ) ) != null )
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
     * @return The jsp URL to display the magane group page
     */
    public String doRemoveGroup( HttpServletRequest request )
    {
    	int nId = Integer.parseInt( request.getParameter( PARAMETER_SPONSOREDLINKGROUP_ID ) );
    	 	
    	SponsoredLinkGroup group = SponsoredLinkGroupHome.findByPrimaryKey( nId, getPlugin(  ) );
    	SponsoredLinkGroupHome.remove( group, getPlugin(  ) );
    	
    	Collection<SponsoredLinkSet> listSet = SponsoredLinkSetHome.findByGroupId( nId, getPlugin(  ) );
    	for( SponsoredLinkSet set : listSet )
    	{
    		SponsoredLinkSetHome.remove( set, getPlugin(  ) );
    	}
    	
    	return JSP_REDIRECT_TO_MANAGE_GROUP;
    }
}


