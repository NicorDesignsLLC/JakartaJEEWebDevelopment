# 3. Implementing Spring Web Services for SOAP - Part 1

### Writing Our Contract-First XSD and WSDL

Spring Web Services enforces a contract-first approach to development, meaning you must define your XML schema before anything else.

#### Charity Registration XML Document Example

Here is an example of a Charity Registration XML document:

```xml
<registration>
    <id>1</id>
    <userName>john_doe</userName>
    <subject>Sample Subject</subject>
    <body>This is a sample body text for the registration.</body>
    <createdDate>2024-06-22T12:00:00Z</createdDate>
    <fileAttachments>
        <fileAttachment>
            <name>sampleFile1.txt</name>
            <mimeContentType>text/plain</mimeContentType>
            <contents>U29tZSBmaWxlIGNvbnRlbnRzIGVuY29kZWQgaW4gYmFzZTY0</contents> <!-- Base64 encoded content -->
        </fileAttachment>
        <fileAttachment>
            <name>sampleFile2.jpg</name>
            <mimeContentType>image/jpeg</mimeContentType>
            <contents>/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUSEhIVFRUVFRUVFRUVFRUVFRUVFRUWFhUVFRUYHSggGBolHRUVITEhJSkrLi4uFx8zODMsNygtLisBCgoKDg0OFQ8QFS0dFR0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAKgBLAMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAABQYBAwIBB//EADkQAAIBAwMBBQUEBAYDAAAAAAABAgMEERIhBRMxQVFhBhMicYGRMkKxwdHh8DJCUpLxFiQzgsH/xAAYAQEBAQEBAAAAAAAAAAAAAAAAAgEDBf/EACARAQEBAAMBAQEAAwEAAAAAAAABEQIhAxJBEyJx8P/aAAwDAQACEQMRAD8A+FNEVKV5QFpKyLHSYTSnqQUUzBhFjEKQhQEZHJBb0glXoeSSdBJ+gqnQnXtJNLQ20hoVFmuPC6qg4pPG0dBHDyXTzkIuD5uHdhkhZyZ2N6Kr2chbyvKTNytosQscZJAlhN3UAAbfiRU+YyFc7koV22a1dvBIR5GshN/DdmPd8KOYk+P8AsRUXW42kkayVEVVOgAHckfXeqSy5TQ26kmt/UG8ZGj8Iz1TnpDgbsQWy0AnRtsmSWsL9WUMyqAj3kR80Rj1kKe5KnXo06d8g5HlA2nUIFQnUpPMbivNQjbVoRQvYZkJOTptQtnRsZBWwLqSTQxzPDEQkQfx3NUuyqwvJUGkETLAg/v7+tQtTGo5SePOh7sRYA6xsf1iaSSDjwKnSa3FAlT8T6FZdFLVmNDGAeKnLZ4jR9mjltuoxTLCFSAPDndAE9J0NYiV0PxtiBOWHX/AFVRyt1hYRGzUkPloW2MbiPafIqXCG7Y0ylcwCWSdAkdqkD6ntQnDEmpSYGEPA+4ABndj/Tzqy0KiulX0VjMmUuN4YgB8wNv/AGgNPBzFxrIsELSKQg4uURvPl8ICaKeHa5KvYaTTK3HGe7zvh54NdANWnIctC2w8WcSe4t5Oue1XRIxj6na49WBJAII+YwF+kfY9E21eS4t0pt1HLiw4eqIOPVGxxRuoMkDbGcGn6Tk4VylDKjiJ9t3tT5QfRu+zSLQztDAhgIEHIGfmP7VrONeaVtJsp02MqgB+QpKkD1H23gNCcuMWtAqEjzDtA2PoDqOtKDRyccGS1EYLeRnCzHfA6j8nP0oP/2Q==</contents> <!-- Base64 encoded content -->
        </fileAttachment>
    </fileAttachments>
</registration>


<fileAttachments>
    <fileAttachment>
        <name>sampleFile1.txt</name>
        <mimeContentType>text/plain</mimeContentType>
        <contents>U29tZSBmaWxlIGNvbnRlbnRzIGVuY29kZWQgaW4gYmFzZTY0</contents> <!-- Base64 encoded content -->
    </fileAttachment>
    <fileAttachment>
        <name>sampleFile2.jpg</name>
        <mimeContentType>image/jpeg</mimeContentType>
        <contents>/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUSEhIVFRUVFRUVFRUVFRUVFRUVFRUWFhUVFRUYHSggGBolHRUVITEhJSkrLi4uFx8zODMsNygtLisBCgoKDg0OFQ8QFS0dFR0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAKgBLAMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAABQYBAwIBB//EADkQAAIBAwMBBQUEBAYDAAAAAAABAgMEERIhBRMxQVFhBhMicYGRMkKxwdHh8DJCUpLxFiQzgsH/xAAYAQEBAQEBAAAAAAAAAAAAAAAAAgEDBf/EACARAQEBAAMBAQEAAwEAAAAAAAABEQIhAxJBEyJx8P/aAAwDAQACEQMRAD8A+FNEVKV5QFpKyLHSYTSnqQUUzBhFjEKQhQEZHJBb0glXoeSSdBJ+gqnQnXtJNLQ20hoVFmuPC6qg4pPG0dBHDyXTzkIuD5uHdhkhZyZ2N6Kr2chbyvKTNytosQscZJAlhN3UAAbfiRU+YyFc7koV22a1dvBIR5GshN/DdmPd8KOYk+P8AsRUXW42kkayVEVVOgAHckfXeqSy5TQ26kmt/UG8ZGj8Iz1TnpDgbsQWy0AnRtsmSWsL9WUMyqAj3kR80Rj1kKe5KnXo06d8g5HlA2nUIFQnUpPMbivNQjbVoRQvYZkJOTptQtnRsZBWwLqSTQxzPDEQkQfx3NUuyqwvJUGkETLAg/v7+tQtTGo5SePOh7sRYA6xsf1iaSSDjwKnSa3FAlT8T6FZdFLVmNDGAeKnLZ4jR9mjltuoxTLCFSAPDndAE9J0NYiV0PxtiBOWHX/AFVRyt1hYRGzUkPloW2MbiPafIqXCG7Y0ylcwCWSdAkdqkD6ntQnDEmpSYGEPA+4ABndj/Tzqy0KiulX0VjMmUuN4YgB8wNv/AGgNPBzFxrIsELSKQg4uURvPl8ICaKeHa5KvYaTTK3HGe7zvh54NdANWnIctC2w8WcSe4t5Oue1XRIxj6na49WBJAII+YwF+kfY9E21eS4t0pt1HLiw4eqIOPVGxxRuoMkDbGcGn6Tk4VylDKjiJ9t3tT5QfRu+zSLQztDAhgIEHIGfmP7VrONeaVtJsp02MqgB+QpKkD1H23gNCcuMWtAqEjzDtA2PoDqOtKDRyccGS1EYLeRnCzHfA6j8nP0oP/2Q==</contents> <!-- Base64 encoded content -->
    </fileAttachment>
</fileAttachments>


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


### References

1. [Spring Web Services Documentation](https://docs.spring.io/spring-ws/docs/current/reference/)
2. [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
3. [Jakarta EE Documentation](https://jakarta.ee/specifications/)

---
