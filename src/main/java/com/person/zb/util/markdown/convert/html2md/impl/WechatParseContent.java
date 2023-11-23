package com.person.zb.util.markdown.convert.html2md.impl;



import com.person.zb.util.markdown.convert.html2md.util.MdConvertUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author : ZhouBin
 */
public class WechatParseContent implements HTMLParse {
    @Override
    public Element parse(Document doc,String eleId) {
        Element ele = doc.getElementById("img-content");
        removeEle(ele);
        return ele;
    }

    @Override
    public String getTitle(Document doc) {
        return MdConvertUtil.fetchTitle(doc);
    }

    @Override
    public String getHost() {
        return "mp.weixin.qq.com";
    }

    private static void removeEle(Element content) {
        content.getElementsByTag("script").remove();
        // 图片标签显示
        long count = content.getElementsByTag("img").stream().peek(e -> {
            e.attr("src", e.attr("data-src"));
        }).count();
//        System.out.println("共计 " + count + " 张图片被处理");
        // 正文内容展示
        content.getElementById("js_content").attr("style", "visibility");
    }
}
