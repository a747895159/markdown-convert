package com.person.zb.util.markdown.convert.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;

/**
 * 支持站点列表
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2021-09-17 14:43
 */
@Getter
@AllArgsConstructor
@Deprecated
public enum HostEnums {

    //CSDN
    CSDN("https://blog.csdn.net/", "id", "article_content", true),

    //博客园
    CNBLOG("https://www.cnblogs.com/", "id", "post_detail", true),

    WECHAT("https://mp.weixin.qq.com/", "id", "img-content", true),

    /**
     * 暂不支持, 需要抓取异步返回数据
     */
    BILIBILI("https://www.bilibili.com/", "css", "article-container", true),

    UNKNOWN("UNKNOWN", "UNKNOWN", "body", true),

    ;

    private String url;

    private String contentType;

    private String pageDiv;

    private Boolean syncFlag;


    public static HostEnums position(URL uri) {
        HostEnums[] values = HostEnums.values();
        for (HostEnums value : values) {
            if (uri.toString().startsWith(value.getUrl())) {
                return value;
            }
        }
        return UNKNOWN;
    }
}