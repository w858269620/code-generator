package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.config.CodeGeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public abstract class ZipUtil {

    public static void filePackage(List<TemplateDefinition.TemplateFileBean> templateFileBeans, String module, String version, String typeGroup) {
        CodeGeneratorProperties codeGeneratorProperties = SpringUtils.getBean(CodeGeneratorProperties.class);
        String tmp = codeGeneratorProperties.getCodeOutputTmp() + "/" + System.currentTimeMillis();
        if (CollectionUtils.isEmpty(templateFileBeans)) {
            return;
        }
        for (TemplateDefinition.TemplateFileBean templateFileBean : templateFileBeans) {
            try {
                String fileDir = templateFileBean.getFileDir();
                String path = tmp + "/" + FileNameUtil.toPath(fileDir);
                File parent = new File(path);
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                OutputStream os = new FileOutputStream(new File(path + "/" + templateFileBean.getFilename() + "." + typeGroup));
                os.write(templateFileBean.getContent().getBytes());
            } catch (Exception e) {
                log.error("生成临时文件失败");
            }
        }


        String zipName = module + "_" + version + ".zip";
        FileOutputStream fos;
        ZipOutputStream zos;

        try {
            File files = new File(tmp);
            fos = new FileOutputStream(zipName);
            zos = new ZipOutputStream(fos);
            writeZip(files, "", zos);
        } catch (FileNotFoundException e) {
            log.error("生成zip文件失败 {}", zipName);
        }
    }

    /**
     * 写入压缩包
     */
    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) { // 需要压缩的文件夹是否存在
            if (file.isDirectory()) {// 判断是否是文件夹
                parentPath += file.getName() + "/"; // 文件夹名称 + "/"
                File[] files = file.listFiles(); // 获取文件夹下的文件夹或文件
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else { // 空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {

                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                } catch (Exception e) {

                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                            if (file != null && file.exists()) {
                                file.delete();
                            }
                        }
                    } catch (IOException e) {

                    }
                }
            }
        }

    }

}
