package com.person.zb.util.markdown.convert.html2md;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MdConvertUtil {

    public static final String CATALOG = "## 文章目录";
    private static int indentation = -1;
    private static boolean orderedList = false;


    public static String fetchTitle(Document doc) {
        String title = "default";
        Elements titleTag = doc.getElementsByTag("title");
        if (!titleTag.isEmpty() && !StringUtils.isBlank(titleTag.get(0).text())) {
            title = titleTag.get(0).text();
            return title;
        }
        Elements osTag = doc.select("meta[property=og:title]");
        if (!osTag.isEmpty() && !StringUtils.isBlank(osTag.get(0).attr("content"))) {
            title = osTag.get(0).attr("content");
            return title;
        }
        Elements twTag = doc.select("meta[twitter:title]");
        if (!twTag.isEmpty() && !StringUtils.isBlank(twTag.get(0).attr("content"))) {
            title = osTag.get(0).attr("content");
            return title;
        }
        return title;
    }


    public static String convert(String theHTML, String baseURL) {
        Document doc = Jsoup.parse(theHTML, baseURL);

        return parseDocument(doc);
    }

    public static String convert(URL url, int timeoutMillis) throws IOException {
        Document doc = Jsoup.parse(url, timeoutMillis);

        return parseDocument(doc);
    }

    public static String convertHtml(String html, String charset) throws IOException {
        Document doc = Jsoup.parse(html, charset);

        return parseDocument(doc);
    }

    public static String convertFile(File file, String charset) throws IOException {
        Document doc = Jsoup.parse(file, charset);
        return parseDocument(doc);
    }

    private static String parseDocument(Document dirtyDoc) {
        indentation = -1;

        String title = dirtyDoc.title();

        Whitelist whitelist = Whitelist.relaxed();
        Cleaner cleaner = new Cleaner(whitelist);

        Document doc = cleaner.clean(dirtyDoc);
        doc.outputSettings().escapeMode(EscapeMode.xhtml);

        if (!title.trim().equals("")) {
            return "# " + title + "\n\n" + getTextContent(doc);
        } else {
            return getTextContent(doc);
        }
    }

    public static String getTextContent(Element element) {
        return getTextContent(element, false);
    }

    private static String getTextContent(Element element, boolean codeBlock) {
        ArrayList<MDLine> lines = new ArrayList<MDLine>();
        String s1 = element.toString();
        if (s1.startsWith("<div class=\"toc\">")) {
            return CATALOG;
        }
        List<Node> children = element.childNodes();
        for (Node child : children) {
            if (child instanceof TextNode) {
                TextNode textNode = (TextNode) child;
                MDLine line = getLastLine(lines);
                if (line.getContent().equals("")) {
                    if (!textNode.isBlank()) {
                        if (codeBlock) {
                            line.append(textNode.getWholeText().replaceAll("#", "/#").replaceAll("\\*", "/\\*"));
                        } else {
                            line.append(textNode.text().replaceAll("#", "/#").replaceAll("\\*", "/\\*"));
                        }
                    }
                } else {
                    line.append(textNode.text().replaceAll("#", "/#").replaceAll("\\*", "/\\*"));
                }

            } else if (child instanceof Element) {
                Element childElement = (Element) child;
                processElement(childElement, lines);
            } else {
            }
        }

        int blankLines = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).toString().trim();
            if (line.equals("")) {
                blankLines++;
            } else {
                blankLines = 0;
            }
            if (blankLines < 2) {
                result.append(line);
                if (i < lines.size() - 1) {
                    result.append("\n");
                }
            }
        }
        return result.toString().replaceAll("\\[]\\(\\)", "");
    }

    private static String getCodeContent(Element element) {
        String s = element.toString();
        String s1 = s.replaceAll("</span>", "");
        s1 = s1.replaceAll("<pre>", "");
        s1 = s1.replaceAll("</pre>", "");
        s1 = s1.replaceAll("<span>", "");
        s1 = s1.replaceAll("</code>", "");
        s1 = s1.replaceAll("<code>", "");
        s1 = s1.replaceAll("<code class=\"(\\S|\\s)*?\">", "");
        s1 = s1.replaceAll("</code>", "");
        s1 = s1.replaceAll("<span class=\"(\\S|\\s)*?\">", "");
//        s1 = s1.replaceAll("<!--(\\S|\\s)*?-->", "");
        s1 = s1.replaceAll("<!-- -->", "");
        s1 = s1.replaceAll("&lt;", "<");
        s1 = s1.replaceAll("&gt;", ">");
        StringBuilder sb = new StringBuilder();
        String[] split = s1.split("\n");
        for (String s2 : split) {
            String temp = s2.trim();
            if (!temp.equals("")) {
                sb.append(s2).append("\n");
            }
        }
        return sb.toString();
    }

    private static void processElement(Element element, ArrayList<MDLine> lines) {
        Tag tag = element.tag();

        String tagName = tag.getName();
        if (tagName.equals("div")) {
            div(element, lines);
        } else if (tagName.equals("p")) {
            p(element, lines);
        } else if (tagName.equals("br")) {
            br(lines);
        } else if (tagName.matches("^h[0-9]+$")) {
            h(element, lines);
        } else if (tagName.equals("strong") || tagName.equals("b")) {
            strong(element, lines);
        } else if (tagName.equals("em")) {
            em(element, lines);
        } else if (tagName.equals("hr")) {
            hr(lines);
        } else if (tagName.equals("a")) {
            a(element, lines);
        } else if (tagName.equals("img")) {
            img(element, lines);
        } else if (tagName.equals("code")) {
            code(element, lines);
        } else if (tagName.equals("ul")) {
            ul(element, lines);
        } else if (tagName.equals("ol")) {
            ol(element, lines);
        } else if (tagName.equals("li")) {
            li(element, lines);
        } else if (tagName.equals("pre")) {
            // 个人添加
            code(element, lines);
        } else if (tagName.equals("thead")) {
            thead(element, lines);
        } else if (tagName.equals("tbody")) {
            tbody(element, lines);
        } else {
            MDLine line = getLastLine(lines);
            line.append(getTextContent(element));
        }
    }


    private static MDLine getLastLine(ArrayList<MDLine> lines) {
        MDLine line;
        if (lines.size() > 0) {
            line = lines.get(lines.size() - 1);
        } else {
            line = new MDLine(MDLine.MDLineType.None, 0, "");
            lines.add(line);
        }

        return line;
    }

    private static void div(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        String content = getTextContent(element);
        if (!content.equals("")) {
            if (!line.getContent().trim().equals("")) {
                lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
                lines.add(new MDLine(MDLine.MDLineType.None, 0, content));
                lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
            } else {
                if (!content.trim().equals("")) {
                    line.append(content);
                }
            }
        }
    }

    private static void table(Element element, ArrayList<MDLine> lines) {
        String tableStr = element.toString();
        List<Node> children = element.childNodes();
        for (Node child : children) {
            if (child instanceof Element) {
                Element childElement = (Element) child;
                String tagName = childElement.tagName();
                if (tagName.equals("thead")) {
                    thead(element, lines);
                }
                if (tagName.equals("tbody")) {
                    thead(element, lines);
                }
            }
        }
        System.out.println(tableStr);
    }

    private static void thead(Element element, ArrayList<MDLine> lines) {
        List<Node> children = element.childNodes();
        for (Node child : children) {
            if (child instanceof Element) {
                Element childElement = (Element) child;
                tr(childElement, lines, true);
            }
        }
    }

    private static void tbody(Element element, ArrayList<MDLine> lines) {
        List<Node> children = element.childNodes();
        for (Node child : children) {
            if (child instanceof Element) {
                Element childElement = (Element) child;
                tr(childElement, lines, false);
            }
        }
    }

    private static void tr(Element element, ArrayList<MDLine> lines, boolean appendFlag) {
        List<Node> childrenList = element.childNodes();
        StringBuilder sb = new StringBuilder();
        for (Node node : childrenList) {
            if (node instanceof Element) {
                Element childElement = (Element) node;
                sb.append("|").append(getTextContent(childElement));
            }
        }
        if (sb.length() > 0) {
            sb.append("|");
            if (appendFlag) {
                sb.append("\n");
                for (Node node : childrenList) {
                    if (node instanceof Element) {
                        sb.append("|").append("---");
                    }
                }
                sb.append("|");
            }

            MDLine line = new MDLine(MDLine.MDLineType.None, 0, sb.toString());
            lines.add(line);
        }
    }

    private static void p(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        if (!line.getContent().trim().equals("")) {
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        }
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, getTextContent(element).replaceAll("&nbsp;", "")));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        if (!line.getContent().trim().equals("")) {
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        }
    }

    private static void br(ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        if (!line.getContent().trim().equals("")) {
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        }
    }

    private static void h(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        if (!line.getContent().trim().equals("")) {
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        }

        int level = Integer.valueOf(element.tagName().substring(1));
        switch (level) {
            case 1:
                lines.add(new MDLine(MDLine.MDLineType.Head1, 0, getTextContent(element)));
                break;
            case 2:
                lines.add(new MDLine(MDLine.MDLineType.Head2, 0, getTextContent(element)));
                break;
            default:
                lines.add(new MDLine(MDLine.MDLineType.Head3, 0, getTextContent(element)));
                break;
        }

        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void strong(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        line.append("**");
        line.append(getTextContent(element));
        line.append("** ");
    }

    private static void em(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        line.append("*");
        line.append(getTextContent(element));
        line.append("*");
    }

    private static void hr(ArrayList<MDLine> lines) {
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.HR, 0, ""));
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void a(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);
        line.append("[");
        line.append(getTextContent(element));
        line.append("]");
        line.append("(");
        String url = element.attr("href");
        line.append(url);
        String title = element.attr("title");
        if (!title.equals("")) {
            line.append(" \"");
            line.append(title);
            line.append("\"");
        }
        line.append(")");
    }

    private static void img(Element element, ArrayList<MDLine> lines) {
        MDLine line = getLastLine(lines);

        line.append("![");
        String alt = element.attr("alt");
        line.append(alt);
        line.append("]");
        line.append("(");
        String url = element.attr("src");
        line.append(url);
        String title = element.attr("title");
        if (!title.equals("")) {
            line.append(" \"");
            line.append(title);
            line.append("\"");
        }
        line.append(")");
    }

    private static void code(Element element, ArrayList<MDLine> lines) {

        String codeContent = getCodeContent(element).replace("/#", "#");
        String code = codeContent.trim();
        if (code.contains("\n")) {
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
            MDLine line = new MDLine(MDLine.MDLineType.None, 0, "    ");
            code = "```\n" + code + "\n```";
            line.append(code);
            lines.add(line);
            lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        } else {
            MDLine line = getLastLine(lines);
            code = "`" + code + "`";
            line.append(code);
        }
    }

    private static void ul(Element element, ArrayList<MDLine> lines) {
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        indentation++;
        orderedList = false;
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        line.append(getTextContent(element));
        lines.add(line);
        indentation--;
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void ol(Element element, ArrayList<MDLine> lines) {
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
        indentation++;
        orderedList = true;
        MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
        line.append(getTextContent(element));
        lines.add(line);
        indentation--;
        lines.add(new MDLine(MDLine.MDLineType.None, 0, ""));
    }

    private static void li(Element element, ArrayList<MDLine> lines) {
        MDLine line;
        if (orderedList) {
            line = new MDLine(MDLine.MDLineType.Ordered, indentation,
                    getTextContent(element));
        } else {
            line = new MDLine(MDLine.MDLineType.Unordered, indentation,
                    getTextContent(element));
        }
        lines.add(line);
    }

    private static void pre(Element element, ArrayList<MDLine> lines) {
        Elements pre = element.select("pre");
        for (Element item : pre) {
            Elements code = item.children();
            for (Element child : code) {
                processElement(child, lines);
            }
            MDLine line = new MDLine(MDLine.MDLineType.None, 0, "");
            line.append("");
            lines.add(line);
            lines.add(new MDLine(MDLine.MDLineType.None, 0, "\n\t"));
        }

    }
}