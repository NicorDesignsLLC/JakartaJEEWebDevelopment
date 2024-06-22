# 3. Implementing Spring Web Services for SOAP - Part 1

## Spring Web Services Introduction

The topic of Spring Web Services is vast and can easily fill an entire book. In this guide, we'll provide a concise introduction and practical example to get you started. For more comprehensive information, refer to the [official Spring Web Services documentation](https://docs.spring.io/spring-ws/docs/current/reference/html).

## Required Maven Dependencies

To begin, you need to include the necessary Maven dependencies in your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.ws</groupId>
        <artifactId>spring-ws-core</artifactId>
        <version>3.1.1.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.10</version>
    </dependency>
</dependencies>
```

---

## Creating a SOAP Endpoint for Charity Registration

In this example, we will create a SOAP endpoint for registering charities in our Jakarta JEE8 Web App. For now, we will not secure our SOAP endpoint as we will cover that later.

### Writing Our Contract-First XSD and WSDL

Spring Web Services enforces a contract-first approach to development, meaning you must define your XML schema before anything else.

#### Charity Registration XML Document Example

Here is an example of a Charity Registration XML document:

```xml
<CharityRegistration>
    <CharityName>Example Charity</CharityName>
    <CharityAddress>123 Charity Lane</CharityAddress>
    <CharityPhone>123-456-7890</CharityPhone>
    <CharityEmail>info@examplecharity.org</CharityEmail>
</CharityRegistration>
```

We also need to be able to add  a charity registration, so we need a `registrationRequest` 


#### Registration Request XML Elements

```xml
<registrationRequest>
    <CharityRegistration>
        <CharityName>Example Charity</CharityName>
        <CharityAddress>123 Charity Lane</CharityAddress>
        <CharityPhone>123-456-7890</CharityPhone>
        <CharityEmail>info@examplecharity.org</CharityEmail>
    </CharityRegistration>
</registrationRequest>

```

We will use an open-source tool to generate the XSD schema for us from these XML documents.

### Generating XSD from XML Using FreeFormatter.com

FreeFormatter.com provides an easy-to-use, online tool for generating an XML Schema (XSD) from an XML document. Here’s how you can use it:

1. **Visit FreeFormatter.com:**
   - Open your web browser and go to [FreeFormatter XML to XSD Generator](https://www.freeformatter.com/xsd-generator.html#before-output).

2. **Input Your XML Content:**
   - Copy and paste your XML content into the text box provided on the website.

3. **Generate the XSD:**
   - Click the "Generate XSD" button. The tool will process your XML and produce an XSD schema.

4. **Download or Copy the XSD:**
   - You can either download the generated XSD file or copy the content directly from the website.

### Example of Generated XSD

Here is an example of what the generated XSD might look like:

```xml
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xs:element name="CharityRegistration">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityName" type="xs:string"/>
                <xs:element name="CharityAddress" type="xs:string"/>
                <xs:element name="CharityPhone" type="xs:string"/>
                <xs:element name="CharityEmail" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="CharityRegistrationForm">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityName" type="xs:string"/>
                <xs:element name="CharityAddress" type="xs:string"/>
                <xs:element name="CharityPhone" type="xs:string"/>
                <xs:element name="CharityEmail" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="registrationRequest">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityRegistration" type="CharityRegistration"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="deleteRegistration">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="CharityId" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
</xs:schema>
```

With the XSD file generated, you can now proceed to create your WSDL and implement your SOAP endpoint in your Jakarta JEE8 Web App.

---


### References

1. [Spring Web Services Documentation](https://docs.spring.io/spring-ws/docs/current/reference/)
2. [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
3. [Jakarta EE Documentation](https://jakarta.ee/specifications/)

---
