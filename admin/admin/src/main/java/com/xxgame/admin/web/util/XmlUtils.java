package com.xxgame.admin.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * XmlUtils
 * @author gil
 */
public class XmlUtils {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    /**
     * 将xml转成map，jdk实现
     * @param xml
     * @return
     */
    public static Map<String, Object> xml2Map(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all XML entity attacks are prevented
            // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
            String feature = "http://apache.org/xml/features/disallow-doctype-decl";
            dbf.setFeature(feature, true);

            DocumentBuilder safebuilder = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            Document document = safebuilder.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            int len = nodeList.getLength();

            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < len; i++) {
                Node node = nodeList.item(i);
                String nodeName = node.getNodeName();
                Node firstChild = node.getFirstChild();
                if (firstChild == null) {
                    continue;
                }
                map.put(nodeName, firstChild.getNodeValue());
            }

            return map;
        } catch (Exception e) {
            logger.error("解析xml出现异常\n" + xml, e);
            throw new RuntimeException("解析xml出现异常");
        }
    }

}
