package com.trustlife.word.util;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Html2Word {

    public static void htmlToWord(InputStream htmlStream,OutputStream docStream,String encoding) throws Exception {

        String content = getContent(htmlStream);
        //拼一个标准的HTML格式文档
        //String content = "<html><head><style>" + css + "</style></head><body>" + body + "</body></html>";

        InputStream is = new ByteArrayInputStream(content.getBytes(encoding));
        inputStreamToWord(is, docStream);
        System.out.println("word文件创建成功");
    }

    /**
     * 把inputStream写入到对应的word输出流OutputStream中
     * 不考虑异常的捕获，直接抛出
     */
    private static void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem();
        //对应于org.apache.poi.hdf.extractor.WordDocument
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
    }

    /**
     * 把输入流里面的内容以UTF-8当文本取出。
     * 不考虑异常，直接抛出
     */
    private static String getContent(InputStream... ises) throws IOException {
        if (ises != null) {
            StringBuilder result = new StringBuilder();
            BufferedReader br;
            String line;
            for (InputStream is : ises) {
                br = new BufferedReader(new InputStreamReader(is, "GBK"));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
            }
            return result.toString();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        InputStream htmlStream = Files.newInputStream(Paths.get("C:\\Users\\Administrator\\Desktop\\文件\\合同管理\\保险代理业务合作协议（线下总对总）.html"));
        OutputStream docStream = Files.newOutputStream(Paths.get("C:\\Users\\Administrator\\Desktop\\文件\\合同管理\\保险代理业务合作协议（线下总对总）test.doc"));
        htmlToWord(htmlStream,docStream,"GBK");
    }
}
