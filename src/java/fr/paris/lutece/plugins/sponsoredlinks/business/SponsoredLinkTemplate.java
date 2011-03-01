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

/**
 * 
 * class SponsoredLinkTemplate
 *
 */
public class SponsoredLinkTemplate
{
	/** Unique name of the resource type implemented by this class */
	public static final String RESOURCE_TYPE = "SPONSOREDLINKS_TEMPLATE_TYPE";
	
	private int _nOrder;
	private String _strDescription;
	private String _strLinkedResourceType;
	
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
	 * @return the name of the type of resource to link
	 */
	public String getLinkedResourceType(  )
	{
		return _strLinkedResourceType;
	}

	/**
	 * Sets the type of resource to link
	 * @param linkedResourceType The name of the type of resource
	 */
	public void setLinkedResourceType( String linkedResourceType )
	{
		_strLinkedResourceType = linkedResourceType;
	}

}
