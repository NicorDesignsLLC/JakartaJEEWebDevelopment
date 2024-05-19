<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Charity Registration :: <c:out value="${fn:trim(htmlTitle)}" /></title>
        
        <!-- Use Bootstrap 2.3.1 -->
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />
        <!-- Custom CSS file for the color scheme -->
        <link rel="stylesheet" href="/resources/css/main.css"/>
        
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
        
        <script type="text/javascript">
            var postInvisibleForm = function(url, fields) {
                var form = $('<form id="mapForm" method="post"></form>')
                        .attr({ action: url, style: 'display: none;' });
                for(var key in fields) {
                    if(fields.hasOwnProperty(key))
                        form.append($('<input type="hidden">').attr({
                            name: key, value: fields[key]
                        }));
                }
                $('body').append(form);
                form.submit();
            };
            var newChat = function() {
                postInvisibleForm('<c:url value="/chat/new" />', { });
            };
        </script>
        <jsp:invoke fragment="headContent" />
    </head>
    <body>
        <div class="container">
            <h1>Charity Registration Web Application</h1>
            <div class="row">
                <div class="span3">
                    <jsp:invoke fragment="navigationContent" />
                </div>
                <div class="span9">
                    <h2><c:out value="${fn:trim(bodyTitle)}" /></h2>
                    <jsp:doBody />
                </div>
            </div>
        </div>
    </body>
</html>
