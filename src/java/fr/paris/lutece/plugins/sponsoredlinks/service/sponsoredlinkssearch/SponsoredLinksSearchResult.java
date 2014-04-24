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
package fr.paris.lutece.plugins.sponsoredlinks.service.sponsoredlinkssearch;

import fr.paris.lutece.portal.service.search.SearchResult;


/**
 * class SponsoredLinksSearchResult
 */
public class SponsoredLinksSearchResult extends SearchResult
{
    //Variables declarations
    private int _nSetId;
    private int _nLinkOrder;
    private int _nGroupId;
    private String _strTargetType;

    /**
     * @return the {@link
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet#getId()
     * <code>set id</code>}
     */
    public int getSetId(  )
    {
        return _nSetId;
    }

    /**
     * @param nSetId the {@link
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkSet#getId()
     * <code>set id</code>} to set
     */
    public void setSetId( int nSetId )
    {
        _nSetId = nSetId;
    }

    /**
     * @return the {@link
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLink#getOrder()
     * <code>link order</code>} in the set
     */
    public int getLinkOrder(  )
    {
        return _nLinkOrder;
    }

    /**
     * @param nLinkOrder the {@link
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLink#getOrder()
     * <code>link order</code>} to set
     */
    public void setLinkOrder( int nLinkOrder )
    {
        _nLinkOrder = nLinkOrder;
    }

    /**
     * @return the {@link
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup#getId()
     * <code>group id</code>}
     */
    public int getGroupId(  )
    {
        return _nGroupId;
    }

    /**
     * @param nGroupId the {@link
     * fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLinkGroup#getId()
     * <code>group id</code>} to set
     */
    public void setGroupId( int nGroupId )
    {
        _nGroupId = nGroupId;
    }

    /**
     * @return the type of the targeted resource
     */
    public String getTargetType(  )
    {
        return _strTargetType;
    }

    /**
     * @param strTargetType the type of the targeted resource to set
     */
    public void setTargetType( String strTargetType )
    {
        _strTargetType = strTargetType;
    }
}
