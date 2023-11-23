package com.person.zb.util.markdown.convert.html2md.impl;



import com.person.zb.util.markdown.convert.html2md.util.MdConvertUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author : ZhouBin
 */
public class IdHtmlParse implements HTMLParse {
    @Override
    public Element parse(Document doc, String eleId) {
        return doc.getElementById(eleId);
    }

    @Override
    public String getTitle(Document doc) {
        return MdConvertUtil.fetchTitle(doc);
    }

    @Override
    public String getHost() {
        return null;
    }
}
