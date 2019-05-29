package tnkf.task.model.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "vname",
        "vnom",
        "vcurs",
        "vcode",
        "vchCode"
})
public class ValuteCursOnDate {

    @XmlElement(name = "Vname", required = true, namespace = "")
    protected String vname;
    @XmlElement(name = "Vnom", namespace = "")
    protected int vnom;
    @XmlElement(name = "Vcurs", required = true, namespace = "")
    protected BigDecimal vcurs;
    @XmlElement(name = "Vcode", namespace = "")
    protected int vcode;
    @XmlElement(name = "VchCode", required = true, namespace = "")
    protected String vchCode;

    /**
     * Gets the value of the vname property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVname() {
        return vname;
    }

    /**
     * Sets the value of the vname property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVname(String value) {
        this.vname = value;
    }

    /**
     * Gets the value of the vnom property.
     */
    public int getVnom() {
        return vnom;
    }

    /**
     * Sets the value of the vnom property.
     */
    public void setVnom(int value) {
        this.vnom = value;
    }

    /**
     * Gets the value of the vcurs property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getVcurs() {
        return vcurs;
    }

    /**
     * Sets the value of the vcurs property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setVcurs(BigDecimal value) {
        this.vcurs = value;
    }

    /**
     * Gets the value of the vcode property.
     */
    public int getVcode() {
        return vcode;
    }

    /**
     * Sets the value of the vcode property.
     */
    public void setVcode(int value) {
        this.vcode = value;
    }

    /**
     * Gets the value of the vchCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVchCode() {
        return vchCode;
    }

    /**
     * Sets the value of the vchCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVchCode(String value) {
        this.vchCode = value;
    }

}
