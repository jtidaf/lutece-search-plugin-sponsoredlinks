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
package fr.paris.lutece.plugins.sponsoredlinks.service;

import fr.paris.lutece.plugins.sponsoredlinks.service.sponsoredlinkssearch.SponsoredLinksSearchResult;
import fr.paris.lutece.plugins.sponsoredlinks.service.sponsoredlinkssearch.SponsoredLinksSearchService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.search.ISponsoredLinksService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class SponsoredLinksService implements ISponsoredLinksService
{
    private static final String EMPTY_STRING = "";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_RESULT_SET = "result_set";
    private static final String TEMPLATE_SPONSOREDLINKS_SET = "sponsoredlinks.template.sponsoredlinksSetTemplate";

    /**
     * {@inheritDoc}
     */
    public String getHtmlCode( String strRequest, Locale locale )
    {
        SortedMap<Integer, SponsoredLinksSearchResult> mapResult = new TreeMap<Integer, SponsoredLinksSearchResult>(  );

        //Search of the results
        for ( SponsoredLinksSearchResult result : SponsoredLinksSearchService.getInstance(  )
                                                                             .getSearchResults( strRequest,
                PluginService.getPlugin( SponsoredLinksPlugin.PLUGIN_NAME ) ) )
        {
            //Here the resulting set is filled with first result according to 
            //their order. That is the result could be a mix of sets if the top 
            // sets in terms of result is not have missing link(s)
            if ( !mapResult.containsKey( result.getLinkOrder(  ) ) )
            {
                mapResult.put( result.getLinkOrder(  ), result );
            }
        }

        if ( mapResult.isEmpty(  ) )
        {
            return EMPTY_STRING;
        }

        List<SponsoredLinksSearchResult> listResult = new ArrayList<SponsoredLinksSearchResult>( mapResult.size(  ) );

        //convert the map to a list
        for ( Integer i : mapResult.keySet(  ) )
        {
            listResult.add( mapResult.get( i ) );
        }

        Map<String, Object> model = new HashMap<String, Object>(  );

        model.put( MARK_RESULT_SET, listResult );
        model.put( MARK_LOCALE, locale );

        String strSponsoredLinksSetTemplate = AppPropertiesService.getProperty( TEMPLATE_SPONSOREDLINKS_SET );
        HtmlTemplate sponsoredLinksSetTemplate = AppTemplateService.getTemplate( strSponsoredLinksSetTemplate, locale,
                model );

        return sponsoredLinksSetTemplate.getHtml(  );
    }
}
