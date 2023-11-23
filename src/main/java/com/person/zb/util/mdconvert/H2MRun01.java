package com.person.zb.util.mdconvert;


import com.person.zb.util.mdconvert.html2md.URL2MdHandler;
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
         * https://www.cnblogs.com/crazymakercircle/p/14221076.html
         * https://www.cnblogs.com/crazymakercircle/p/13895735.html
         *
         * https://thinkwon.blog.csdn.net/article/details/104397367
         *
         * https://mp.weixin.qq.com/s/fXIjIzXXi936vaqKw6gayw
         * https://mp.weixin.qq.com/s/yWn5vUpUh-VKyYgGHf38Zg
         *
         * https://blog.51cto.com/u_5634409/2343489
         * https://blog.51cto.com/u_16357390/8491071
         * https://www.bilibili.com/read/cv17358778/?from=search&spm_id_from=333.337.0.0
         * https://www.jianshu.com/p/1c16195e9a1b
         *
         */
        MutablePair<String, String> convert = URL2MdHandler.parseHtml("https://www.cnblogs.com/offerwx/p/16623724.html", "");
        String title = convert.getLeft();
        String value = convert.getRight();
        IOUtils.write(value, new FileOutputStream("D:\\data\\" + title + ".md"), "utf-8");
    }
}
