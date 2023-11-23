
#1.sitdown工具 本地文件浏览器打开，复制网页源码HTML，选择对应平台转换。 或者对应网址：https://editor.mdnice.com/

![](https://img2023.cnblogs.com/blog/1694759/202311/1694759-20231123114207315-1244587606.png)

#2.本地Run方试运行

#3. Jsoup使用指南 https://www.cnblogs.com/offerwx/p/16623724.html


# [JSOUP使用](https://www.cnblogs.com/offerwx/p/16623724.html)

# JSOUP各种方法使用与场景

## 解析页面（获取页面内容）

### 您在 Java 字符串中有 HTML，并且您希望解析该 HTML 以获取其内容，或确保其格式正确，或对其进行修改。字符串可能来自用户输入、文件或网络。

Jsoup.parse(String html)方法
这个方法可以从数据库中拿到html格式的字符串然后二次解析
这个地方需要注意 如果不是完整的html字符串的话 Jsoup.parse 会自动补全 与 标签

```
// 完整的html
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>我是完整的html代码.</p></body></html>";
        Document doc = Jsoup.parse(html);
        // 不完整的html
        String html1 = "<a>我只是一段html代码.</a>";
        Document doc1 = Jsoup.parse(html1);
        System.out.println("====================================");
```

### 您有一个要解析的正文 HTML 片段（例如，div包含几个标签；与完整的 HTML 文档相反）。p也许它是由用户提交评论或在 CMS 中编辑页面正文提供的。

使用Jsoup.parseBodyFragment(String html)方法。
这个方法其实也会补全页面标签 会自动补全 与 body>

```
String html3 = "<div><p>Lorem ipsum.</p>";
        Document doc3 = Jsoup.parseBodyFragment(html);
        Element body = doc3.body();
```

### 使用jsoup解析网络地址

Jsoup.connect()

```
Document doc4 = Jsoup.connect("http://example.com/").get();
        String title = doc.title();
        System.out.println("==========================");
```

### 使用jsoup解析网络地址(带参数)

Jsoup.connect()
使用参数可以解决指定请求参数 或这个token认证问题

```
Document doc5 = Jsoup.connect("http://example.com")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
```

### 您在磁盘上有一个包含 HTML 的文件，您希望加载和解析该文件，然后可能从中操作或提取数据。

```
File input = new File("/tmp/input.html");
Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
```

## 解析页面（获取元素信息）

### 您有一个要从中提取数据的 HTML 文档。您大致了解 HTML 文档的结构

在将HTML解析为Document.

```
File input = new File("/tmp/input.html");
Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
Element content = doc.getElementById("content");
Elements links = content.getElementsByTag("a");
for (Element link : links) {
  String linkHref = link.attr("href");
  String linkText = link.text();
}
```

```
元素提供了一系列类似 DOM 的方法来查找元素，并提取和操作它们的数据。DOM getter 是上下文相关的：在父 Document 上调用它们会在文档下找到匹配的元素；调用一个子元素，他们会在该子元素下找到元素。通过这种方式，您可以筛选出您想要的数据。
寻找元素
getElementById(String id)  // 根据id获取元素
getElementsByTag(String tag) // 根据tag获取元素
getElementsByClass(String className) // 根据className获取元素
getElementsByAttribute(String key)（及相关方法） // 根据attr获取元素
元素兄弟：siblingElements(), firstElementSibling(), lastElementSibling(); nextElementSibling(),previousElementSibling()
图表：parent(), children(),child(int index)
元素数据
attr(String key)获取和attr(String key, String value)设置属性
attributes()获取所有属性
id(),className()和classNames()
text()获取和text(String value)设置文本内容
html()获取和html(String value)设置内部 HTML 内容
outerHtml()获取外部 HTML 值
data()获取数据内容（例如 ofscript和style标签）
tag()和tagName()
处理 HTML 和文本
append(String html), prepend(String html)
appendText(String text),prependText(String text)
appendElement(String tagName),prependElement(String tagName)
html(String value)
```

### 您想使用 CSS 或类似 jquery 的选择器语法来查找或操作元素。

使用Element.select(String selector)和Elements.select(String selector)方法：

```
File input = new File("/tmp/input.html");
Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
Elements links = doc.select("a[href]"); // a with href
Elements pngs = doc.select("img[src$=.png]");
  // img with src ending .png
Element masthead = doc.select("div.masthead").first();
  // div with class=masthead
Elements resultLinks = doc.select("h3.r > a"); // direct a after h3
```

```
该select方法在Document、Element或 中可用Elements。它是上下文相关的，因此您可以通过从特定元素中进行选择或通过链接选择调用来进行过滤。
Select 返回一个元素列表 (as Elements)，它提供了一系列方法来提取和操作结果。
选择器概述
tagname: 按标签查找元素，例如a
ns|tag: 在命名空间中按标签查找元素，例如fb|name查找<fb:name>元素
#id: 按 ID 查找元素，例如#logo
.class: 按类名查找元素，例如.masthead
[attribute]: 具有属性的元素，例如[href]
[^attr]: 具有属性名称前缀的[^data-]元素，例如查找具有 HTML5 数据集属性的元素
[attr=value]: 具有属性值的元素，例如[width=500]（也可以引用，比如[data-name='launch sequence']）
[attr^=value], [attr$=value], [attr*=value]: 具有以值开头、结尾或包含值的属性的元素，例如[href*=/path/]
[attr~=regex]: 属性值与正则表达式匹配的元素；例如img[src~=(?i)\.(png|jpe?g)]
*: 所有元素，例如*
选择器组合
el#id: 带有 ID 的元素，例如div#logo
el.class：具有类的元素，例如div.masthead
el[attr]: 具有属性的元素，例如a[href]
任何组合，例如a[href].highlight
ancestor child: 继承自祖先的子元素，例如在具有“body”类的块下的任何位置.body p查找元素p
parent > child: 直接从父元素下降的子元素，例如div.content > p查找p元素；并body > *找到body标签的直接子代
siblingA + siblingB: 查找紧跟在同级 A 之前的同级 B 元素，例如div.head + div
siblingA ~ siblingX: 查找在同级 A 之前的同级 X 元素，例如h1 ~ p
el, el, el: 对多个选择器进行分组，找到匹配任何选择器的唯一元素；例如div.masthead, div.logo
伪选择器
:lt(n): 查找兄弟索引（即其在 DOM 树中相对于其父级的位置）小于的元素n；例如td:lt(3)
:gt(n): 查找兄弟索引大于的元素n；例如div p:gt(2)
:eq(n)：查找兄弟索引等于的元素n；例如form input:eq(1)
:has(selector)：查找包含与选择器匹配的元素的元素；例如div:has(p)
:not(selector)：查找与选择器不匹配的元素；例如div:not(.logo)
:contains(text)：查找包含给定文本的元素。搜索不区分大小写；例如p:contains(jsoup)
:containsOwn(text): 查找直接包含给定文本的元素
:matches(regex): 查找文本与指定正则表达式匹配的元素；例如div:matches((?i)login)
:matchesOwn(regex): 查找自身文本与指定正则表达式匹配的元素
请注意，上述索引伪选择器是从 0 开始的，即第一个元素在索引 0 处，第二个在 1 处，依此类推
```

### 解析文档并找到一些元素后，您将想要获取这些元素中的数据。

要获取属性的值，请使用Node.attr(String key)方法
对于元素（及其组合子项）上的文本，请使用Element.text()
对于 HTML，使用Element.html()或Node.outerHtml()视情况而定

```
String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
Document doc = Jsoup.parse(html);
Element link = doc.select("a").first();
String text = doc.body().text(); // "An example link"
String linkHref = link.attr("href"); // "http://example.com/"
String linkText = link.text(); // "example""
String linkOuterH = link.outerHtml(); 
    // "<a href="http://example.com"><b>example</b></a>"
String linkInnerH = link.html(); // "<b>example</b>"
```

```
以上方法是元素数据访问方法的核心。还有其他的：
Element.id()
Element.tagName()
Element.className()和Element.hasClass(String className)
所有这些访问器方法都有相应的设置器方法来更改数据
```

### 您有一个包含相对 URL 的 HTML 文档，您需要将其解析为绝对 URL。

确保在解析文档时指定 a base URI（从 URL 加载时是隐式的），并且
使用abs:属性前缀从属性解析绝对 URL：

```
Document doc = Jsoup.connect("http://jsoup.org").get();
Element link = doc.select("a").first();
String relHref = link.attr("href"); // == "/"
String absHref = link.attr("abs:href"); // "http://jsoup.org/"
```

```
在 HTML 元素中，URL 通常是相对于文档的位置编写的：<a href="/download">...</a>. 当您使用该Node.attr(String key)方法获取 href 属性时，它将按照源 HTML 中指定的方式返回。
如果你想得到一个绝对 URL，有一个属性键前缀abs:会导致属性值被解析为文档的基本 URI（原始位置）：attr("abs:href")
对于这个用例，在解析文档时指定基本 URI 很重要。
如果您不想使用abs:前缀，还有一种方法Node.absUrl(String key)可以做同样的事情，但通过自然属性键访问。
```

# [更多相关的具体的请查看官方文档](https://jsoup.org/cookbook/introduction/parsing-a-document "更多相关的具体的请查看官方文档")

 
 
 [原文地址](https://www.cnblogs.com/offerwx/p/16623724.html) 