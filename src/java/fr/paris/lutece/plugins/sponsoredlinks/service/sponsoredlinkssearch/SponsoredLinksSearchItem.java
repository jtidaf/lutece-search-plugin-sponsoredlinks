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
package fr.paris.lutece.plugins.sponsoredlinks.service.sponsoredlinkssearch;

import fr.paris.lutece.portal.service.search.SearchItem;

import org.apache.lucene.document.Document;


/**
 * SponsoredLinksSearchItem
 */
public class SponsoredLinksSearchItem extends SearchItem
{
	/** Name of the {@link org.apache.lucene.document.Field} that stores the 
	 * actual type of the resource pointed by a sponsoredlink
	 */
    public static final String FIELD_TARGET_TYPE = "target_type";
    
    /** Name of the {@link org.apache.lucene.document.Field} that stores the id 
     * of the group of tag
     */
    public static final String FIELD_GROUP_UID = "group_id";

    // Variables declarations
    private String _strTargetType;
    private String _strGroupId;


    /**
     * @param document a document
     */
    public SponsoredLinksSearchItem( Document document )
    {
        super( document );
        _strTargetType = document.get( FIELD_TARGET_TYPE ) ;
        _strGroupId = document.get( FIELD_GROUP_UID );
    }

    /**
     * @return the type of the targeted resource
     */
    public String getTargetType(  )
    {
        return  _strTargetType;
    }

    /**
     * @param strTargetType the type of the targeted resource
     */
    public void setTargetType( String strTargetType )
    {
    	_strTargetType = strTargetType;
    }
    
    /**
     * @return the id of the indexed {@link 
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup 
     * <code>group of tags</code>}
     */
    public String getGroupId(  )
    {
    	return _strGroupId;
    }
    
    /**
     * @param strGroupId the id of the indexed {@link 
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup 
     * <code>group of tags</code>}
     */
    public void setGroupId( String strGroupId )
    {
    	_strGroupId = strGroupId;
    }

}
