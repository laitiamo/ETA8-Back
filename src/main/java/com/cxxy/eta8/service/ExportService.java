package com.cxxy.eta8.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;

public class ExportService {

    public static final ExportService me = new ExportService();

    public static final String BASE_DOWNLOAD_PATH = PathKit.getWebRootPath() + File.separator + "download";
    public static final String XLS_PATH = BASE_DOWNLOAD_PATH + File.separator + "xls";
    public static final String ZIP_PATH = BASE_DOWNLOAD_PATH + File.separator + "zip";

    private ExportService() {

    }

    public File exportStudentAward(List<Record> records) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个工作簿
        HSSFSheet sheet = workbook.createSheet(); // 创建一个工作表
        HSSFRow headerRow = sheet.createRow(0); // 创建一个标题栏

        HSSFCellStyle headerStyle = workbook.createCellStyle(); // 创建单元格样式
        HSSFFont headerFont = workbook.createFont(); // 创建字体对象
        headerFont.setBold(true); // 设置字体加粗
        headerFont.setFontHeightInPoints((short) 10); // 创建字体大小 短整型强转
        headerStyle.setFont(headerFont);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直类型
        headerRow.setHeightInPoints(20f);

        String[] headerTile = {"学号", "姓名", "性别", "年级", "专业", "班级", "奖项级别", "奖项名称", "获奖名次", "获奖时间"};
        for (int i = 0; i < headerTile.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headerTile[i]);
            sheet.setColumnWidth(i, 20 * 256); // 列宽
        }
        for (int i = 1; i <= records.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i);
            Record r = records.get(i - 1);
            dataRow.createCell(0).setCellValue(r.getStr("username"));
            dataRow.createCell(1).setCellValue(r.getStr("name"));
            dataRow.createCell(2).setCellValue(r.getStr("genderName"));
            dataRow.createCell(3).setCellValue(r.getStr("gradeName"));
            dataRow.createCell(4).setCellValue(r.getStr("majorName"));
            dataRow.createCell(5).setCellValue(r.getStr("className"));
            dataRow.createCell(6).setCellValue(r.getStr("rankName"));
            dataRow.createCell(7).setCellValue(r.getStr("awardName"));
            dataRow.createCell(8).setCellValue(r.getStr("awardPlace"));
            dataRow.createCell(9).setCellValue(new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("awardTime")));
        }
        workbook.setActiveSheet(0);

        String fileName = "student_"
                + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis())) + ".xls";
        File xls = new File(XLS_PATH + File.separator + fileName);
        if (!xls.getParentFile().exists()) {
            xls.getParentFile().mkdirs();
        }
        workbook.write(xls);
        workbook.close();
        return xls;
    }

    public File exportTeacherAward(List<Record> records) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerStyle.setFont(headerFont);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerRow.setHeightInPoints(20f);

        String[] headerTile = {"教职工号", "姓名", "性别", "奖项级别", "奖项名称", "获奖名次", "获奖时间"};
        for (int i = 0; i < headerTile.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headerTile[i]);
            sheet.setColumnWidth(i, 20 * 256);
        }
        for (int i = 1; i <= records.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i);
            Record r = records.get(i - 1);
            dataRow.createCell(0).setCellValue(r.getStr("username"));
            dataRow.createCell(1).setCellValue(r.getStr("name"));
            dataRow.createCell(2).setCellValue(r.getStr("genderName"));
            dataRow.createCell(3).setCellValue(r.getStr("rankName"));
            dataRow.createCell(4).setCellValue(r.getStr("awardName"));
            dataRow.createCell(5).setCellValue(r.getStr("awardPlace"));
            dataRow.createCell(6).setCellValue(new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("awardTime")));
        }
        workbook.setActiveSheet(0); // 设置显示工作表

        String fileName = "teacher_"
                + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis())) + ".xls";
        File xls = new File(XLS_PATH + File.separator + fileName);
        if (!xls.getParentFile().exists()) {
            xls.getParentFile().mkdirs();
        }
        workbook.write(xls); // 将内存数据写入文件
        workbook.close(); // 清除缓存数据
        return xls;
    }

    public File exportTeacherPaper(List<Record> records) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerStyle.setFont(headerFont);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerRow.setHeightInPoints(20f);

        String[] headerTile = {"教职工号", "姓名", "性别", "成果名称", "所属单位", "成果等级", "发布时间"};
        for (int i = 0; i < headerTile.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headerTile[i]);
            sheet.setColumnWidth(i, 20 * 256);
        }
        for (int i = 1; i <= records.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i);
            Record r = records.get(i - 1);
            dataRow.createCell(0).setCellValue(r.getStr("username"));
            dataRow.createCell(1).setCellValue(r.getStr("name"));
            dataRow.createCell(2).setCellValue(r.getStr("genderName"));
            dataRow.createCell(3).setCellValue(r.getStr("paperName"));
            dataRow.createCell(4).setCellValue(r.getStr("paperPlace"));
            dataRow.createCell(5).setCellValue(r.getStr("rankName"));
            dataRow.createCell(6).setCellValue(new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("awardTime")));
        }
        workbook.setActiveSheet(0); // 设置显示工作表

        String fileName = "teacher_"
                + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis())) + ".xls";
        File xls = new File(XLS_PATH + File.separator + fileName);
        if (!xls.getParentFile().exists()) {
            xls.getParentFile().mkdirs();
        }
        workbook.write(xls); // 将内存数据写入文件
        workbook.close(); // 清除缓存数据
        return xls;
    }

    public File exportTeacherSubject(List<Record> records) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerStyle.setFont(headerFont);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerRow.setHeightInPoints(20f);

        String[] headerTile = {"项目编号", "项目名称", "所属单位", "项目类别", "项目级别", "项目来源", "一级学科", "统计归属", "合作单位", "发布时间"};
        for (int i = 0; i < headerTile.length; i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(headerTile[i]);
            sheet.setColumnWidth(i, 20 * 256);
        }
        for (int i = 1; i <= records.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i);
            Record r = records.get(i - 1);
            dataRow.createCell(0).setCellValue(r.getStr("subjectNum"));
            dataRow.createCell(1).setCellValue(r.getStr("subjectName"));
            dataRow.createCell(2).setCellValue(r.getStr("subjectPlace"));
            dataRow.createCell(3).setCellValue(r.getStr("rankName"));
            dataRow.createCell(4).setCellValue(r.getStr("levelName"));
            dataRow.createCell(5).setCellValue(r.getStr("SourceName"));
            dataRow.createCell(6).setCellValue(r.getStr("TypeName"));
            dataRow.createCell(7).setCellValue(r.getStr("BelongName"));
            dataRow.createCell(8).setCellValue(r.getStr("CooperateName"));
            dataRow.createCell(9).setCellValue(new SimpleDateFormat("yyyy/MM/dd").format(r.getDate("subjectTime")));
        }
        workbook.setActiveSheet(0); // 设置显示工作表

        String fileName = "teacher_subject"
                + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis())) + ".xls";
        File xls = new File(XLS_PATH + File.separator + fileName);
        if (!xls.getParentFile().exists()) {
            xls.getParentFile().mkdirs();
        }
        workbook.write(xls); // 将内存数据写入文件
        workbook.close(); // 清除缓存数据
        return xls;
    }

    public File exportStudentZIP(List<Record> records) throws Exception {
        // 准备压缩文件信息
        String dirPath = ZIP_PATH +
                File.separator +
                new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis()));
        String zipPath = dirPath + ".zip";
        File zipFile = new File(zipPath);
        if (!zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }

        // 将待压缩文件复制到新目录下
        for (Record r : records) {
            //多图优化，字符分割 2020-10-20
            String imagePath = r.getStr("imagePath");
            if (imagePath.indexOf("*") != -1) {
                String[] parts = imagePath.split("\\*");
                //如果存在"*"则分割，取出图片数量
                Integer picNum = Integer.parseInt(parts[1]);
                String[] pathParts = parts[0].split("\\.");
                String pathPart = pathParts[0];
                for (int i = 0; i < picNum; i++) {
                    File srcFile = new File(PathKit.getWebRootPath() + pathPart + "_" + i + ".jpeg");
                    //如果图片丢失，用模板代替 2021-3-4
                    if (!srcFile.exists()) {
                        srcFile = new File(PathKit.getWebRootPath() + "/download/template/404notfound.jpeg");
                    }

                    String majorName = r.getStr("majorName");
                    String className = r.getStr("className");
                    String username = r.getStr("username");
                    String name = r.getStr("name");
                    String awardName = r.getStr("awardName");
                    File destFile = new File(dirPath + File.separator +
                            majorName + File.separator +
                            className + File.separator +
                            username + "_" +
                            name + "_" +
                            awardName + "_" +
                            new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(r.getDate("createAt")) + "_" + i + ".jpeg");
                    if (!destFile.exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    // 将源文件路径的文件复制到目标文件路径的文件，方式为替换已经存在的文件
                    Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                File srcFile = new File(PathKit.getWebRootPath() + imagePath);
                //如果图片丢失，用模板代替 2021-3-4
                if (!srcFile.exists()) {
                    srcFile = new File(PathKit.getWebRootPath() + "/download/template/404notfound.jpeg");
                }
                String majorName = r.getStr("majorName");
                String className = r.getStr("className");
                String username = r.getStr("username");
                String name = r.getStr("name");
                String awardName = r.getStr("awardName");
                File destFile = new File(dirPath + File.separator +
                        majorName + File.separator +
                        className + File.separator +
                        username + "_" +
                        name + "_" +
                        awardName + "_" +
                        new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(r.getDate("createAt")) + ".jpeg");
                if (!destFile.exists()) {
                    destFile.getParentFile().mkdirs();
                }
                // 将源文件路径的文件复制到目标文件路径的文件，方式为替换已经存在的文件
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            ///


        }

        // 开始压缩
        Project project = new Project();
        // 该类定义了一个具有所有目标、任务和各种其他属性的Ant项目。它还提供了使用特定目标名称启动生成的机制。
        // 该类还封装了允许使用抽象路径名称引用文件的方法，这些抽象路径名称在运行时被翻译成本机系统文件路径
        Zip zip = new Zip();
        zip.setProject(project);
        zip.setDestFile(zipFile);            // 创建文件目录
        FileSet fileSet = new FileSet();    // 文件集:可以创建映像的子集(简单理解为可以创建子文件夹)
        fileSet.setDir(new File(dirPath));    // 设置实例对象的基本目录，文件夹目录
        zip.addFileset(fileSet);            // 向zip对象里添加之前创建的文件集
        zip.execute();                        // 构建更新zip
        if (zipFile.exists()) {
            return zipFile;
        }
        return null;
    }

    public File exportTeacherZIP(List<Record> records) throws Exception {
        // 准备压缩文件信息
        String dirPath = ZIP_PATH + File.separator +
                new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis()));
        String zipPath = dirPath + ".zip";
        File zipFile = new File(zipPath);
        if (!zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }

        // 将待压缩文件复制到新目录下
        for (Record r : records) {
            //多图优化，字符分割 2020-10-20
            String imagePath = r.getStr("imagePath");
            if (imagePath.indexOf("*") != -1) {
                String[] parts = imagePath.split("\\*");
                //如果存在"*"则分割，取出图片数量
                Integer picNum = Integer.parseInt(parts[1]);
                String[] pathParts = parts[0].split("\\.");
                String pathPart = pathParts[0];
                for (int i = 0; i < picNum; i++) {
                    File srcFile = new File(PathKit.getWebRootPath() + pathPart + "_" + i + ".jpeg");
                    //如果图片丢失，用模板代替 2021-3-4
                    if (!srcFile.exists()) {
                        srcFile = new File(PathKit.getWebRootPath() + "/download/template/404notfound.jpeg");
                    }
                    String username = r.getStr("username");
                    String name = r.getStr("name");
                    String awardName = r.getStr("awardName");
                    File destFile = new File(dirPath + File.separator +
                            "teacher" + File.separator +
                            username + "_" +
                            name + "_" +
                            awardName + "_" +
                            new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(r.getDate("createAt")) + "_" + i +
                            ".jpeg");
                    if (!destFile.exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                File srcFile = new File(PathKit.getWebRootPath() + imagePath);
                //如果图片丢失，用模板代替 2021-3-4
                if (!srcFile.exists()) {
                    srcFile = new File(PathKit.getWebRootPath() + "/download/template/404notfound.jpeg");
                }
                String username = r.getStr("username");
                String name = r.getStr("name");
                String awardName = r.getStr("awardName");
                File destFile = new File(dirPath + File.separator +
                        "teacher" + File.separator +
                        username + "_" +
                        name + "_" +
                        awardName + "_" +
                        new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(r.getDate("createAt")) + "_" +
                        ".jpeg");
                if (!destFile.exists()) {
                    destFile.getParentFile().mkdirs();
                }
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            ///

        }

        // 开始压缩
        Project project = new Project();
        Zip zip = new Zip();
        zip.setProject(project);
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setDir(new File(dirPath));
        zip.addFileset(fileSet);
        zip.execute();
        if (zipFile.exists()) {
            return zipFile;
        }
        return null;
    }

}
