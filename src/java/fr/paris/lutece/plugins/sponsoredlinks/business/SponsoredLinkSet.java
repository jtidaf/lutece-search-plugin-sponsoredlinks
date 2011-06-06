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

import java.util.Collections;
import java.util.List;


/**
 *
 * class SponsoredLinkSet
 *
 */
public class SponsoredLinkSet
{
    /** Unique name of the resource type implemented by this class */
    public static final String RESOURCE_TYPE = "SPONSOREDLINKS_SET_TYPE";
    private int _nId;
    private String _strTitle;
    private List<SponsoredLink> _sponsoredLinkList;
    private int _nGroupId;

    /**
     *
     * @return the id of this set
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     * Sets the id of this set
     * @param id The id
     */
    public void setId( int id )
    {
        _nId = id;
    }

    /**
     *
     * @return the title of this set
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * Sets the title of this set
     * @param title The title
     */
    public void setTitle( String title )
    {
        _strTitle = title;
    }

    /**
     *
     * @return the list of links in this set
     */
    public List<SponsoredLink> getSponsoredLinkList(  )
    {
        return _sponsoredLinkList;
    }

    /**
     * Sets the list of links in this set
     * @param links A Collection of SponsoredLink
     */
    public void setSponsoredLinkList( List<SponsoredLink> links )
    {
        _sponsoredLinkList = links;
        Collections.sort( _sponsoredLinkList );
    }

    /**
     *
     * @return the id of the associated group of tags
     */
    public int getGroupId(  )
    {
        return _nGroupId;
    }

    /**
     * Sets the id of the associated group of tags
     * @param groupId The id of a SponsoredLinkGroup
     */
    public void setGroupId( int groupId )
    {
        _nGroupId = groupId;
    }
}
