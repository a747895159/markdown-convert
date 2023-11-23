package com.person.zb.util.markdown.convert;



import com.person.zb.util.markdown.convert.html2md.util.URL2MdHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.FileOutputStream;

/**
 * @author : ZhouBin
 */
public class Run01 {

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
         *
         * 异步文章 暂不支持
         * https://segmentfault.com/a/1190000018835760
         */
        MutablePair<String, String> convert = URL2MdHandler.parse("https://blog.51cto.com/u_5634409/2343489");
        String title = convert.getLeft();
        String value = convert.getRight();
        IOUtils.write(value, new FileOutputStream("C:\\Users\\zhoubin\\Desktop\\temp_111\\" + title + ".md"), "utf-8");
    }
}
