package com.hams.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.unis.service package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FileReciveJsonResponse_QNAME = new QName("http://service.unis.com/", "fileReciveJsonResponse");
    private final static QName _FileReciveJson_QNAME = new QName("http://service.unis.com/", "fileReciveJson");
    private final static QName _FileReciveTxtResponse_QNAME = new QName("http://service.unis.com/", "fileReciveTxtResponse");
    private final static QName _FileReciveXmlResponse_QNAME = new QName("http://service.unis.com/", "fileReciveXmlResponse");
    private final static QName _FileReciveTxt_QNAME = new QName("http://service.unis.com/", "fileReciveTxt");
    private final static QName _FileReciveXml_QNAME = new QName("http://service.unis.com/", "fileReciveXml");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.unis.service
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileReciveTxt }
     */
    public FileReciveTxt createFileReciveTxt() {
        return new FileReciveTxt();
    }

    /**
     * Create an instance of {@link FileReciveXml }
     */
    public FileReciveXml createFileReciveXml() {
        return new FileReciveXml();
    }

    /**
     * Create an instance of {@link FileReciveJsonResponse }
     */
    public FileReciveJsonResponse createFileReciveJsonResponse() {
        return new FileReciveJsonResponse();
    }

    /**
     * Create an instance of {@link FileReciveTxtResponse }
     */
    public FileReciveTxtResponse createFileReciveTxtResponse() {
        return new FileReciveTxtResponse();
    }

    /**
     * Create an instance of {@link FileReciveJson }
     */
    public FileReciveJson createFileReciveJson() {
        return new FileReciveJson();
    }

    /**
     * Create an instance of {@link FileReciveXmlResponse }
     */
    public FileReciveXmlResponse createFileReciveXmlResponse() {
        return new FileReciveXmlResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveJsonResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.unis.com/", name = "fileReciveJsonResponse")
    public JAXBElement<FileReciveJsonResponse> createFileReciveJsonResponse(FileReciveJsonResponse value) {
        return new JAXBElement<FileReciveJsonResponse>(_FileReciveJsonResponse_QNAME, FileReciveJsonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveJson }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.unis.com/", name = "fileReciveJson")
    public JAXBElement<FileReciveJson> createFileReciveJson(FileReciveJson value) {
        return new JAXBElement<FileReciveJson>(_FileReciveJson_QNAME, FileReciveJson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveTxtResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.unis.com/", name = "fileReciveTxtResponse")
    public JAXBElement<FileReciveTxtResponse> createFileReciveTxtResponse(FileReciveTxtResponse value) {
        return new JAXBElement<FileReciveTxtResponse>(_FileReciveTxtResponse_QNAME, FileReciveTxtResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveXmlResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.unis.com/", name = "fileReciveXmlResponse")
    public JAXBElement<FileReciveXmlResponse> createFileReciveXmlResponse(FileReciveXmlResponse value) {
        return new JAXBElement<FileReciveXmlResponse>(_FileReciveXmlResponse_QNAME, FileReciveXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveTxt }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.unis.com/", name = "fileReciveTxt")
    public JAXBElement<FileReciveTxt> createFileReciveTxt(FileReciveTxt value) {
        return new JAXBElement<FileReciveTxt>(_FileReciveTxt_QNAME, FileReciveTxt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveXml }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.unis.com/", name = "fileReciveXml")
    public JAXBElement<FileReciveXml> createFileReciveXml(FileReciveXml value) {
        return new JAXBElement<FileReciveXml>(_FileReciveXml_QNAME, FileReciveXml.class, null, value);
    }

}
