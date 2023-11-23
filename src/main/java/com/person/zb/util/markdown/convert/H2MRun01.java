package com.person.zb.util.markdown.convert;



import com.person.zb.util.markdown.convert.html2md.URL2MdHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.FileOutputStream;

/**
 * @author : ZhouBin
 */
public class H2MRun01 {

    public static void main(String[] args) throws Exception {
        /**
         * https://www.cnblogs.com/crazymakercircle/p/14221076.html
         * https://www.cnblogs.com/crazymakercircle/p/13895735.html
         *
         * https://thinkwon.blog.csdn.net/article/details/104397367
         *
         * https://mp.weixin.qq.com/s/bExjRkVbDLTV2Wf9G6dzrg
         *
         * https://blog.51cto.com/u_5634409/2343489
         * https://blog.51cto.com/u_16357390/8491071
         * https://www.bilibili.com/read/cv17358778/?from=search&spm_id_from=333.337.0.0
         * https://www.jianshu.com/p/1c16195e9a1b
         * 异步文章 暂不支持
         * https://segmentfault.com/a/1190000018835760
         */
        MutablePair<String, String> convert = URL2MdHandler.parseHtml("https://mp.weixin.qq.com/s/bExjRkVbDLTV2Wf9G6dzrg","");
        String title = convert.getLeft();
        String value = convert.getRight();
        IOUtils.write(value, new FileOutputStream("D:\\data\\" + title + ".md"), "utf-8");
    }
}
