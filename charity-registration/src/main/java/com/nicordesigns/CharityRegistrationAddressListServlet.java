package com.nicordesigns;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

@WebServlet(
        name = "charityRegistrationAddressListServlet",
        urlPatterns = "/charityRegistrationAddressListServlet"
)
public class CharityRegistrationAddressListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final SortedSet<Address> addresses = new TreeSet<>();

    static {
        try {
            addresses.add(new Address("1", "Zisize Care Centre", "+27 1 83 3160369", new URL("https://www.facebook.com/ZISIZE"),
                    Calendar.getInstance().getTime(), //Old Date prior to Java 8
                    Instant.parse("2022-06-05T21:22:23Z")
            ));
            addresses.add(new Address("2", "UMCA", "+27 21 887 0212", new URL("http://vcsv.co.za/"),
                    Date.from(Instant.now()), Instant.parse("2022-06-05T15:31:17Z")
            ));
            addresses.add(new Address("3", "Diaconia", "+27 21 957 7113", new URL("http://www.diaconia.co.za/"),
                    Calendar.getInstance().getTime(), //Old Date prior to Java 8
                    Instant.parse("2022-06-05T01:45:01Z")
            ));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Register resource mapping for CSS files
        String resourcesLocation = "/resources/";
        String resourcesMapping = "/resources/*";

        javax.servlet.ServletContext servletContext = getServletContext();
        javax.servlet.ServletRegistration defaultRegistration = servletContext.getServletRegistration("default");
        defaultRegistration.addMapping(resourcesMapping);

        Locale locale = request.getLocale();
        String country = locale.getCountry();
        String language = locale.getLanguage();

        language = request.getParameter("language");

        System.out.println("locale = " + locale);
        System.out.println("language = " + language);
        System.out.println("country = " + country);

        if ("afrikaans".equalsIgnoreCase(language)) {
            locale = new Locale("af", "ZA");
        } else if (country.equalsIgnoreCase("ZA")) {
            locale = new Locale("en", "ZA");
        }

        Config.set(request, Config.FMT_LOCALE, locale);

        if (request.getParameter("empty") != null)
            request.setAttribute("addresses", Collections.<Address>emptySet());
        else
            request.setAttribute("addresses", addresses);

        request.getRequestDispatcher("/WEB-INF/jsp/view/listRegistrationAddresses.jsp")
                .forward(request, response);
    }
}
