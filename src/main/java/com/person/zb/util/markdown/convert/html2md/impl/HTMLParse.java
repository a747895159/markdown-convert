package com.person.zb.util.markdown.convert.html2md.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface HTMLParse {

    Element parse(Document doc,String eleId);

    String getTitle(Document doc);

    String getHost();
}
