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

import fr.paris.lutece.plugins.sponsoredlinks.service.search.SponsoredLinksIndexer;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.search.LuceneSearchEngine;
import fr.paris.lutece.portal.service.search.SearchItem;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;


/**
 * class LuceneSearchEngine
 */
public class SponsoredLinksLuceneSearchEngine implements SponsoredLinksSearchEngine
{
    /**
     * {@inheritDoc}
     */
    public List<SponsoredLinksSearchItem> getSearchResults( String strQuery )
    {
        List<SponsoredLinksSearchItem> listResults = new ArrayList<SponsoredLinksSearchItem>( );
        IndexSearcher searcher = null;
        Query filter = null;
        Query query = null;

        try
        {
            IndexReader ir = DirectoryReader.open( IndexationService.getDirectoryIndex( ) );
            searcher = new IndexSearcher( ir );

            //filter on content
            if ( StringUtils.isNotBlank( strQuery ) )
            {
                QueryParser parser = new QueryParser(
                        SearchItem.FIELD_CONTENTS, IndexationService.getAnalyser( ) );
                query = parser.parse( ( strQuery != null ) ? strQuery : "" );
            }

            //filter on sponsoredlink type
            Query[] filters = null;

            Query queryTypeSponsoredLink = new TermQuery( new Term( SearchItem.FIELD_TYPE,
                    SponsoredLinksIndexer.INDEX_TYPE_SPONSOREDLINKS ) );
            filters = new Query[1];

            filters[filters.length - 1] = queryTypeSponsoredLink;

            BooleanQuery.Builder bQueryBuilder = new BooleanQuery.Builder( );
            bQueryBuilder.add( query, BooleanClause.Occur.MUST );
            bQueryBuilder.add( filter, BooleanClause.Occur.FILTER );

            TopDocs topDocs = searcher.search( bQueryBuilder.build( ), LuceneSearchEngine.MAX_RESPONSES );

            ScoreDoc[] hits = topDocs.scoreDocs;

            for ( int i = 0; i < hits.length; i++ )
            {
                int docId = hits[i].doc;
                Document document = searcher.doc( docId );
                SponsoredLinksSearchItem si = new SponsoredLinksSearchItem( document );
                listResults.add( si );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( e.getMessage( ), e );
        }

        return listResults;
    }
}
