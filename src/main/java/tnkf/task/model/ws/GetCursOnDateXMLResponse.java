
package tnkf.task.model.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetCursOnDateXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getCursOnDateXMLResult"
})
@XmlRootElement(name = "GetCursOnDateXMLResponse")
public class GetCursOnDateXMLResponse {

    @XmlElement(name = "GetCursOnDateXMLResult")
    protected GetCursOnDateXMLResponse.GetCursOnDateXMLResult getCursOnDateXMLResult;

    /**
     * Gets the value of the getCursOnDateXMLResult property.
     *
     * @return
     *     possible object is
     *     {@link GetCursOnDateXMLResponse.GetCursOnDateXMLResult }
     *
     */
    public GetCursOnDateXMLResponse.GetCursOnDateXMLResult getGetCursOnDateXMLResult() {
        return getCursOnDateXMLResult;
    }

    /**
     * Sets the value of the getCursOnDateXMLResult property.
     *
     * @param value
     *     allowed object is
     *     {@link GetCursOnDateXMLResponse.GetCursOnDateXMLResult }
     *
     */
    public void setGetCursOnDateXMLResult(GetCursOnDateXMLResponse.GetCursOnDateXMLResult value) {
        this.getCursOnDateXMLResult = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;any/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "valuteData"
    })
    public static class GetCursOnDateXMLResult {

        @XmlElement(name = "ValuteData", required = true, namespace = "")
        protected ValuteData valuteData;

        /**
         * Gets the value of the valuteData property.
         *
         * @return
         *     possible object is
         *     {@link ValuteData }
         *
         */
        public ValuteData getValuteData() {
            return valuteData;
        }

        /**
         * Sets the value of the valuteData property.
         *
         * @param value
         *     allowed object is
         *     {@link ValuteData }
         *
         */
        public void setValuteData(ValuteData value) {
            this.valuteData = value;
        }

    }

}
