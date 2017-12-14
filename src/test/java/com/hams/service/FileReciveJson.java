package com.hams.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fileReciveJson complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="fileReciveJson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xmlName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataJson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gdrCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileReciveJson", propOrder = {
        "xmlName",
        "dataJson",
        "gdrCode"
})
public class FileReciveJson {

    protected String xmlName;
    protected String dataJson;
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
     * Gets the value of the dataJson property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDataJson() {
        return dataJson;
    }

    /**
     * Sets the value of the dataJson property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDataJson(String value) {
        this.dataJson = value;
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
