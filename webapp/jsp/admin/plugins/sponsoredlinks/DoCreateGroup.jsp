<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="sponsoredlinks" scope="session" class="fr.paris.lutece.plugins.sponsoredlinks.web.SponsoredLinksJspBean" />

<% 
   sponsoredlinks.init( request, sponsoredlinks.RIGHT_MANAGE_SPONSOREDLINKS );
   response.sendRedirect( sponsoredlinks.doCreateGroup( request ) );
%>