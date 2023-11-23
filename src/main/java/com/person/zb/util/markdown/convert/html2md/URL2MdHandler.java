package com.person.zb.util.markdown.convert.html2md;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Objects;

/**
 * @author : ZhouBin
 */
public class URL2MdHandler {

    public static final String CATALOG = "## 文章目录";

    /**
     * 根据URL规则进行解析
     */
    public static MutablePair<String, String> parseHtml(String url) {
        HostRuleEnum ruleEnum = HostRuleEnum.findHost(url);
        return parseRemoteUrlHtml(url, ruleEnum, null);

    }

    /**
     * 根据元素Id进行解析，若为空时，按照url方式解析
     */
    public static MutablePair<String, String> parseHtml(String url, String eleId) {
        HostRuleEnum ruleEnum = HostRuleEnum.findHost(url);
        return parseRemoteUrlHtml(url, ruleEnum, eleId);
    }

    private static MutablePair<String, String> parseRemoteUrlHtml(String url, HostRuleEnum ruleEnum, String eleId) {
        try {
            MutablePair<String, String> pair = new MutablePair<>();
            // 获取正文内容，
            Document doc = Jsoup.parse(new URL(url), 5000);
            String title;
            Element ele = null;
            if (StringUtils.isNotBlank(eleId)) {
                title = MdConvertUtil.fetchTitle(doc);
                ele = doc.getElementById(eleId);
            } else {
                title = getTitle(doc, ruleEnum.getTitleSplit());
                if (Objects.equals(EleTagEnum.ID, ruleEnum.getEleTag())) {
                    String[] str1 = ruleEnum.getEleTagVal().split("\\|");
                    if (str1.length > 1) {
                        for (String s : str1) {
                            ele = doc.getElementById(s);
                            if (ele != null) {
                                break;
                            }
                        }
                    } else {
                        ele = doc.getElementById(ruleEnum.getEleTagVal());
                    }
                } else if (Objects.equals(EleTagEnum.CSS, ruleEnum.getEleTag())) {
                    ele = doc.select("." + ruleEnum.getEleTagVal()).get(0);
                } else if (Objects.equals(EleTagEnum.TAG, ruleEnum.getEleTag())) {
                    ele = doc.getElementsByTag(ruleEnum.getEleTagVal()).get(0);
                } else {
                    ele = doc.getElementsByTag(ruleEnum.getEleTagVal()).get(0);
                }
                handlerWebSite(ruleEnum, ele);
            }
            //获取正文内容元素
            String content = MdConvertUtil.getTextContent(ele);
            if (content != null && content.contains(CATALOG)) {
                String[] split = content.split(CATALOG);
                content = CATALOG + "\n[TOC]\n" + split[1];
            }
            content += "\n \n \n [原文地址](" + url + ") ";

            pair.setRight(content);
            pair.setLeft(title.replaceAll("\\\\","").replaceAll("/",""));
            return pair;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String getTitle(Document doc, String titleSplit) {
        String title = MdConvertUtil.fetchTitle(doc);
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(titleSplit)) {
            return title.split(titleSplit)[0];
        }
        return title;
    }

    private static void handlerWebSite(HostRuleEnum ruleEnum, Element ele) {
        if (ruleEnum == null) {
            return;
        }
        switch (ruleEnum) {
            case CSDN:
                ele.getElementsByTag("script").remove();
                ele.select(".dp-highlighter").remove();
                break;
            case CNBLOG:
                ele.getElementsByTag("script").remove();
                break;
            case WECHAT:
                ele.getElementsByTag("script").remove();
                // 图片标签显示
                ele.getElementsByTag("img").forEach(e -> e.attr("src", e.attr("data-src")));
                // 正文内容展示
                Element jsContent = ele.getElementById("js_content");
                if (jsContent != null) {
                    jsContent.attr("style", "visibility");
                }
                break;
            case BILIBILI:
                ele.getElementsByTag("img").forEach(e -> e.attr("src", e.attr("data-src")));
                break;
            case JS:
                ele.getElementsByTag("img").forEach(e -> e.attr("src", e.attr("data-original-src")));
                break;
            default:

        }

    }


}
