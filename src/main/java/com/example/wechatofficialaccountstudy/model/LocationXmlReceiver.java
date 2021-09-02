package com.example.wechatofficialaccountstudy.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.omg.CORBA.BAD_CONTEXT;


@Data
@JacksonXmlRootElement(localName = "xml")
public class LocationXmlReceiver extends BaseXml{

    @JacksonXmlProperty(localName = "Location_X")
    private String location_X;
    @JacksonXmlProperty(localName = "Location_Y")
    private String location_Y;
    @JacksonXmlProperty(localName = "Scale")
    private String scale;
    @JacksonXmlProperty(localName = "Label")
    private String label;
    @JacksonXmlProperty(localName = "MsgId")
    private String msgId;

}
