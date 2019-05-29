package tnkf.task.model.ws;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCursOnDateXMLResponse }
     *
     */
    public GetCursOnDateXMLResponse createGetCursOnDateXMLResponse() {
        return new GetCursOnDateXMLResponse();
    }

    /**
     * Create an instance of {@link GetCursOnDateXMLResponse.GetCursOnDateXMLResult }
     *
     */
    public GetCursOnDateXMLResponse.GetCursOnDateXMLResult createGetCursOnDateXMLResponseGetCursOnDateXMLResult() {
        return new GetCursOnDateXMLResponse.GetCursOnDateXMLResult();
    }

    /**
     * Create an instance of {@link ValuteData }
     *
     */
    public ValuteData createValuteData() {
        return new ValuteData();
    }

    /**
     * Create an instance of {@link ValuteCursOnDate }
     *
     */
    public ValuteCursOnDate createValuteCursOnDate() {
        return new ValuteCursOnDate();
    }

    /**
     * Create an instance of {@link GetCursOnDateXML }
     *
     */
    public GetCursOnDateXML createGetCursOnDateXML() {
        return new GetCursOnDateXML();
    }

}
