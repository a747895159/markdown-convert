package com.person.zb.util.mdconvert;


import com.person.zb.util.mdconvert.html2md.URL2MdHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.FileOutputStream;

/**
 * 异步-selenium方式获取页面内容
 *
 * @author : ZhouBin
 */
public class H2MRun03 {

    public static void main(String[] args) throws Exception {
        /*
         *
         * 异步文章
         * https://segmentfault.com/a/1190000018835760
         * https://www.mdnice.com/writing/2a53472ff62e47e78f4d8dc005e91be1
         * https://www.toutiao.com/article/6730619165612179979
         */
        MutablePair<String, String> convert = URL2MdHandler.parseHtml("https://www.toutiao.com/article/6730619165612179979", "");
        String title = convert.getLeft();
        String value = convert.getRight();
        IOUtils.write(value, new FileOutputStream("D:\\data\\" + title + ".md"), "utf-8");
    }
}
