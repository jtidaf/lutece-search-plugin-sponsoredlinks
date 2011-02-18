package fr.paris.lutece.plugins.sponsoredlinks.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkTemplate;
import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksSetResourceIdService;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksTemplateResourceIdService;
import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksTopicResourceIdService;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;

public class SponsoredLinksJspBean extends PluginAdminPageJspBean
{
    public static final String RIGHT_MANAGE_SPONSOREDLINKS = "SPONSOREDLINKS_MANAGEMENT";
    
    //templates
    private static final String TEMPLATE_CREATE_SET = "admin/plugins/sponsoredlinks/create_set.html";
    private static final String TEMPLATE_CREATE_TOPIC = "admin/plugins/sponsoredlinks/create_topic.html";
    private static final String TEMPLATE_MANAGE_ADVANCED_PARAMETERS = "admin/plugins/sponsoredlinks/manage_advanced_parameters.html";
    private static final String TEMPLATE_MANAGE_SET = "admin/plugins/sponsoredlinks/manage_set.html";
    private static final String TEMPLATE_MANAGE_SPONSOREDLINKS = "admin/plugins/sponsoredlinks/manage_sponsoredlinks.html";
    private static final String TEMPLATE_MANAGE_TOPIC = "admin/plugins/sponsoredlinks/manage_topic.html";
    private static final String TEMPLATE_MODIFY_SET = "admin/plugins/sponsoredlinks/modify_set.html";
    private static final String TEMPLATE_MODIFY_TOPIC = "admin/plugins/sponsoredlinks/modify_topic.html";

    //jsp definition
    private static final String JSP_DO_REMOVE_SET = "jsp/admin/plugins/sponsoredlinks/DoRemoveSet.jsp";
    private static final String JSP_DO_REMOVE_TOPIC = "jsp/admin/plugins/sponsoredlinks/DoRemoveTopic.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_SPONSOREDLINKS = "ManageSponsoredLinks.jsp";
    
    //properties
    private static final String PROPERTY_ITEM_PER_PAGE = "sponsoredlinks.itemsPerPage";
	private static final String PROPERTY_PAGE_TITLE_MANAGE_SPONSOREDLINKS = "sponsoredlinks.manage_sponsoredlinks.title";
	private static final String PROPERTY_PAGE_TITLE_MANAGE_SET = "sponsoredlinks.manage_set.title";
	private static final String PROPERTY_PAGE_TITLE_CREATE_SET = "sponsoredlinks.create_set.title";
    
    //Markers
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS = "permission_manage_advanced_parameters";
    private static final String MARK_PERMISSION_CREATE_SET = "permission_create_set";
    private static final String MARK_PERMISSION_MODIFY_SET = "permission_modify_set";
    private static final String MARK_PERMISSION_DELETE_SET = "permission_delete_set";
    private static final String MARK_PERMISSION_CREATE_TOPIC = "permission_create_topic";
    private static final String MARK_PERMISSION_MODIFY_TOPIC = "permission_modify_topic";
    private static final String MARK_PERMISSION_DELETE_TOPIC = "permission_delete_topic";
    private static final String MARK_SET_LIST = "set_list";
    private static final String MARK_TOPIC_LIST = "topic_list";
    private static final String MARK_TEMPLATE_LINK_LIST = "template_link_list";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_PAGINATOR = "paginator";
    
    // other constants
    private static final String EMPTY_STRING = "";
     
    //session fields
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_ITEM_PER_PAGE, 50 );
    private String _strCurrentPageIndexSet;
    private int _nItemsPerPageSet;
    private String _strCurrentPageIndexTopic;
    private int _nItemsPerPageTopic;
    
///////////////////////////////////////////////////////////////////////////////////////////////////
/// Modèle de données
    //TODO: Remplacer les donnees brutes par le modele dynamique
    private static List<SponsoredLinkGroup> listGroup;
    private static List<SponsoredLinkSet> listSet;
    private static List<SponsoredLinkTemplate> listTemplate;
    
    // Initialisation du modèle
    public void init( HttpServletRequest request, String strRight )
	    throws AccessDeniedException
	{
	    super.init( request, strRight );
	    listGroup = new ArrayList<SponsoredLinkGroup>(  );
	    listSet = new ArrayList<SponsoredLinkSet>(  );
	    listTemplate = new ArrayList<SponsoredLinkTemplate>(  );
	    
	    listTemplate.add( new SponsoredLinkTemplate( listTemplate.size(), "fiche élu", "type_lien@fiche.elu" ) );
	    listTemplate.add( new SponsoredLinkTemplate( listTemplate.size(), "document administratif", "type_doc@mairie.fr" ) );
	    
	    SponsoredLinkSet set = new SponsoredLinkSet( "test_invalide", 0 );
	    set.addSponsoredLink( "elu@mairie.fr" );
	    set.addSponsoredLink( "doc.marie.fr/doc1");
	    listSet.add( set );
	    
	    set = new SponsoredLinkSet( "test_valide", 1 );
	    set.addSponsoredLink( "elu2@mairie.fr" );
	    set.addSponsoredLink( "doc.mairie.fr/doc3" );
	    listSet.add( set );
	    
	    listGroup.add( new SponsoredLinkGroup( "thème1", "tag1;tag2;tag3" ) );
	    listGroup.add( new SponsoredLinkGroup( "thème2", "tag4;tag5;tag6;tag7" ) );
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
    	HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_SPONSOREDLINKS, getLocale(  ) );

        return getAdminPage( template.getHtml(  ) );

    }

///////////////////////////////////////////////////////////////////////////////////////////////////
/// Set management
    public String getManageSet( HttpServletRequest request )
    {   	
    	setPageTitleProperty( PROPERTY_PAGE_TITLE_MANAGE_SET );
    	
    	_strCurrentPageIndexSet = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX,
                _strCurrentPageIndexSet );
        _nItemsPerPageSet = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE,
                _nItemsPerPageSet, _nDefaultItemsPerPage );
    	
        Map<String, Object> model = new HashMap<String, Object>(  );
        
        LocalizedPaginator paginator = new LocalizedPaginator( listSet, _nItemsPerPageSet, getHomeUrl( request ),
                LocalizedPaginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndexSet, getLocale(  ) );
        
        boolean bPermissionAdvancedParameter = RBACService.isAuthorized( SponsoredLinkTemplate.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksTemplateResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, getUser(  ) );
        boolean bPermissionCreateSet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksSetResourceIdService.PERMISSION_CREATE_SET, getUser(  ) );
        boolean bPermissionModifySet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksSetResourceIdService.PERMISSION_MODIFY_SET, getUser(  ) );
        boolean bPermissionDeleteSet = RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksSetResourceIdService.PERMISSION_DELETE_SET, getUser(  ) );
        boolean bPermissionCreateTopic = RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksTopicResourceIdService.PERMISSION_CREATE_TOPIC, getUser(  ) );
        boolean bPermissionModifyTopic = RBACService.isAuthorized( SponsoredLinkGroup.RESOURCE_TYPE, 
        		RBAC.WILDCARD_RESOURCES_ID,	SponsoredLinksTopicResourceIdService.PERMISSION_MODIFY_TOPIC, getUser(  ) );
       
        model.put( MARK_LOCALE, request.getLocale(  ) );
        model.put( MARK_PERMISSION_MANAGE_ADVANCED_PARAMETERS, bPermissionAdvancedParameter );
        model.put( MARK_PERMISSION_CREATE_SET, bPermissionCreateSet );
        model.put( MARK_PERMISSION_MODIFY_SET, bPermissionModifySet );
        model.put( MARK_PERMISSION_DELETE_SET, bPermissionDeleteSet );
        model.put( MARK_PERMISSION_CREATE_TOPIC, bPermissionCreateTopic );
        model.put( MARK_PERMISSION_MODIFY_TOPIC, bPermissionModifyTopic );
        
        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPageSet );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_SET_LIST, paginator.getPageItems(  ) );
        
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_SET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }
    
    public String getCreateSet( HttpServletRequest request )
    {
    	if ( !RBACService.isAuthorized( SponsoredLinkSet.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID, 
    			SponsoredLinksSetResourceIdService.PERMISSION_CREATE_SET, getUser(  ) ) )
    	{
    		return getManageSet( request );
    	}
    	
    	setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE_SET );
    	
    	Map<String, Object> model = new HashMap<String, Object>(  );
    	
    	model.put( MARK_TEMPLATE_LINK_LIST, listTemplate );
    	//listGroup.add( SponsoredLinkGroup.UNDEFINED_TOPIC );
    	model.put( MARK_TOPIC_LIST, listGroup );
    	
    	HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_SET, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    	
    }

}


