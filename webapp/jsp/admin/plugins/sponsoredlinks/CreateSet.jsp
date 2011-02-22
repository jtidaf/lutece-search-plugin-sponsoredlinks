<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="sponsoredlinks" scope="session" class="fr.paris.lutece.plugins.sponsoredlinks.web.SponsoredLinksJspBean" />

<% sponsoredlinks.init( request, sponsoredlinks.RIGHT_MANAGE_SPONSOREDLINKS ); %>
<%= sponsoredlinks.getCreateSet( request ) %>

<%@ include file="../../AdminFooter.jsp" %>