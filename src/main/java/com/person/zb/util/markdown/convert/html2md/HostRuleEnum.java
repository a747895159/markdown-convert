package com.person.zb.util.markdown.convert.html2md;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HostRuleEnum {

    //CSDN
    CSDN("blog.csdn.net", EleTagEnum.ID, "article_content", true, "-CSDN博客"),

    //博客园
    CNBLOG("cnblogs.com", EleTagEnum.ID, "post_detail", true, " - 博客园"),

    //微信
    WECHAT("mp.weixin.qq.com", EleTagEnum.ID, "img-content", true, null),

    //51CTO
    CTO_51("blog.51cto.com", EleTagEnum.ID, "markdownContent", true, "_51CTO博客"),

    /**
     * 暂不支持, 需要抓取异步返回数据
     */
    BILIBILI("https://www.bilibili.com/", EleTagEnum.CSS, "article-container", true, null),

    UNKNOWN("UNKNOWN", null, "body", true, null),

    ;

    private String host;

    private EleTagEnum eleTag;

    private String eleTagVal;

    private Boolean syncFlag;

    private String titleSplit;


    public static HostRuleEnum findHost(String url) {
        return Arrays.stream(HostRuleEnum.values()).filter(t -> url.contains(t.getHost())).findFirst().orElse(UNKNOWN);
    }
}
