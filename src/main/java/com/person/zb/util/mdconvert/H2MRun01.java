package com.person.zb.util.mdconvert;


import com.person.zb.util.mdconvert.html2md.HtmlHandlerUtil;
import com.person.zb.util.mdconvert.uodo.MarkdownToHtmlUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.FileOutputStream;

/**
 * 同步获取页面内容 测试
 *
 * @author : ZhouBin
 */
public class H2MRun01 {

    public static void main(String[] args) throws Exception {
        /*
         * https://www.cnblogs.com/crazymakercircle/p/13895735.html
         * https://thinkwon.blog.csdn.net/article/details/104397367
         * https://mp.weixin.qq.com/s/yWn5vUpUh-VKyYgGHf38Zg
         * https://blog.51cto.com/u_16357390/8491071
         * https://www.bilibili.com/read/cv17358778/?from=search&spm_id_from=333.337.0.0
         * https://www.jianshu.com/p/1c16195e9a1b
         * https://zhuanlan.zhihu.com/p/590236713
         *
         */
        MutablePair<String, String> convert = HtmlHandlerUtil.parseHtml("https://zhuanlan.zhihu.com/p/590236713", "");
        String title = convert.getLeft();
        String value = convert.getRight();
        IOUtils.write(value, new FileOutputStream("D:\\data\\" + title + ".md"), "utf-8");

        //TODO 半成品生成HTMl，效果不太好
        String htmlContent = MarkdownToHtmlUtils.markdownToHtmlExtensions(value);
        IOUtils.write(htmlContent, new FileOutputStream("D:\\data\\" + title + ".html"), "utf-8");
    }
}
