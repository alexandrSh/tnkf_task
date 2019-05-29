package tnkf.task.model.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "valuteCursOnDate"
})
public class ValuteData {

    @XmlElement(name = "ValuteCursOnDate", required = true, namespace = "")
    protected List<ValuteCursOnDate> valuteCursOnDate;
    @XmlAttribute(name = "OnDate")
    protected Integer onDate;

    /**
     * Gets the value of the valuteCursOnDate property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valuteCursOnDate property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValuteCursOnDate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValuteCursOnDate }
     */
    public List<ValuteCursOnDate> getValuteCursOnDate() {
        if (valuteCursOnDate == null) {
            valuteCursOnDate = new ArrayList<>();
        }
        return this.valuteCursOnDate;
    }

    /**
     * Gets the value of the onDate property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getOnDate() {
        return onDate;
    }

    /**
     * Sets the value of the onDate property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setOnDate(Integer value) {
        this.onDate = value;
    }
}
