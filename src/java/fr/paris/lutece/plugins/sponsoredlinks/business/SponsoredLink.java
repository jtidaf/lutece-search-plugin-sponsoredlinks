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

/**
 * 
 * class SponsoredLink.
 * Note: this class has a natural ordering that is inconsistent with equals.
 * See {@link #compareTo(SponsoredLink) compareTo}
 *
 */
public class SponsoredLink implements java.lang.Comparable<SponsoredLink>
{
	private int _nOrder;
	private String _strUrl;
	
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
	 * @param order The order in the set
	 */
	public void setOrder( int order )
	{
		_nOrder = order;
	}

	/**
	 * 
	 * @return the url of the link
	 */
	public String getUrl(  )
	{
		return _strUrl;
	}
	
	/**
	 * Sets the url of the link
	 * @param url The url
	 */
	public void setUrl( String url )
	{
		this._strUrl = url;
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
	    	if( otherOrder == _nOrder )
	    	{
	    		return 0;
	    	} 
		    else
		    {
		    	return 1;
		    }
	    }
	    	
	}
		
}