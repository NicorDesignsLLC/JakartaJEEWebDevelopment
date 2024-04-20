# Spring Framework 5 Internationalization in Jakarta EE 8

## 1. Why would you want to use Spring i18n in a Jakarta EE 8 web app?
- **Spring i18n:**
    - **Unified Approach**: Spring offers a unified approach to internationalization across various layers of the application, providing consistent handling of messages, formatting, and locale management.
    - **Advanced Features**: Spring's i18n offers additional features such as message formatting, including `MessageFormat` support for dynamic message arguments.
    - **Ease of Use**: Spring's abstractions and APIs (such as `MessageSource` and `LocaleResolver`) simplify internationalization tasks and enhance the ease of use.
    - **Integration**: If you're already using Spring for other aspects of your application, adding Spring's i18n support can provide a seamless and integrated experience.

- **Jakarta EE 8 i18n (JSTL and JSP):**
    - **Standard Features**: Jakarta EE 8 provides standard i18n features using JSTL and JSP, including `fmt` tags to handle localization and formatting.
    - **Straightforward**: For straightforward applications, the standard i18n features offered by Jakarta EE 8 may suffice, as they are built-in and easy to use.
    - **Limited Advanced Features**: Compared to Spring, Jakarta EE 8’s i18n capabilities may be less flexible and powerful, particularly when handling complex formatting or message sources.

## 2. How to use Spring i8n APIs in a Jakarta EE 8 web app?
- **Spring i18n:**
    - **Add Spring Dependencies**: Include Spring's i18n and context dependencies in your Jakarta EE project.
    - **Configure `MessageSource`**: Define a `ResourceBundleMessageSource` bean in your Spring configuration to handle messages from resource bundles.
    - **Configure `LocaleResolver`**: Define a `LocaleResolver` bean to handle locale determination (e.g., `SessionLocaleResolver` for session-based locale tracking).
    - **Accessing Messages**: Use Spring's `MessageSource` to retrieve localized messages in your controllers or services.

- **Jakarta EE 8 i18n (JSTL and JSP):**
    - Use JSTL's `fmt` tags in your JSPs to handle localization and formatting.
    - Access resource bundles directly in JSPs using JSTL tags like `<fmt:message>`.

## 3. How to configure Spring i8n in your Jakarta EE 8 web app?
- **Spring i18n:**
    - **Configure Message Source**: Define a `ResourceBundleMessageSource` bean in your Spring configuration, specifying the base name of the resource bundles.
    - **Configure Locale Resolver**: Add a `LocaleResolver` bean, such as `SessionLocaleResolver` or `CookieLocaleResolver`, to manage the current locale.
    - **Add Locale Change Interceptor**: You can add a `LocaleChangeInterceptor` to allow users to change their locale based on a request parameter.

- **Jakarta EE 8 i18n (JSTL and JSP):**
    - Set the default locale in the web application context (`web.xml` or a similar configuration file).
    - Place resource bundles in appropriate locations (e.g., `src/main/resources`).
    - Use JSTL `fmt` tags in JSPs to format dates, numbers, and messages based on the locale.

## 4. How to add Spring i18n to an existing Jakarta EE 8 web app with no prior Internationalization present?

### Assess Current Internationalization:
Before integrating Spring i18n into your existing Jakarta EE 8 web app, it's essential to evaluate the current state of internationalization. Identify any existing mechanisms for localization, such as resource bundles, and understand how they are currently utilized within the application.

### Integrate Spring Dependencies:
Begin by adding the necessary Spring dependencies to your Jakarta EE project. These dependencies include the Spring i18n module along with the Spring context module. Ensure that you update your project's build configuration (e.g., Maven or Gradle) to include these dependencies.

### Configure Spring Beans:
Define the required Spring beans for internationalization in your Spring configuration. This includes configuring a `ResourceBundleMessageSource` bean, which serves as the central repository for message bundles containing locale-specific messages. Specify the base names of the resource bundles and any additional configurations, such as file encoding.

Next, configure a `LocaleResolver` bean to manage the current locale for each user session. Depending on your application's requirements, you can choose from various implementations of `LocaleResolver`, such as `SessionLocaleResolver` or `CookieLocaleResolver`. Customize the locale resolver to suit your application's locale determination strategy.

### Update Controllers and Services:
Modify existing controllers and services within your application to leverage Spring's `MessageSource` for retrieving localized messages. Instead of directly accessing message bundles or using JSTL tags for localization, inject the `MessageSource` bean into your controllers and services. Utilize the `getMessage()` method provided by the `MessageSource` interface to retrieve localized messages based on the current locale.

### Implement Localization for Multi-Languages:
For countries or regions where multiple languages are prevalent, such as South Africa with English and Afrikaans, extend your internationalization setup to support these languages. Create separate message bundles for each language, organized by locale-specific folders within your application's resource directory (e.g., `messages_en`, `messages_af`). Ensure that each message bundle contains translations for the corresponding language.

In your Spring configuration, configure the `ResourceBundleMessageSource` bean to scan multiple base names corresponding to each language's message bundles. This allows Spring to resolve messages based on the user's locale, fetching the appropriate translations for the selected language.

### Adapt Views for Multi-Languages:
Update your application's views, such as JSPs or Thymeleaf templates, to support multi-languages. Incorporate mechanisms for users to select their preferred language, such as dropdown menus or language toggles. When rendering views, dynamically generate content based on the user's selected locale, ensuring that text, labels, and other UI elements are displayed in the appropriate language.

For example, in a bilingual context like South Africa, provide options for users to switch between English and Afrikaans languages. Update view templates to retrieve localized messages using Spring's internationalization features, ensuring seamless language switching based on user preferences.

### Test and Refine:
Thoroughly test the internationalization functionality to validate that localized messages are displayed correctly for each supported language. Test scenarios with different locales to ensure proper localization across all language options, including English South African and Afrikaans South African. Iterate on your implementation as needed, addressing any issues or discrepancies in the localization process.

### Documentation and Training:
Document the changes made to incorporate Spring i18n and multi-language capabilities into your Jakarta EE 8 web app. Provide comprehensive documentation outlining the configuration steps, usage guidelines, and best practices for internationalization. Additionally, offer training sessions for developers to familiarize them with the Spring i18n features and how to effectively implement multi-language support within the application.
