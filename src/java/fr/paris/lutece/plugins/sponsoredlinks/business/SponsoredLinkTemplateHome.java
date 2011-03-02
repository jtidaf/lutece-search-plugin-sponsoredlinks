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
package fr.paris.lutece.plugins.sponsoredlinks.business;

import java.util.Collection;

import fr.paris.lutece.plugins.sponsoredlinks.service.SponsoredLinksPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * 
 * This class provides instances management methods (create, find, ...) for SponsoredLinkTemplate objects
 *
 */
public final class SponsoredLinkTemplateHome
{
	//Static variable pointed at the DAO instance
	private static ISponsoredLinkTemplateDAO _dao = 
		(ISponsoredLinkTemplateDAO) SpringContextService.getPluginBean( 
				SponsoredLinksPlugin.PLUGIN_NAME,
				ISponsoredLinkTemplateDAO.SPRING_BEAN_ID );

	/**
	 * Private constructor - this class need not be instantiated
	 */
	private SponsoredLinkTemplateHome(  )
	{
	}
	
	/**
     * Creation of an instance of SponsoredLinkTemplate
     *  
     * @param template The instance of SponsoredLinkTemplate which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkTemplate which has been created with its primary key
     */
    public static SponsoredLinkTemplate create( SponsoredLinkTemplate template, Plugin plugin )
    {
    	_dao.insert( template, plugin );
    	
    	//TODO/ Put a new null SponsoredLink for every set
    	return template;
    }
    
    /**
     * Update of the specified SponsoredLinkTemplate
     * 
     * @param template The instance of SponsoredLinkTemplate which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of SponsoredLinkTemplate which has been updated
     */
    public static SponsoredLinkTemplate update( SponsoredLinkTemplate template, Plugin plugin )
    {
    	//TODO: Check whether the resource type have changed and update link sets consequently
    	    	
    	_dao.store( template, plugin );
    	
    	return template;
    }
    
    /**
     * Updates a SponsoredLinkTemplate to a new order and shifts in-between templates consequently
     * 
     * @param template The template to update order
     * @param nNewOrder the new order for the template
     * @param plugin the Plugin object
     * @return the template updated to the new order
     * @throws IndexOutOfBoundsException if  the new order parameter is less than 1 or greater than the current max order
     */
    public static SponsoredLinkTemplate updateOrder( SponsoredLinkTemplate template, int nNewOrder, Plugin plugin )
    	throws IndexOutOfBoundsException
    {
    	int nMaxOrder = _dao.newPrimaryKey( plugin ) -1 ;
    	int nOldOrder = template.getOrder(  );
    	if( ( nNewOrder < 1 ) || ( nNewOrder > nMaxOrder ) )
    	{
    		throw new IndexOutOfBoundsException(  );
    	}
    	
    	if( nNewOrder == nOldOrder )
    	{
    		return template;
    	}
    	
    	for( SponsoredLinkTemplate currentTemplate : findAll( plugin ) )
    	{
    		int nCurrentOrder = currentTemplate.getOrder(  );
	    	if( ( nOldOrder > nNewOrder ) && 
	    			( nCurrentOrder < nOldOrder ) && ( nCurrentOrder >= nNewOrder ) )
	    	{
	    		currentTemplate.setOrder( nCurrentOrder + 1 );
	    		_dao.store( currentTemplate, plugin );
	    	}
	    	else if( ( nOldOrder < nNewOrder ) &&
	    			( nCurrentOrder > nOldOrder ) && ( nCurrentOrder <= nNewOrder ) )
	    	{
	    		currentTemplate.setOrder( nCurrentOrder -1 );
	    		_dao.store( currentTemplate, plugin );
	    	}
    	}
    	
    	template.setOrder( nNewOrder );
    	_dao.store( template, plugin );
    	
    	//TODO: update link sets consequently
    	
    	return template;
    }
    
    /**
     * Removes the specified SponsoredLinkTemplate (optional operation).
     * Shifts any subsequent template (substract one from their order)
     * 
     * @param template The SponsoredLinkTemplate object to remove
     * @param plugin The Plugin object
     */
    public static void remove( SponsoredLinkTemplate template, Plugin plugin )
    {  	
    	int nMaxOrder = _dao.newPrimaryKey( plugin ) - 1;
    	template = updateOrder( template, nMaxOrder, plugin );
    	
    	//TODO: remove matching link in every set
    	
    	_dao.delete( template, plugin );
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of the SponsoredLinkTemplate whose identifier/order is specified in parameter
     * 
     * @param nKey The primary key of the template
     * @param plugin The Plugin object
     * @return An instance of the found template
     */
    public static SponsoredLinkTemplate findByPrimaryKey( int nKey, Plugin plugin )
    {
    	SponsoredLinkTemplate template = _dao.load( nKey, plugin );

    	return template;
    }
    
    /**
     * Returns all stored SponsoredLinkTemplate objects
     * 
     * @param plugin The Plugin object
     * @return A collection of all templates
     */
    public static Collection<SponsoredLinkTemplate> findAll( Plugin plugin )
    {
    	return _dao.selectAll( plugin );
    }
    
    
    /**
     * Returns templates that are linked to the specified resource type
     * @param strResourceType The type of the linked resource
     * @param plugin The Plugin object
     * @return A collection of found SponsoredLinkTemplate objects
     */
    public static Collection<SponsoredLinkTemplate> findByResourceType( String strResourceType, Plugin plugin )
    {
    	return _dao.selectByResourceType( strResourceType, plugin );
    }
}
