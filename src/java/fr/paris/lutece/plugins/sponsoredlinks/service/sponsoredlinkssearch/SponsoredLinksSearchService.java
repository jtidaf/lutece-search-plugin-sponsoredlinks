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

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.sponsoredlinks.business.SponsoredLink;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;


/**
 * class SponsoredLinksSearchService
 */
public class SponsoredLinksSearchService
{
    private static final String BEAN_SEARCH_ENGINE = "sponsoredlinks.sponsoredlinksSearchEngine";
    private static final String REGEX_ID = "^[\\d]+$";

    // Constants corresponding to the variables defined in the lutece.properties file
    private static SponsoredLinksSearchService _singleton;

    /**
     * @return the instance of the service
     */
    public static SponsoredLinksSearchService getInstance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new SponsoredLinksSearchService(  );
        }

        return _singleton;
    }

    /**
     * Return search results
     * @param strQuery The search query
     * @param plugin The plugin
     * @return Results as a collection of {@link DiggSubmit}
     */
    public List<SponsoredLink> getSearchResults( String strQuery, Plugin plugin )
    {
        List<SponsoredLink> listDiggSubmitResult = new ArrayList<SponsoredLink>(  );
        SponsoredLinksSearchEngine engine = (SponsoredLinksSearchEngine) SpringContextService.getPluginBean( plugin.getName(  ),
                BEAN_SEARCH_ENGINE );
        /*
        List<Integer> diggSubmitListId = DiggSubmitHome.getDiggSubmitListId( filter, plugin );
        List<SponsoredLinksSearchItem> listSearchesults = engine.getSearchResults( strQuery );
        DiggSubmit diggSubmit;

        for ( Integer nDiggSubmitId : diggSubmitListId )
        {
            for ( SponsoredLinksSearchItem searchResult : listSearchesults )
            {
                if ( ( searchResult.getType(  ) != null ) && ( searchResult.getId(  ) != null ) &&
                        searchResult.getType(  ).equals( DigglikeIndexer.INDEX_TYPE_DIGG ) &&
                        searchResult.getIdDiggSubmit(  ).matches( REGEX_ID ) &&
                        ( Integer.parseInt( searchResult.getIdDiggSubmit(  ) ) == nDiggSubmitId ) )
                {
                    diggSubmit = DiggSubmitHome.findByPrimaryKey( nDiggSubmitId, plugin );

                    if ( diggSubmit != null )
                    {
                        listDiggSubmitResult.add( diggSubmit );
                    }
                }
            }
        }
        */

        return listDiggSubmitResult;
    }
}
