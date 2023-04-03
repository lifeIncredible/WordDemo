package com.trustlife.word.util;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Word2Html {

    /**
     * 将word2003转换为html文件
     *
     * @param wordPath word文件路径
     * @param wordName word文件名称无后缀
     * @param suffix   word文件后缀
     * @param htmlPath html存储地址
     */
    public static String Word2003ToHtml(String wordPath, String wordName, String suffix, String htmlPath) throws Exception {
        String htmlName = wordName + ".html";
        final String imagePath = htmlPath + "image" + File.separator;
        // 判断html文件是否存在
        File htmlFile = new File(htmlPath + htmlName);
        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }

        // 原word文档
        final String file = wordPath + File.separator + wordName + suffix;
        InputStream input = Files.newInputStream(new File(file).toPath());

        HWPFDocument wordDocument = new HWPFDocument(input);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = documentBuilderFactory.newDocumentBuilder().newDocument();
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);

        // 设置图片存放的位置
        wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> {
            File imgPath = new File(imagePath);
            // 图片目录不存在则创建
            if (!imgPath.exists()) {
                imgPath.mkdirs();
            }
            File file1 = new File(imagePath + suggestedName);
            try {
                OutputStream os = Files.newOutputStream(file1.toPath());
                os.write(content);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 图片在html文件上的路径 相对路径
            return "image/" + suggestedName;
        });

        // 解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        // 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        OutputStream outStream = Files.newOutputStream(htmlFile.toPath());
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        return htmlFile.getAbsolutePath();
    }

    /**
     * 2007版本word转换成html
     *
     * @param wordPath word文件路径
     * @param wordName word文件名称无后缀
     * @param suffix   word文件后缀
     * @param htmlPath html存储地址
     * @return
     * @throws IOException
     */
    public static String Word2007ToHtml(String wordPath, String wordName, String suffix, String htmlPath) throws IOException {

        String htmlName = wordName + ".html";
        String imagePath = htmlPath + "image" + File.separator;

        // 判断Html文件是否已生成过
        File htmlFile = new File(htmlPath + htmlName);
        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }

        // word文件
        File wordFile = new File(wordPath + File.separator + wordName + suffix);

        // 1、加载word文档生成 XWPFDocument对象
        InputStream wordInputstream = Files.newInputStream(wordFile.toPath());
        XWPFDocument document = new XWPFDocument(wordInputstream);

        // 2、解析 XHTML配置
        File imgFolder = new File(imagePath);

        XHTMLOptions options = XHTMLOptions.getDefault();
        options.setExtractor(new FileImageExtractor(imgFolder));

        // Html中图片的路径 相对路径
        options.URIResolver(new BasicURIResolver("image"));
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);

        // 3、 将 XWPFDocument转换成XHTML
        // 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        OutputStream out = Files.newOutputStream(htmlFile.toPath());
        XHTMLConverter.getInstance().convert(document, out, options);
        return htmlFile.getAbsolutePath();
    }

    public static void main(String[] args) throws Exception {
        String htmlPath = "C:\\Users\\Administrator\\Desktop\\文件\\合同管理\\";
        String wordPath  = "C:\\Users\\Administrator\\Desktop\\文件\\合同管理\\";
        String wordName  = "保险代理业务合作协议（线下总对总）";
        String wordSuffix  = ".doc";

        String path = Word2003ToHtml(wordPath,wordName,wordSuffix,htmlPath); //Word2007ToHtml(wordPath, wordName,wordSuffix, htmlPath);
        System.out.println(path);
    }
}