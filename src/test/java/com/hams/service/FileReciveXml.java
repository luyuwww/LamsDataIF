package com.hams.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fileReciveXml complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="fileReciveXml">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xmlName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gdrCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileReciveXml", propOrder = {
        "xmlName",
        "dataXml",
        "gdrCode"
})
public class FileReciveXml {

    protected String xmlName;
    protected String dataXml;
    protected String gdrCode;

    /**
     * Gets the value of the xmlName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getXmlName() {
        return xmlName;
    }

    /**
     * Sets the value of the xmlName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setXmlName(String value) {
        this.xmlName = value;
    }

    /**
     * Gets the value of the dataXml property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDataXml() {
        return dataXml;
    }

    /**
     * Sets the value of the dataXml property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDataXml(String value) {
        this.dataXml = value;
    }

    /**
     * Gets the value of the gdrCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGdrCode() {
        return gdrCode;
    }

    /**
     * Sets the value of the gdrCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGdrCode(String value) {
        this.gdrCode = value;
    }

}
