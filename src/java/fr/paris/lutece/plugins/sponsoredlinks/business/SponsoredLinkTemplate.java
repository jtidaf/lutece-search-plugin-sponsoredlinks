/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.portal.service.insert.InsertService;
import fr.paris.lutece.portal.service.insert.InsertServiceManager;


/**
 *
 * class SponsoredLinkTemplate
 *
 */
public class SponsoredLinkTemplate
{
    /** Unique name of the resource type implemented by this class */
    public static final String RESOURCE_TYPE = "SPONSOREDLINKS_TEMPLATE_TYPE";
    
    private static final String EMPTY_STRING = "";
    
    private int _nOrder;
    private String _strDescription;
    private InsertService _insertService;
    private String _strSubCategory;
    
    

    /**
     * The subcategory that might be used to filter resources.
     * @return The subcategory that might be used to filter resources, empty string otherwise.
     */
    public String getSubCategory(  )
	{
		return _strSubCategory == null ? EMPTY_STRING : _strSubCategory;
	}

    /**
     * The subcategory that might be used to filter resources.
     * @param strSubCategory The subcategory that might be used to filter resources, empty string otherwise.
     */
	public void setSubCategory( String strSubCategory )
	{
		this._strSubCategory = strSubCategory;
	}

	/**
     *
     * @return the order of the link in the set
     */
    public int getOrder(  )
    {
        return _nOrder;
    }

    /**
     * Sets the order of the link in the set
     * @param order The order
     */
    public void setOrder( int order )
    {
        _nOrder = order;
    }

    /**
     *
     * @return a description of the link
     */
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Sets a description of the link
     * @param description A description
     */
    public void setDescription( String description )
    {
        _strDescription = description;
    }

    /**
     *
     * @return the InsertService
     */
    public InsertService getInsertService(  )
    {
        return _insertService;
    }

    /**
     * Sets the InsertService of this template
     * @param insertService the InsertService to associate with this template
     */
    public void setInsertService( InsertService insertService )
    {
        _insertService = insertService;
    }

    /**
     * Sets the InsertService of this template from its identifier.
     * Sets null if there is no matching InsertService or if the InsertService
     * is not enable
     * @param strId the id of the InsertService to associate with this template
     * @return the InsertService if found and enabled, null otherwise
     */
    public InsertService setInsertService( String strId )
    {
        InsertService insertService = InsertServiceManager.getInsertService( strId );

        if ( ( insertService != null ) && insertService.isEnabled(  ) )
        {
            _insertService = insertService;
        }
        else
        {
            _insertService = null;
        }

        return _insertService;
    }
}
