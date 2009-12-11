package com.vaadin.incubator.simpleshop.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.NodeFactory;

/**
 * This is a helper class for maintaining the translations.xml file. This file
 * will look for any translations defined in the SystemMsg-file but which have
 * not yet been added to the translations.xml. This class will then create stubs
 * for those missing translations.
 * 
 * @author Kim
 * 
 */
public class FillXml {

    public static void main(String[] args) {
        File file = new File("src/translations.xml");
        Builder builder = new Builder();
        try {
            List<String> ids = new ArrayList<String>();
            Document document = builder.build(file);
            Element root = document.getRootElement();
            Elements tu = root.getChildElements("body").get(0)
                    .getChildElements("tu");
            for (int i = 0; i < tu.size(); i++) {
                String id = tu.get(i).getAttributeValue("tuid");
                ids.add(id);
            }

            NodeFactory nf = new NodeFactory();
            for (SystemMsg msg : SystemMsg.values()) {
                if (!ids.contains(msg.name())) {
                    Element element = nf.startMakingElement("tu", null);
                    element.addAttribute(new Attribute("tuid", msg.name()));

                    for (String lang : new String[] { "fi", "sv", "en" }) {
                        Element e = nf.startMakingElement("tuv", null);
                        e.addAttribute(new Attribute("lang", lang));

                        Element seg = nf.startMakingElement("seg", null);
                        seg.addAttribute(new Attribute("value", "TODO"));
                        e.appendChild(seg);
                        element.appendChild(e);
                    }
                    root.getChildElements("body").get(0).appendChild(element);
                }
            }

            String xml = root.toXML().replaceAll("<seg value=\"TODO\" />",
                    "<seg>TODO</seg>");

            if (file.exists()) {
                FileOutputStream fileoutstream = new FileOutputStream(file);
                Writer writer = new OutputStreamWriter(fileoutstream, "UTF-8");
                writer.write(xml);
                writer.close();

            } else {
                System.out.println("file did not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
