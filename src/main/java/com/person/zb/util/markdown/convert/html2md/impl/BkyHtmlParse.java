package com.person.zb.util.markdown.convert.html2md.impl;


import com.person.zb.util.markdown.convert.html2md.util.MdConvertUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author : ZhouBin
 */
public class BkyHtmlParse implements HTMLParse {
    @Override
    public Element parse(Document doc,String eleId) {
        Element ele = doc.getElementById("post_detail");
        removeEle(ele);
        return ele;
    }

    @Override
    public String getTitle(Document doc) {
        return MdConvertUtil.fetchTitle(doc).replaceAll(" - 博客园", "");
    }

    @Override
    public String getHost() {
        return "cnblogs.com";
    }

    private static void removeEle(Element content) {
        content.getElementsByTag("script").remove();
    }
}
