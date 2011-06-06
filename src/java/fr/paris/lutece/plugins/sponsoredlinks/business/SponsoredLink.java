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

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * class SponsoredLink.
 * Note: this class has a natural ordering that is inconsistent with equals.
 * See {@link #compareTo(SponsoredLink) compareTo}
 *
 */
public class SponsoredLink implements java.lang.Comparable<SponsoredLink>
{
    // Number of the capturing group according to the regex
    /** Key for the href attribute in the link */
    public static final int HREF = 1;

    /** Key for the title attribute in the link */
    public static final int TITLE = 2;

    /** Key for the alt attribute in the link */
    public static final int ALT = 3;

    /** Key for the content in the link */
    public static final int CONTENT = 4;

    //Regex used to validate a link and capture data from it
    //^<a\b(?:href="([^>]*?)"|title="([^>]*?)"|alt="([^>]*?)"|[^>]*?)*>(.*?)</a>$
    private static final String SPONSORED_LINK_REGEX = "^<a\\b(?:" + //opening of <a> tag and start of a non-capturing group
        "href=\"([^>]*?)\"|" + //href attribute with capturing group #1
        "title=\"([^>]*?)\"|" + //title attribute with capturing group #2
        "alt=\"([^>]*?)\"|" + //alt attribute with capturing group #3
        "[^>]*?" + // any non-matching attribute
        ")*>" + // end of the non-capturing group and closing of the <a> tag
        "(.*?)" + // inner content with capturing group #4
        "</a>$"; // closing tag

    //Pre-compiled representation of the regex.
    private static final Pattern _pattern = Pattern.compile( SPONSORED_LINK_REGEX, Pattern.CASE_INSENSITIVE );
    private int _nOrder;
    private String _strLink;

    /**
     *
     * @return the order of the SponsoredLink in the set
     */
    public int getOrder(  )
    {
        return _nOrder;
    }

    /**
     * Sets the order of the SponsoredLink in the set
     * @param order The order in the set
     */
    public void setOrder( int order )
    {
        _nOrder = order;
    }

    /**
     *
     * @return the html link of this SponsoredLink
     */
    public String getLink(  )
    {
        return _strLink;
    }

    /**
     * Sets the html link of this SponsoredLink
     * @param link The url
     */
    public void setLink( String link )
    {
        _strLink = link;
    }

    /**
     * Defines the ordering of SponsoredLink object based on their {@link #getOrder() order}
     * @param otherLink the SponsoredLink to be compared
     * @return -1 if otherLink is greater, 0 if equal 1 otherwise
     */
    public int compareTo( SponsoredLink otherLink )
    {
        int otherOrder = otherLink.getOrder(  );

        if ( otherOrder > _nOrder )
        {
            return -1;
        }
        else
        {
            if ( otherOrder == _nOrder )
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
    }

    /**
     * Check if the link is a valid html link (as returned by an InsertService).
     * The link must at least contain non-empty href attribute and content.
     * Has to match the pattern <a (attr="[^>]*")*>.*</a>
     * @return true if the link is valid html
     */
    public boolean isValidLink(  )
    {
        if ( StringUtils.isBlank( _strLink ) )
        {
            return false;
        }

        Matcher matcher = _pattern.matcher( _strLink );

        if ( !matcher.matches(  ) )
        {
            return false;
        }

        String strHref = matcher.group( HREF );
        String strContent = matcher.group( CONTENT );

        if ( StringUtils.isBlank( strHref ) || StringUtils.isBlank( strContent ) )
        {
            return false;
        }

        return true;
    }

    /**
     * Parses the link to find the specified attribute or the content.
     * The link has to be valid html.
     * @param nAttr the key for the attribute. Has to be in the set
     *                 {ALT, HREF, TITLE, CONTENT}
     * @return the attribute/content if exists. Returns null if the link is null
     *  and if the key or the link is not valid
     */
    public String getLinkAttribute( int nAttr )
    {
        if ( StringUtils.isBlank( _strLink ) ||
                ( ( nAttr != HREF ) && ( nAttr != ALT ) && ( nAttr != TITLE ) && ( nAttr != CONTENT ) ) )
        {
            return null;
        }

        String strHrefAttr = null;
        Matcher matcher = _pattern.matcher( _strLink );

        if ( matcher.matches(  ) )
        {
            strHrefAttr = matcher.group( nAttr );
        }

        return strHrefAttr;
    }
}
