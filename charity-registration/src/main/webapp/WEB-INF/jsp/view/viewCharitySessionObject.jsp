<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
    <title>View Charity Session Object</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"/>
    <!-- Custom CSS file for the color scheme -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom-style.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	</head>
    
    <body>
        <h2>View Charity Session Object</h2>
        <a href="<c:url value="/charitySession" />">Charity Category List</a><br /><br />
        <a href="<c:url value="/charitySession?action=emptyCharitySessionObject" />">Empty Charity Session Object</a><br /><br />
        <%
            @SuppressWarnings("unchecked")
            Map<Integer, String> categories =
                    (Map<Integer, String>)request.getAttribute("categories");
            
        	@SuppressWarnings("unchecked")
            Map<Integer, Integer> categoryHolder =
                    (Map<Integer, Integer>)session.getAttribute("categoryHolder");

            if(categoryHolder == null || categoryHolder.size() == 0)
                out.println("Your category holder is empty.");
            else
            {
                for(int id : categoryHolder.keySet())
                {
                    out.println(categories.get(id) + " (qty: " + categoryHolder.get(id) +
                            ")<br />");
                }
            }
        %>
    </body>
</html>