package com.example.wechatofficialaccountstudy.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "xml")
public class TemplateMessageNotification extends BaseXml{
    @JacksonXmlProperty(localName = "Event")
    private String event;
    @JacksonXmlProperty(localName = "MsgID")
    private String msgID;
    @JacksonXmlProperty(localName = "Status")
    private String status;
}
