package com.person.zb.util.markdown.convert.html2md;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HostRuleEnum {

    /**
     *
     */
    CSDN("blog.csdn.net", EleTagEnum.ID, "article_content", true, "-CSDN博客"),

    CNBLOG("cnblogs.com", EleTagEnum.ID, "post_detail", true, " - 博客园"),

    //微信
    WECHAT("mp.weixin.qq.com", EleTagEnum.ID, "img-content", true, null),

    CTO_51("blog.51cto.com", EleTagEnum.ID, "markdownContent", true, "_51CTO博客"),

    BILIBILI("bilibili.com", EleTagEnum.CSS, "article-content", true, " - 哔哩哔哩"),

    JS("jianshu.com", EleTagEnum.CSS, "article-content", true, " - 简书"),


    UNKNOWN("UNKNOWN", null, "body", true, null),

    ;

    /**
     * host域名
     */
    private String host;

    /**
     * 元素类型
     */
    private EleTagEnum eleTag;

    /**
     * 元素值
     */
    private String eleTagVal;

    /**
     * 页面内容 同步/异步标识，暂不支持异步方式
     */
    private Boolean syncFlag;

    /**
     * 标题名分割串
     */
    private String titleSplit;


    public static HostRuleEnum findHost(String url) {
        return Arrays.stream(HostRuleEnum.values()).filter(t -> url.contains(t.getHost())).findFirst().orElse(UNKNOWN);
    }
}
