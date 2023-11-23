package com.person.zb.util.markdown.convert.html2md.util;


import com.person.zb.util.markdown.convert.html2md.impl.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : ZhouBin
 */
public class URL2MdHandler {

    public static final String CATALOG = "## 文章目录";

    private static List<HTMLParse> list = new ArrayList<>(8);

    static {
        BkyHtmlParse bky = new BkyHtmlParse();
        list.add(bky);
        CSDNHtmlParse csdn = new CSDNHtmlParse();
        list.add(csdn);
        WechatParseContent wechat = new WechatParseContent();
        list.add(wechat);

    }

    public static MutablePair<String, String> parse(String url) {
        HTMLParse htmlContent = new DefaultHtmlParse();
        for (HTMLParse h : list) {
            if (url.contains(h.getHost())) {
                htmlContent = h;
                break;
            }
        }
        return pair(url, htmlContent, null);
    }

    public static MutablePair<String, String> parseById(String url, String eleId) {
        IdHtmlParse htmlContent = new IdHtmlParse();
        return pair(url, htmlContent, eleId);
    }

    private static MutablePair<String, String> pair(String url, HTMLParse htmlParse, String eleId) {
        try {
            MutablePair<String, String> pair = new MutablePair<>();
            // 获取正文内容，
            Document doc = Jsoup.parse(new URL(url), 5000);
            String title = htmlParse.getTitle(doc);
            //获取正文内容元素
            Element parse = htmlParse.parse(doc, eleId);
            String content = MdConvertUtil.getTextContent(parse);
            if (content.contains(CATALOG)) {
                String[] split = content.split(CATALOG);
                content = CATALOG + "\n[TOC]\n" + split[1];
            }
            content += "\n \n \n [原文地址](" + url + ") ";

            pair.setRight(content);
            pair.setLeft(title);
            return pair;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
