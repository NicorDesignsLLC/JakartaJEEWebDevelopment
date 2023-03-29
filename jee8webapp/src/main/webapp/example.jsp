<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%-- page directive --%>
<%!
    //A declaration, will be jspservice method scope 
    private final int zero = 0;

    protected String mariachi = "Mexican Restaurant Band";

    //The assignment below is not declarative and is a syntax error if uncommented
    //mariachi = "testting testing 1 2 3";

    public long addSix(long number)
    {
        return number + 6L;
    }

    public class ThisInnerClass
    {

    }
    
    ThisInnerClass instanceVariable = new ThisInnerClass();

    //WeirdClassWithinMethod is in method scope, so the declaration below is
    // a syntax error if uncommented
    
    //WeirdClassWithinMethod bad = new WeirdClassWithinMethod();
%>
<%
    // A scriptlet is java code copied to the jspService method
    class WeirdClassWithinMethod
    {

    }
    WeirdClassWithinMethod weirdClass = new WeirdClassWithinMethod();
    ThisInnerClass innerClass = new ThisInnerClass();
    int seven;
    seven = 7;
%>
<%= "Hello, World" %><br />
<!-- Expressions with values displayed in the HTML -->
<%= addSix(12L) %>
