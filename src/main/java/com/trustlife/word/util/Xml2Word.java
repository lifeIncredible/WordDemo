package com.trustlife.word;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Xml2Word {


    private static void buildDoc(TestUser user, OutputStream outputStream) throws IOException {

        XWPFDocument document = new XWPFDocument();// 创建Word文件
        XWPFParagraph paragraph = document.createParagraph();//创建段落
        XWPFRun run = paragraph.createRun();//创建段落文本
        run.setText(">>人员信息查询-详情");//文本内容
        run.setBold(true);//加粗
        run.setFontSize(14);//字体大小

        //创建段落
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setText("人员基本信息");
        run1.setBold(true);
        run1.setFontSize(12);

        //创建表格
        XWPFTable table = document.createTable(6, 4);
        //表格居中，还有其他
        table.setTableAlignment(TableRowAlign.CENTER);
        //遍历表格 设置每一列的 宽度  共4列
        for (XWPFTableRow row : table.getRows()) {
            row.getCell(0).setWidthType(TableWidthType.DXA);
            row.getCell(0).setWidth("1500");
            row.getCell(1).setWidthType(TableWidthType.DXA);
            row.getCell(1).setWidth("1500");
            row.getCell(2).setWidthType(TableWidthType.DXA);
            row.getCell(2).setWidth("2000");
            row.getCell(3).setWidthType(TableWidthType.DXA);
            row.getCell(3).setWidth("2000");
        }
        //第一行。第二列和第四列可以设置从库里查询的数据。b
        table.getRow(0).getCell(0).setText("面试人员：");
        table.getRow(0).getCell(1).setText(user.getName());
        table.getRow(0).getCell(2).setText("性别：");
        table.getRow(0).getCell(3).setText(user.getSex());
        //第二行
        table.getRow(1).getCell(0).setText("手机号：");
        table.getRow(1).getCell(1).setText(user.getPhone());
        table.getRow(1).getCell(2).setText("身份证：");
        table.getRow(1).getCell(3).setText(user.getIdCard());

        //创建段落
        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setText("面试信息");
        run2.setBold(true);
        run2.setFontSize(12);

        //创建表格
        XWPFTable table1 = document.createTable(5, 4);
        table1.setTableAlignment(TableRowAlign.CENTER);

        for (XWPFTableRow row : table1.getRows()) {
            row.getCell(0).setWidthType(TableWidthType.DXA);
            row.getCell(0).setWidth("1500");
            row.getCell(1).setWidthType(TableWidthType.DXA);
            row.getCell(1).setWidth("1500");
            row.getCell(2).setWidthType(TableWidthType.DXA);
            row.getCell(2).setWidth("2000");
            row.getCell(3).setWidthType(TableWidthType.DXA);
            row.getCell(3).setWidth("2000");
        }

        table1.getRow(0).getCell(0).setText("面试岗位：");
        table1.getRow(0).getCell(1).setText(user.getPost());
        table1.getRow(0).getCell(2).setText("级别：");
        table1.getRow(0).getCell(3).setText(user.getLevel());

        XWPFParagraph paragraph3 = document.createParagraph();
        XWPFRun run3 = paragraph3.createRun();
        run3.setText("入职入场信息");
        run3.setBold(true);
        run3.setFontSize(12);

        XWPFTable table2 = document.createTable(8, 4);
        table2.setTableAlignment(TableRowAlign.CENTER);

        for (XWPFTableRow row : table2.getRows()) {
            row.getCell(0).setWidthType(TableWidthType.DXA);
            row.getCell(0).setWidth("1500");
            row.getCell(1).setWidthType(TableWidthType.DXA);
            row.getCell(1).setWidth("1500");
            row.getCell(2).setWidthType(TableWidthType.DXA);
            row.getCell(2).setWidth("2000");
            row.getCell(3).setWidthType(TableWidthType.DXA);
            row.getCell(3).setWidth("2000");
        }

        table2.getRow(0).getCell(0).setText("入职状态：");
        table2.getRow(0).getCell(1).setText(user.getStatus());
        table2.getRow(0).getCell(2).setText("入职部门：");
        table2.getRow(0).getCell(3).setText(user.getDept());

        //获取所有表格
        List<XWPFTable> tables = document.getTables();
        //遍历表格，删除表格所有边框
        tables.forEach(XWPFTable::removeBorders);
        //输出
        document.write(outputStream);
        //关闭流
        outputStream.close();
        System.out.println("word文件创建成功");
    }

    public static void main(String[] args) throws IOException {
        //设置数据
        TestUser user = new TestUser();
        user.setName("小红");
        user.setSex("女");
        user.setPhone("13122222222");
        user.setIdCard("412322222233333333");
        user.setPost("java");
        user.setLevel("初级");
        user.setStatus("未入职");
        user.setDept("银行组");
        //创建输出文件
        File file = new File("C:\\Users\\Administrator\\Desktop\\文件\\合同管理\\testXml.doc");
        //输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        //创建
        buildDoc(user,fileOutputStream);
    }


}
