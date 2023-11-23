package com.person.zb.util.markdown.convert;



import com.person.zb.util.markdown.convert.html2md.URL2MdHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.FileOutputStream;

/**
 * @author : ZhouBin
 */
public class H2MRun03 {

    public static void main(String[] args) throws Exception {
        /*
         *
         * 异步文章 暂不支持
         * https://segmentfault.com/a/1190000018835760
         * https://www.mdnice.com/writing/892151db7a314d12bf636a4e6b7dfd5d
         */
        MutablePair<String, String> convert = URL2MdHandler.parseHtml("https://www.mdnice.com/writing/892151db7a314d12bf636a4e6b7dfd5d","");
        String title = convert.getLeft();
        String value = convert.getRight();
        IOUtils.write(value, new FileOutputStream("D:\\data\\" + title + ".md"), "utf-8");
    }
}
