package com.person.zb.util.mdconvert;


import com.person.zb.util.mdconvert.html2md.H2MConvertUtil;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;

/**
 * @author : ZhouBin
 */
public class H2MRun02 {

    public static void main(String[] args) throws Exception {

        String value = H2MConvertUtil.convertHtml(htmlContent(), "utf-8");
        IOUtils.write(value, new FileOutputStream("D:\\data\\" + "内容测试" + ".md"), "utf-8");
    }

    private static String htmlContent() {

        return "<article class=\"article fmt article-content \">\n" +
                "<h2 id=\"item-1\">源起</h2>\n" +
                "<p>在开发过程中，遇到需要把方法调用改为异步的情况，本来以为简单得加个@Asyn在方法上就行了，没想到项目启动的时候报了如下的错误：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: \n" +
                "Error creating bean with name 'customerServiceImpl': \n" +
                "Bean with name 'customerServiceImpl' has been injected into other beans [customerServiceImpl,followServiceImpl,cupidService] in its raw version as part of a circular reference, \n" +
                "but has eventually been wrapped. This means that said other beans do not use the final version of the bean. \n" +
                "This is often the result of over-eager type matching - consider using 'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-livecodeserver\">Caused <span class=\"hljs-keyword\">by</span>: org.springframework.beans.factory.BeanCurrentlyInCreationException: \n" +
                "Error creating bean <span class=\"hljs-keyword\">with</span> name <span class=\"hljs-string\">'customerServiceImpl'</span>: \n" +
                "Bean <span class=\"hljs-keyword\">with</span> name <span class=\"hljs-string\">'customerServiceImpl'</span> has been injected <span class=\"hljs-keyword\">into</span> other beans [customerServiceImpl,followServiceImpl,cupidService] <span class=\"hljs-keyword\">in</span> its raw <span class=\"hljs-built_in\">version</span> <span class=\"hljs-keyword\">as</span> part <span class=\"hljs-keyword\">of</span> <span class=\"hljs-keyword\">a</span> circular reference, \n" +
                "but has eventually been wrapped. This means that said other beans <span class=\"hljs-built_in\">do</span> <span class=\"hljs-keyword\">not</span> use <span class=\"hljs-keyword\">the</span> final <span class=\"hljs-built_in\">version</span> <span class=\"hljs-keyword\">of</span> <span class=\"hljs-keyword\">the</span> bean. \n" +
                "This is often <span class=\"hljs-keyword\">the</span> <span class=\"hljs-built_in\">result</span> <span class=\"hljs-keyword\">of</span> over-eager type matching - consider <span class=\"hljs-keyword\">using</span> <span class=\"hljs-string\">'getBeanNamesOfType'</span> <span class=\"hljs-keyword\">with</span> <span class=\"hljs-keyword\">the</span> <span class=\"hljs-string\">'allowEagerInit'</span> flag turned off, <span class=\"hljs-keyword\">for</span> example.</pre>\n" +
                "<p>看了下好像报的是循环依赖的错误，但是Spring单例是支持循环依赖的，当时一脸懵逼。<br>拿着报错去百度了下，说是多个动态代理导致的循环依赖报错，也找到了报错的地点，但是还是不明白为什么会这样，所以打算深入源码探个究竟，顺便回顾下Bean的获取流程和循环依赖的内容。</p>\n" +
                "<h2 id=\"item-2\">模拟场景</h2>\n" +
                "<p>用SpringBoot新建一个demo项目，因为原项目是有定义切面的，这里也定义一个切面：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"@Aspect\n" +
                "@Component\n" +
                "public class TestAspect {\n" +
                "\n" +
                "    @Pointcut(&quot;execution(public * com.example.demo.service.CyclicDependencyService.sameClassMethod(..))&quot;)\n" +
                "    private void testPointcut() {}\n" +
                "\n" +
                "    @AfterReturning(&quot;testPointcut()&quot;)\n" +
                "    public void after(JoinPoint point) {\n" +
                "        System.out.println(&quot;在&quot; + point.getSignature() + &quot;之后干点事情&quot;);\n" +
                "    }\n" +
                "\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-less\"><span class=\"hljs-variable\">@Aspect</span>\n" +
                "<span class=\"hljs-variable\">@Component</span>\n" +
                "public class TestAspect {\n" +
                "\n" +
                "    <span class=\"hljs-variable\">@Pointcut</span>(<span class=\"hljs-string\">\"execution(public * com.example.demo.service.CyclicDependencyService.sameClassMethod(..))\"</span>)\n" +
                "    private void testPointcut() {}\n" +
                "\n" +
                "    <span class=\"hljs-variable\">@AfterReturning</span>(<span class=\"hljs-string\">\"testPointcut()\"</span>)\n" +
                "    public void after(JoinPoint point) {\n" +
                "        <span class=\"hljs-selector-tag\">System</span><span class=\"hljs-selector-class\">.out</span><span class=\"hljs-selector-class\">.println</span>(<span class=\"hljs-string\">\"在\"</span> + point.getSignature() + <span class=\"hljs-string\">\"之后干点事情\"</span>);\n" +
                "    }\n" +
                "\n" +
                "}</pre>\n" +
                "<p>然后新建一个注入自己的Service构成循环依赖，然后提供一个方法满足切点要求，并且加上@Async注解：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"@Service\n" +
                "public class CyclicDependencyService {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private CyclicDependencyService cyclicDependencyService;\n" +
                "\n" +
                "    public void test() {\n" +
                "        System.out.println(&quot;调用同类方法&quot;);\n" +
                "        cyclicDependencyService.sameClassMethod();\n" +
                "    }\n" +
                "\n" +
                "    @Async\n" +
                "    public void sameClassMethod() {\n" +
                "        System.out.println(&quot;循环依赖中的异步方法&quot;);\n" +
                "        System.out.println(&quot;方法线程：&quot; + Thread.currentThread().getName());\n" +
                "    }\n" +
                "\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-csharp\">@Service\n" +
                "<span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">CyclicDependencyService</span> {\n" +
                "\n" +
                "    @Autowired\n" +
                "    <span class=\"hljs-keyword\">private</span> CyclicDependencyService cyclicDependencyService;\n" +
                "\n" +
                "    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">test</span>()</span> {\n" +
                "        System.<span class=\"hljs-keyword\">out</span>.println(<span class=\"hljs-string\">\"调用同类方法\"</span>);\n" +
                "        cyclicDependencyService.sameClassMethod();\n" +
                "    }\n" +
                "\n" +
                "    @Async\n" +
                "    <span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">sameClassMethod</span>()</span> {\n" +
                "        System.<span class=\"hljs-keyword\">out</span>.println(<span class=\"hljs-string\">\"循环依赖中的异步方法\"</span>);\n" +
                "        System.<span class=\"hljs-keyword\">out</span>.println(<span class=\"hljs-string\">\"方法线程：\"</span> + Thread.currentThread().getName());\n" +
                "    }\n" +
                "\n" +
                "}</pre>\n" +
                "<p>还有别忘了给Application启动类加上@EnableAsync和@EnableAspectJAutoProxy：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"@EnableAsync\n" +
                "@EnableAspectJAutoProxy\n" +
                "@SpringBootApplication\n" +
                "public class DemoApplication {\n" +
                "    public static void main(String[] args) {\n" +
                "        SpringApplication.run(DemoApplication.class, args);\n" +
                "    }\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-less\"><span class=\"hljs-variable\">@EnableAsync</span>\n" +
                "<span class=\"hljs-variable\">@EnableAspectJAutoProxy</span>\n" +
                "<span class=\"hljs-variable\">@SpringBootApplication</span>\n" +
                "public class DemoApplication {\n" +
                "    <span class=\"hljs-selector-tag\">public</span> <span class=\"hljs-selector-tag\">static</span> <span class=\"hljs-selector-tag\">void</span> <span class=\"hljs-selector-tag\">main</span>(String[] args) {\n" +
                "        <span class=\"hljs-selector-tag\">SpringApplication</span><span class=\"hljs-selector-class\">.run</span>(DemoApplication.class, args);\n" +
                "    }\n" +
                "}</pre>\n" +
                "<p>最后打好断点，开始debug。</p>\n" +
                "<h2 id=\"item-3\">debug</h2>\n" +
                "<p>从Bean创建的的起点--AbstractBeanFactory#getBean开始</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"// Eagerly check singleton cache for manually registered singletons.\n" +
                "Object sharedInstance = getSingleton(beanName);\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-smali\">// Eagerly<span class=\"hljs-built_in\"> check </span>singleton cache for manually registered singletons.\n" +
                "Object sharedInstance = getSingleton(beanName);</pre>\n" +
                "<p>首先会在缓存中查找，DefaultSingletonBeanRegistry#getSingleton(String beanName, boolean allowEarlyReference)：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"protected Object getSingleton(String beanName, boolean allowEarlyReference) {\n" +
                "   Object singletonObject = this.singletonObjects.get(beanName);\n" +
                "   if (singletonObject == null &amp;&amp; isSingletonCurrentlyInCreation(beanName)) {\n" +
                "      synchronized (this.singletonObjects) {\n" +
                "         singletonObject = this.earlySingletonObjects.get(beanName);\n" +
                "         if (singletonObject == null &amp;&amp; allowEarlyReference) {\n" +
                "            ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);\n" +
                "            if (singletonFactory != null) {\n" +
                "               singletonObject = singletonFactory.getObject();\n" +
                "               this.earlySingletonObjects.put(beanName, singletonObject);\n" +
                "               this.singletonFactories.remove(beanName);\n" +
                "            }\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "   return singletonObject;\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-kotlin\"><span class=\"hljs-keyword\">protected</span> Object getSingleton(String beanName, boolean allowEarlyReference) {\n" +
                "   Object singletonObject = <span class=\"hljs-keyword\">this</span>.singletonObjects.<span class=\"hljs-keyword\">get</span>(beanName);\n" +
                "   <span class=\"hljs-keyword\">if</span> (singletonObject == <span class=\"hljs-literal\">null</span> &amp;&amp; isSingletonCurrentlyInCreation(beanName)) {\n" +
                "      synchronized (<span class=\"hljs-keyword\">this</span>.singletonObjects) {\n" +
                "         singletonObject = <span class=\"hljs-keyword\">this</span>.earlySingletonObjects.<span class=\"hljs-keyword\">get</span>(beanName);\n" +
                "         <span class=\"hljs-keyword\">if</span> (singletonObject == <span class=\"hljs-literal\">null</span> &amp;&amp; allowEarlyReference) {\n" +
                "            ObjectFactory&lt;?&gt; singletonFactory = <span class=\"hljs-keyword\">this</span>.singletonFactories.<span class=\"hljs-keyword\">get</span>(beanName);\n" +
                "            <span class=\"hljs-keyword\">if</span> (singletonFactory != <span class=\"hljs-literal\">null</span>) {\n" +
                "               singletonObject = singletonFactory.getObject();\n" +
                "               <span class=\"hljs-keyword\">this</span>.earlySingletonObjects.put(beanName, singletonObject);\n" +
                "               <span class=\"hljs-keyword\">this</span>.singletonFactories.remove(beanName);\n" +
                "            }\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "   <span class=\"hljs-keyword\">return</span> singletonObject;\n" +
                "}</pre>\n" +
                "<p>这里一共有三级缓存：</p>\n" +
                "<ol>\n" +
                "<li>singletonObjects，保存初始化完成的单例bean实例；</li>\n" +
                "<li>earlySingletonObjects，保存提前曝光的单例bean实例；</li>\n" +
                "<li>singletonFactories，保存单例bean的工厂函数对象；</li>\n" +
                "</ol>\n" +
                "<p>后面两级都是为了解决循环依赖设置的，具体查找逻辑在后续其他情况下调用会说明。</p>\n" +
                "<p>缓存中找不到，就要创建单例：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"sharedInstance = getSingleton(beanName, () -> {\n" +
                "   try {\n" +
                "      return createBean(beanName, mbd, args);\n" +
                "   }\n" +
                "   catch (BeansException ex) {\n" +
                "      // Explicitly remove instance from singleton cache: It might have been put there\n" +
                "      // eagerly by the creation process, to allow for circular reference resolution.\n" +
                "      // Also remove any beans that received a temporary reference to the bean.\n" +
                "      destroySingleton(beanName);\n" +
                "      throw ex;\n" +
                "   }\n" +
                "});\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-reasonml\">sharedInstance = get<span class=\"hljs-constructor\">Singleton(<span class=\"hljs-params\">beanName</span>, ()</span> -&gt; {\n" +
                "   <span class=\"hljs-keyword\">try</span> {\n" +
                "      return create<span class=\"hljs-constructor\">Bean(<span class=\"hljs-params\">beanName</span>, <span class=\"hljs-params\">mbd</span>, <span class=\"hljs-params\">args</span>)</span>;\n" +
                "   }\n" +
                "   catch (BeansException ex) {\n" +
                "      <span class=\"hljs-comment\">// Explicitly remove instance from singleton cache: It might have been put there</span>\n" +
                "      <span class=\"hljs-comment\">// eagerly by the creation process, to allow for circular reference resolution.</span>\n" +
                "      <span class=\"hljs-comment\">// Also remove any beans that received a temporary reference to the bean.</span>\n" +
                "      destroy<span class=\"hljs-constructor\">Singleton(<span class=\"hljs-params\">beanName</span>)</span>;\n" +
                "      throw ex;\n" +
                "   }\n" +
                "});</pre>\n" +
                "<p>调用DefaultSingletonBeanRegistry#getSingleton(String beanName, ObjectFactory&lt;?&gt; singletonFactory):</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {\n" +
                "    ...\n" +
                "    beforeSingletonCreation(beanName);\n" +
                "    ...\n" +
                "    singletonObject = singletonFactory.getObject();\n" +
                "    ...\n" +
                "    afterSingletonCreation(beanName);\n" +
                "    ...\n" +
                "    addSingleton(beanName, singletonObject);\n" +
                "    ...\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-reasonml\">public Object get<span class=\"hljs-constructor\">Singleton(String <span class=\"hljs-params\">beanName</span>, ObjectFactory&lt;?&gt; <span class=\"hljs-params\">singletonFactory</span>)</span> {<span class=\"hljs-operator\">\n" +
                "    ...\n" +
                "    </span>before<span class=\"hljs-constructor\">SingletonCreation(<span class=\"hljs-params\">beanName</span>)</span>;<span class=\"hljs-operator\">\n" +
                "    ...\n" +
                "    </span>singletonObject = singletonFactory.get<span class=\"hljs-constructor\">Object()</span>;<span class=\"hljs-operator\">\n" +
                "    ...\n" +
                "    </span>after<span class=\"hljs-constructor\">SingletonCreation(<span class=\"hljs-params\">beanName</span>)</span>;<span class=\"hljs-operator\">\n" +
                "    ...\n" +
                "    </span>add<span class=\"hljs-constructor\">Singleton(<span class=\"hljs-params\">beanName</span>, <span class=\"hljs-params\">singletonObject</span>)</span>;<span class=\"hljs-operator\">\n" +
                "    ...\n" +
                "</span>}</pre>\n" +
                "<p>创建前后分别做了这几件事：</p>\n" +
                "<ol>\n" +
                "<li>前，beanName放入singletonsCurrentlyInCreation，表示单例正在创建中</li>\n" +
                "<li>后，从singletonsCurrentlyInCreation中移除beanName</li>\n" +
                "<li>后，将创建好的bean放入singletonObjects，移除在singletonFactories和earlySingletonObjects的对象</li>\n" +
                "</ol>\n" +
                "<p>创建单例调用getSingleton时传入的工厂函数对象的getObject方法，实际上就是createBean方法，主要逻辑在AbstractAutowireCapableBeanFactory#doCreateBean中：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"...\n" +
                "instanceWrapper = createBeanInstance(beanName, mbd, args);\n" +
                "final Object bean = instanceWrapper.getWrappedInstance();\n" +
                "...\n" +
                "// Eagerly cache singletons to be able to resolve circular references\n" +
                "// even when triggered by lifecycle interfaces like BeanFactoryAware.\n" +
                "boolean earlySingletonExposure = (mbd.isSingleton() &amp;&amp; this.allowCircularReferences &amp;&amp;\n" +
                "      isSingletonCurrentlyInCreation(beanName));\n" +
                "if (earlySingletonExposure) {\n" +
                "   if (logger.isTraceEnabled()) {\n" +
                "      logger.trace(&quot;Eagerly caching bean '&quot; + beanName +\n" +
                "            &quot;' to allow for resolving potential circular references&quot;);\n" +
                "   }\n" +
                "   addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));\n" +
                "}\n" +
                "// Initialize the bean instance.\n" +
                "Object exposedObject = bean;\n" +
                "try {\n" +
                "   populateBean(beanName, mbd, instanceWrapper);\n" +
                "   exposedObject = initializeBean(beanName, exposedObject, mbd);\n" +
                "}\n" +
                "...\n" +
                "if (earlySingletonExposure) {\n" +
                "   Object earlySingletonReference = getSingleton(beanName, false);\n" +
                "   if (earlySingletonReference != null) {\n" +
                "      if (exposedObject == bean) {\n" +
                "         exposedObject = earlySingletonReference;\n" +
                "      }\n" +
                "      else if (!this.allowRawInjectionDespiteWrapping &amp;&amp; hasDependentBean(beanName)) {\n" +
                "         String[] dependentBeans = getDependentBeans(beanName);\n" +
                "         Set<String> actualDependentBeans = new LinkedHashSet<>(dependentBeans.length);\n" +
                "         for (String dependentBean : dependentBeans) {\n" +
                "            if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {\n" +
                "               actualDependentBeans.add(dependentBean);\n" +
                "            }\n" +
                "         }\n" +
                "         if (!actualDependentBeans.isEmpty()) {\n" +
                "            throw new BeanCurrentlyInCreationException(beanName,\n" +
                "                  &quot;Bean with name '&quot; + beanName + &quot;' has been injected into other beans [&quot; +\n" +
                "                  StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +\n" +
                "                  &quot;] in its raw version as part of a circular reference, but has eventually been &quot; +\n" +
                "                  &quot;wrapped. This means that said other beans do not use the final version of the &quot; +\n" +
                "                  &quot;bean. This is often the result of over-eager type matching - consider using &quot; +\n" +
                "                  &quot;'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.&quot;);\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-livescript\">...\n" +
                "instanceWrapper = createBeanInstance(beanName, mbd, args);\n" +
                "final <span class=\"hljs-built_in\">Object</span> bean = instanceWrapper.getWrappedInstance();\n" +
                "...\n" +
                "<span class=\"hljs-regexp\">// Eagerly cache singletons to be able to resolve circular references\n" +
                "//</span> even <span class=\"hljs-keyword\">when</span> triggered <span class=\"hljs-keyword\">by</span> lifecycle interfaces like BeanFactoryAware.\n" +
                "boolean earlySingletonExposure = (mbd.isSingleton() &amp;&amp; this.allowCircularReferences &amp;&amp;\n" +
                "      isSingletonCurrentlyInCreation(beanName));\n" +
                "<span class=\"hljs-keyword\">if</span> (earlySingletonExposure) {\n" +
                "   <span class=\"hljs-keyword\">if</span> (logger.isTraceEnabled()) {\n" +
                "      logger.trace(<span class=\"hljs-string\">\"Eagerly caching bean '\"</span> + beanName +\n" +
                "            <span class=\"hljs-string\">\"' to allow for resolving potential circular references\"</span>);\n" +
                "   }\n" +
                "   addSingletonFactory<span class=\"hljs-function\"><span class=\"hljs-params\">(beanName, () -&gt; getEarlyBeanReference(beanName, mbd, bean))</span>;\n" +
                "}\n" +
                "// <span class=\"hljs-title\">Initialize</span> <span class=\"hljs-title\">the</span> <span class=\"hljs-title\">bean</span> <span class=\"hljs-title\">instance</span>.\n" +
                "<span class=\"hljs-title\">Object</span> <span class=\"hljs-title\">exposedObject</span> = <span class=\"hljs-title\">bean</span>;\n" +
                "<span class=\"hljs-title\">try</span> {\n" +
                "   <span class=\"hljs-title\">populateBean</span><span class=\"hljs-params\">(beanName, mbd, instanceWrapper)</span>;\n" +
                "   <span class=\"hljs-title\">exposedObject</span> = <span class=\"hljs-title\">initializeBean</span><span class=\"hljs-params\">(beanName, exposedObject, mbd)</span>;\n" +
                "}\n" +
                "...\n" +
                "<span class=\"hljs-title\">if</span> <span class=\"hljs-params\">(earlySingletonExposure)</span> {\n" +
                "   <span class=\"hljs-title\">Object</span> <span class=\"hljs-title\">earlySingletonReference</span> = <span class=\"hljs-title\">getSingleton</span><span class=\"hljs-params\">(beanName, <span class=\"hljs-literal\">false</span>)</span>;\n" +
                "   <span class=\"hljs-title\">if</span> <span class=\"hljs-params\">(earlySingletonReference != <span class=\"hljs-literal\">null</span>)</span> {\n" +
                "      <span class=\"hljs-title\">if</span> <span class=\"hljs-params\">(exposedObject == bean)</span> {\n" +
                "         <span class=\"hljs-title\">exposedObject</span> = <span class=\"hljs-title\">earlySingletonReference</span>;\n" +
                "      }\n" +
                "      <span class=\"hljs-title\">else</span> <span class=\"hljs-title\">if</span> <span class=\"hljs-params\">(!this.allowRawInjectionDespiteWrapping &amp;&amp; hasDependentBean(beanName))</span> {\n" +
                "         <span class=\"hljs-title\">String</span>[] <span class=\"hljs-title\">dependentBeans</span> = <span class=\"hljs-title\">getDependentBeans</span><span class=\"hljs-params\">(beanName)</span>;\n" +
                "         <span class=\"hljs-title\">Set</span>&lt;<span class=\"hljs-title\">String</span>&gt; <span class=\"hljs-title\">actualDependentBeans</span> = <span class=\"hljs-title\">new</span> <span class=\"hljs-title\">LinkedHashSet</span>&lt;&gt;<span class=\"hljs-params\">(dependentBeans.length)</span>;\n" +
                "         <span class=\"hljs-title\">for</span> <span class=\"hljs-params\">(<span class=\"hljs-built_in\">String</span> dependentBean : dependentBeans)</span> {\n" +
                "            <span class=\"hljs-title\">if</span> <span class=\"hljs-params\">(!removeSingletonIfCreatedForTypeCheckOnly(dependentBean))</span> {\n" +
                "               <span class=\"hljs-title\">actualDependentBeans</span>.<span class=\"hljs-title\">add</span><span class=\"hljs-params\">(dependentBean)</span>;\n" +
                "            }\n" +
                "         }\n" +
                "         <span class=\"hljs-title\">if</span> <span class=\"hljs-params\">(!actualDependentBeans.isEmpty())</span> {\n" +
                "            <span class=\"hljs-title\">throw</span> <span class=\"hljs-title\">new</span> <span class=\"hljs-title\">BeanCurrentlyInCreationException</span><span class=\"hljs-params\">(beanName,\n" +
                "                  <span class=\"hljs-string\">\"Bean with name '\"</span> + beanName + <span class=\"hljs-string\">\"' has been injected into other beans [\"</span> +\n" +
                "                  StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +\n" +
                "                  <span class=\"hljs-string\">\"] in its raw version as part of a circular reference, but has eventually been \"</span> +\n" +
                "                  <span class=\"hljs-string\">\"wrapped. This means that said other beans do not use the final version of the \"</span> +\n" +
                "                  <span class=\"hljs-string\">\"bean. This is often the result of over-eager type matching - consider using \"</span> +\n" +
                "                  <span class=\"hljs-string\">\"'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.\"</span>)</span>;\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "}</span></pre>\n" +
                "<p>可以看到报错就是在这个方法里抛出的，那么这个方法就是重点中的重点。</p>\n" +
                "<p>首先实例化单例，instantiate，只是实例化获取对象引用，还没有注入依赖。我debug时记录的bean对象是<code>CyclicDependencyService@4509</code>；</p>\n" +
                "<p>然后判断bean是否需要提前暴露，需要满足三个条件：1、是单例；2、支持循环依赖；3、bean正在创建中，也就是到前面提到的singletonsCurrentlyInCreation中能查找到，全满足的话就会调用DefaultSingletonBeanRegistry#addSingletonFactory把beanName和单例工厂函数对象（匿名实现调用AbstractAutowireCapableBeanFactory#getEarlyBeanReference方法）放入singletonFactories；</p>\n" +
                "<p>接着就是注入依赖，填充属性，具体怎么注入这里就不展开了，最后会为属性cyclicDependencyService调用DefaultSingletonBeanRegistry.getSingleton(beanName, true)，注意这里和最开始的那次调用不一样，isSingletonCurrentlyInCreation为true，就会在singletonFactories中找到bean的单例工厂函数对象，也就是在上一步提前暴露时放入的，然后调用它的匿名实现AbstractAutowireCapableBeanFactory#getEarlyBeanReference：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {\n" +
                "   Object exposedObject = bean;\n" +
                "   if (!mbd.isSynthetic() &amp;&amp; hasInstantiationAwareBeanPostProcessors()) {\n" +
                "      for (BeanPostProcessor bp : getBeanPostProcessors()) {\n" +
                "         if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {\n" +
                "            SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;\n" +
                "            exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "   return exposedObject;\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-reasonml\">protected Object get<span class=\"hljs-constructor\">EarlyBeanReference(String <span class=\"hljs-params\">beanName</span>, RootBeanDefinition <span class=\"hljs-params\">mbd</span>, Object <span class=\"hljs-params\">bean</span>)</span> {\n" +
                "   Object exposedObject = bean;\n" +
                "   <span class=\"hljs-keyword\">if</span> (!mbd.is<span class=\"hljs-constructor\">Synthetic()</span><span class=\"hljs-operator\"> &amp;&amp; </span>has<span class=\"hljs-constructor\">InstantiationAwareBeanPostProcessors()</span>) {\n" +
                "      <span class=\"hljs-keyword\">for</span> (BeanPostProcessor bp : get<span class=\"hljs-constructor\">BeanPostProcessors()</span>) {\n" +
                "         <span class=\"hljs-keyword\">if</span> (bp instanceof SmartInstantiationAwareBeanPostProcessor) {\n" +
                "            SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;\n" +
                "            exposedObject = ibp.get<span class=\"hljs-constructor\">EarlyBeanReference(<span class=\"hljs-params\">exposedObject</span>, <span class=\"hljs-params\">beanName</span>)</span>;\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "   return exposedObject;\n" +
                "}</pre>\n" +
                "<p>方法逻辑就是挨个调用实现了SmartInstantiationAwareBeanPostProcessor接口的后置处理器（以下简称BBP）的getEarlyBeanReference方法。一个一个debug下来，其他都是原样返回bean，只有AnnotationAwareAspectJAutoProxyCreator会把原bean（<code>CyclicDependencyService@4509</code>）存在earlyProxyReferences，然后将bean的代理返回（debug时记录的返回对象是<code>CyclicDependencyService$$EnhancerBySpringCGLIB$$6ed9e2db@4740</code>）并放入earlySingletonObjects，再赋给属性cyclicDependencyService。</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"public Object getEarlyBeanReference(Object bean, String beanName) {\n" +
                "   Object cacheKey = getCacheKey(bean.getClass(), beanName);\n" +
                "   this.earlyProxyReferences.put(cacheKey, bean);\n" +
                "   return wrapIfNecessary(bean, beanName, cacheKey);\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-mipsasm\">public Object getEarlyBeanReference(Object <span class=\"hljs-keyword\">bean, </span>String <span class=\"hljs-keyword\">beanName) </span>{\n" +
                "   Object <span class=\"hljs-keyword\">cacheKey </span>= getCacheKey(<span class=\"hljs-keyword\">bean.getClass(), </span><span class=\"hljs-keyword\">beanName);\n" +
                "</span>   this.earlyProxyReferences.put(<span class=\"hljs-keyword\">cacheKey, </span><span class=\"hljs-keyword\">bean);\n" +
                "</span>   return wrapIfNecessary(<span class=\"hljs-keyword\">bean, </span><span class=\"hljs-keyword\">beanName, </span><span class=\"hljs-keyword\">cacheKey);\n" +
                "</span>}</pre>\n" +
                "<p>属性填充完成后就是调用初始化方法AbstractAutowireCapableBeanFactory#initializeBean：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"...\n" +
                "invokeAwareMethods(beanName, bean);\n" +
                "...\n" +
                "wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);\n" +
                "...\n" +
                "invokeInitMethods(beanName, wrappedBean, mbd);\n" +
                "...\n" +
                "wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);\n" +
                "...\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-reasonml\">...\n" +
                "invoke<span class=\"hljs-constructor\">AwareMethods(<span class=\"hljs-params\">beanName</span>, <span class=\"hljs-params\">bean</span>)</span>;<span class=\"hljs-operator\">\n" +
                "...\n" +
                "</span>wrappedBean = apply<span class=\"hljs-constructor\">BeanPostProcessorsBeforeInitialization(<span class=\"hljs-params\">wrappedBean</span>, <span class=\"hljs-params\">beanName</span>)</span>;<span class=\"hljs-operator\">\n" +
                "...\n" +
                "</span>invoke<span class=\"hljs-constructor\">InitMethods(<span class=\"hljs-params\">beanName</span>, <span class=\"hljs-params\">wrappedBean</span>, <span class=\"hljs-params\">mbd</span>)</span>;<span class=\"hljs-operator\">\n" +
                "...\n" +
                "</span>wrappedBean = apply<span class=\"hljs-constructor\">BeanPostProcessorsAfterInitialization(<span class=\"hljs-params\">wrappedBean</span>, <span class=\"hljs-params\">beanName</span>)</span>;\n" +
                "...</pre>\n" +
                "<p>初始化主要分为这几步：</p>\n" +
                "<ol>\n" +
                "<li>如果bean实现了BeanNameAware、BeanClassLoaderAware或BeanFactoryAware，把相应的资源放入bean；</li>\n" +
                "<li>顺序执行BBP的postProcessBeforeInitialization方法；</li>\n" +
                "<li>如果实现了InitializingBean就执行afterPropertiesSet方法，然后执行自己的init-method；</li>\n" +
                "<li>顺序执行BBP的postProcessAfterInitialization。</li>\n" +
                "</ol>\n" +
                "<p>debug的时候发现是第4步改变了bean，先执行AnnotationAwareAspectJAutoProxyCreator#postProcessAfterInitialization：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {\n" +
                "   if (bean != null) {\n" +
                "      Object cacheKey = getCacheKey(bean.getClass(), beanName);\n" +
                "      if (this.earlyProxyReferences.remove(cacheKey) != bean) {\n" +
                "         return wrapIfNecessary(bean, beanName, cacheKey);\n" +
                "      }\n" +
                "   }\n" +
                "   return bean;\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-mipsasm\">public Object postProcessAfterInitialization(@Nullable Object <span class=\"hljs-keyword\">bean, </span>String <span class=\"hljs-keyword\">beanName) </span>{\n" +
                "   if (<span class=\"hljs-keyword\">bean </span>!= null) {\n" +
                "      Object <span class=\"hljs-keyword\">cacheKey </span>= getCacheKey(<span class=\"hljs-keyword\">bean.getClass(), </span><span class=\"hljs-keyword\">beanName);\n" +
                "</span>      if (this.earlyProxyReferences.remove(<span class=\"hljs-keyword\">cacheKey) </span>!= <span class=\"hljs-keyword\">bean) </span>{\n" +
                "         return wrapIfNecessary(<span class=\"hljs-keyword\">bean, </span><span class=\"hljs-keyword\">beanName, </span><span class=\"hljs-keyword\">cacheKey);\n" +
                "</span>      }\n" +
                "   }\n" +
                "   return <span class=\"hljs-keyword\">bean;\n" +
                "</span>}</pre>\n" +
                "<p>这里会获取并移除之前存在earlyProxyReferences的bean（<code>CyclicDependencyService@4509</code>），因为和当前bean是同一个对象，所以什么都没做直接返回。随后会执行AsyncAnnotationBeanPostProcessor#postProcessAfterInitialization：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"if (isEligible(bean, beanName)) {\n" +
                "   ProxyFactory proxyFactory = prepareProxyFactory(bean, beanName);\n" +
                "   if (!proxyFactory.isProxyTargetClass()) {\n" +
                "      evaluateProxyInterfaces(bean.getClass(), proxyFactory);\n" +
                "   }\n" +
                "   proxyFactory.addAdvisor(this.advisor);\n" +
                "   customizeProxyFactory(proxyFactory);\n" +
                "   return proxyFactory.getProxy(getProxyClassLoader());\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-reasonml\"><span class=\"hljs-keyword\">if</span> (is<span class=\"hljs-constructor\">Eligible(<span class=\"hljs-params\">bean</span>, <span class=\"hljs-params\">beanName</span>)</span>) {\n" +
                "   ProxyFactory proxyFactory = prepare<span class=\"hljs-constructor\">ProxyFactory(<span class=\"hljs-params\">bean</span>, <span class=\"hljs-params\">beanName</span>)</span>;\n" +
                "   <span class=\"hljs-keyword\">if</span> (!proxyFactory.is<span class=\"hljs-constructor\">ProxyTargetClass()</span>) {\n" +
                "      evaluate<span class=\"hljs-constructor\">ProxyInterfaces(<span class=\"hljs-params\">bean</span>.<span class=\"hljs-params\">getClass</span>()</span>, proxyFactory);\n" +
                "   }\n" +
                "   proxyFactory.add<span class=\"hljs-constructor\">Advisor(<span class=\"hljs-params\">this</span>.<span class=\"hljs-params\">advisor</span>)</span>;\n" +
                "   customize<span class=\"hljs-constructor\">ProxyFactory(<span class=\"hljs-params\">proxyFactory</span>)</span>;\n" +
                "   return proxyFactory.get<span class=\"hljs-constructor\">Proxy(<span class=\"hljs-params\">getProxyClassLoader</span>()</span>);\n" +
                "}</pre>\n" +
                "<p>先判断bean是否有需要代理，因为CyclicDependencyService有方法带有@Async注解就需要代理，返回代理对象是<code>CyclicDependencyService$$EnhancerBySpringCGLIB$$e66d8f6e@5273</code>。</p>\n" +
                "<p>返回的代理对象赋值给AbstractAutowireCapableBeanFactory#doCreateBean方法内的exposedObject，接下来就到了检查循环依赖的地方了：</p>\n" +
                "<div class=\"widget-codetool\" style=\"display: none;\">\n" +
                "      <div class=\"widget-codetool--inner\">\n" +
                "        <button type=\"button\" class=\"btn btn-dark rounded-0 sflex-center copyCode\" data-toggle=\"tooltip\" data-placement=\"top\" data-clipboard-text=\"if (earlySingletonExposure) {\n" +
                "   Object earlySingletonReference = getSingleton(beanName, false);\n" +
                "   if (earlySingletonReference != null) {\n" +
                "      if (exposedObject == bean) {\n" +
                "         exposedObject = earlySingletonReference;\n" +
                "      }\n" +
                "      else if (!this.allowRawInjectionDespiteWrapping &amp;&amp; hasDependentBean(beanName)) {\n" +
                "         String[] dependentBeans = getDependentBeans(beanName);\n" +
                "         Set<String> actualDependentBeans = new LinkedHashSet<>(dependentBeans.length);\n" +
                "         for (String dependentBean : dependentBeans) {\n" +
                "            if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {\n" +
                "               actualDependentBeans.add(dependentBean);\n" +
                "            }\n" +
                "         }\n" +
                "         if (!actualDependentBeans.isEmpty()) {\n" +
                "            throw new BeanCurrentlyInCreationException(beanName,\n" +
                "                  &quot;Bean with name '&quot; + beanName + &quot;' has been injected into other beans [&quot; +\n" +
                "                  StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +\n" +
                "                  &quot;] in its raw version as part of a circular reference, but has eventually been &quot; +\n" +
                "                  &quot;wrapped. This means that said other beans do not use the final version of the &quot; +\n" +
                "                  &quot;bean. This is often the result of over-eager type matching - consider using &quot; +\n" +
                "                  &quot;'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.&quot;);\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "}\" aria-label=\"复制\" data-bs-original-title=\"复制\">\n" +
                "        <i class=\"far fa-copy\"></i>\n" +
                "        </button>\n" +
                "      </div>\n" +
                "      </div><pre class=\"hljs language-dart\"><span class=\"hljs-keyword\">if</span> (earlySingletonExposure) {\n" +
                "   <span class=\"hljs-built_in\">Object</span> earlySingletonReference = getSingleton(beanName, <span class=\"hljs-keyword\">false</span>);\n" +
                "   <span class=\"hljs-keyword\">if</span> (earlySingletonReference != <span class=\"hljs-keyword\">null</span>) {\n" +
                "      <span class=\"hljs-keyword\">if</span> (exposedObject == bean) {\n" +
                "         exposedObject = earlySingletonReference;\n" +
                "      }\n" +
                "      <span class=\"hljs-keyword\">else</span> <span class=\"hljs-keyword\">if</span> (!<span class=\"hljs-keyword\">this</span>.allowRawInjectionDespiteWrapping &amp;&amp; hasDependentBean(beanName)) {\n" +
                "         <span class=\"hljs-built_in\">String</span>[] dependentBeans = getDependentBeans(beanName);\n" +
                "         <span class=\"hljs-built_in\">Set</span>&lt;<span class=\"hljs-built_in\">String</span>&gt; actualDependentBeans = <span class=\"hljs-keyword\">new</span> LinkedHashSet&lt;&gt;(dependentBeans.length);\n" +
                "         <span class=\"hljs-keyword\">for</span> (<span class=\"hljs-built_in\">String</span> dependentBean : dependentBeans) {\n" +
                "            <span class=\"hljs-keyword\">if</span> (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {\n" +
                "               actualDependentBeans.add(dependentBean);\n" +
                "            }\n" +
                "         }\n" +
                "         <span class=\"hljs-keyword\">if</span> (!actualDependentBeans.isEmpty()) {\n" +
                "            <span class=\"hljs-keyword\">throw</span> <span class=\"hljs-keyword\">new</span> BeanCurrentlyInCreationException(beanName,\n" +
                "                  <span class=\"hljs-string\">\"Bean with name '\"</span> + beanName + <span class=\"hljs-string\">\"' has been injected into other beans [\"</span> +\n" +
                "                  StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +\n" +
                "                  <span class=\"hljs-string\">\"] in its raw version as part of a circular reference, but has eventually been \"</span> +\n" +
                "                  <span class=\"hljs-string\">\"wrapped. This means that said other beans do not use the final version of the \"</span> +\n" +
                "                  <span class=\"hljs-string\">\"bean. This is often the result of over-eager type matching - consider using \"</span> +\n" +
                "                  <span class=\"hljs-string\">\"'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.\"</span>);\n" +
                "         }\n" +
                "      }\n" +
                "   }\n" +
                "}</pre>\n" +
                "<p>首先从earlySingletonObjects里拿到前面属性填充时放入的bean代理（<code>CyclicDependencyService$$EnhancerBySpringCGLIB$$6ed9e2db@4740</code>），不为空的话就比较bean和exposedObject，分别是<code>CyclicDependencyService@4509</code>和<code>CyclicDependencyService$$EnhancerBySpringCGLIB$$e66d8f6e@5273</code>，很明显不是同一个对象，然后会判断allowRawInjectionDespiteWrapping属性和是否有依赖的bean，然后判断这些bean是否是真实依赖的，一旦存在真实依赖的bean，就会抛出BeanCurrentlyInCreationException。</p>\n" +
                "<h2 id=\"item-4\">总结</h2>\n" +
                "<p>总结下Spring解决循环依赖的思路：在创建bean时，对于满足提前曝光条件的单例，会把该单例的工厂函数对象放入三级缓存中的singletonFactories中；然后在填充属性时，如果存在循环依赖，必然会尝试获取该单例，也就是执行之前放入的工厂函数的匿名实现，这时候拿到的有可能是原bean对象，也有可能是被某些BBP处理过返回的代理对象，会放入三级缓存中的earlySingletonObjects中；接着bean开始初始化，结果返回的有可能是原bean对象，也有可能是代理对象；最后对于满足提前曝光的单例，如果真的有提前曝光的动作，就会去检查初始化后的bean对象是不是原bean对象是同一个对象，只有不是的情况下才可能抛出异常。重点就在于<strong>存在循环依赖的情况下，初始化过的bean对象是不是跟原bean是同一个对象</strong>。</p>\n" +
                "<p>从以上的debug过程可以看出，是AsyncAnnotationBeanPostProcessor这个BBP在初始化过程中改变了bean，使得结果bean和原bean不是一个对象，而AnnotationAwareAspectJAutoProxyCreator则是在填充属性获取提前曝光的对象时把原始bean缓存起来，返回代理的bean。然后在初始化时执行它的postProcessAfterInitialization方法时如果传入的bean是之前缓存的原始bean，就直接返回，不进行代理。如果其他BBP也都没有改变bean的话，初始化过后的bean就是跟原始bean是同一个对象，这时就会把提前曝光的对象（代理过的）作为最终生成的bean。</p>\n" +
                "</article>";
    }
}
