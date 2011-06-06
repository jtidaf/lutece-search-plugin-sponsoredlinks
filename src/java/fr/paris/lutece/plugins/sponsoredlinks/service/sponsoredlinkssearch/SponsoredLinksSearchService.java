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
package fr.paris.lutece.plugins.sponsoredlinks.service.sponsoredlinkssearch;

import fr.paris.lutece.plugins.sponsoredlinks.service.search.SponsoredLinksIndexer;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.apache.lucene.document.DateTools;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * class SponsoredLinksIncludeService
 */
public class SponsoredLinksSearchService
{
    private static final String REGEX_UID = "^([\\d]+)_" + SponsoredLinksIndexer.SET_SHORT_NAME + ":([\\d]+)_" +
        SponsoredLinksIndexer.LINK_SHORT_NAME + "$";
    private static final String REGEX_GROUP_ID = "^([\\d]+)_" + SponsoredLinksIndexer.GROUP_SHORT_NAME + "$";
    private static final Pattern _patternUID = Pattern.compile( REGEX_UID );
    private static final Pattern _patternGroupId = Pattern.compile( REGEX_GROUP_ID );

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
     * Return every search results.
     * @param strQuery The search query
     * @param plugin The plugin
     * @return Results as a list of {@link SponsoredLinksSearchResult}
     */
    public List<SponsoredLinksSearchResult> getSearchResults( String strQuery, Plugin plugin )
    {
        List<SponsoredLinksSearchResult> listResult = new ArrayList<SponsoredLinksSearchResult>(  );
        SponsoredLinksSearchEngine engine = (SponsoredLinksSearchEngine) SpringContextService.getPluginBean( plugin.getName(  ),
                SponsoredLinksSearchEngine.SPRING_BEAN_ID );

        for ( SponsoredLinksSearchItem item : engine.getSearchResults( strQuery ) )
        {
            SponsoredLinksSearchResult result = new SponsoredLinksSearchResult(  );
            result.setId( item.getId(  ) );

            try
            {
                result.setDate( DateTools.stringToDate( item.getDate(  ) ) );
            }
            catch ( ParseException e )
            {
                AppLogService.error( "Bad Date Format for indexed item \"" + item.getTitle(  ) + "\" : " +
                    e.getMessage(  ) );
            }

            result.setUrl( item.getUrl(  ) );
            result.setTitle( item.getTitle(  ) );
            result.setSummary( item.getSummary(  ) );
            result.setType( item.getType(  ) );

            //Sponsored links specific data
            result.setTargetType( item.getTargetType(  ) );

            Matcher matcher = _patternUID.matcher( item.getId(  ) );

            if ( matcher.matches(  ) )
            {
                result.setSetId( Integer.parseInt( matcher.group( 1 ) ) );
                result.setLinkOrder( Integer.parseInt( matcher.group( 2 ) ) );
            }

            matcher = _patternGroupId.matcher( item.getGroupId(  ) );

            if ( matcher.matches(  ) )
            {
                result.setGroupId( Integer.parseInt( matcher.group( 1 ) ) );
            }

            listResult.add( result );
        }

        return listResult;
    }
}
