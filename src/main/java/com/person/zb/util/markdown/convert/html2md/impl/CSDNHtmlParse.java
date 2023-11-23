package com.person.zb.util.markdown.convert.html2md.impl;

import com.person.zb.util.markdown.convert.html2md.util.MdConvertUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author : ZhouBin
 */
public class CSDNHtmlParse implements HTMLParse {


    @Override
    public Element parse(Document doc,String eleId) {
        Element ele = doc.getElementById("article_content");
        handleCsdn(ele);
        return ele;
    }

    @Override
    public String getTitle(Document doc) {
        return MdConvertUtil.fetchTitle(doc).replaceAll("-CSDN博客", "");
    }

    @Override
    public String getHost() {
        return "blog.csdn.net";
    }

    private static void handleCsdn(Element content) {
        content.getElementsByTag("script").remove();
        content.select(".dp-highlighter").remove();
    }
}
