package cn.iba8.module.generator.common.i18n;

import cn.iba8.module.generator.common.util.MD5;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class I18nFileDefinition implements Serializable {

    private List<I18nFileDefinitionModule> modules = new ArrayList<>();

    public static void main(String[] args) {
        I18nFileDefinition of = I18nFileDefinition.of("D:\\my\\data\\input");
        System.out.println(JSON.toJSONString(of));
    }

    public static I18nFileDefinition of(String dirPreffix) {
        I18nFileDefinition definition = new I18nFileDefinition();
        File dirFile = new File(dirPreffix);
        if (dirFile.isDirectory()) {
            File[] moduleFiles = dirFile.listFiles();
            List<I18nFileDefinitionModule> i18nFileDefinitionModules = I18nFileDefinitionModule.ofFiles(moduleFiles);
            definition.setModules(i18nFileDefinitionModules);
        }
        return definition;
    }

    @Data
    public static class I18nFileDefinitionModule implements Serializable {
        private String code;
        private String name;
        private String version;
        private Integer version1;
        private Integer version2;
        private Integer version3;
        private List<I18nFileDefinitionFile> files = new ArrayList<>();

        public static I18nFileDefinitionModule ofDirName(String dirName) {
            String[] names = dirName.split("##");
            I18nFileDefinitionModule module = new I18nFileDefinitionModule();
            module.setCode(names[0]);
            module.setName(names[1]);
            module.setVersion(names[2]);
            module.setVersion1(Integer.valueOf(names[3]));
            module.setVersion2(Integer.valueOf(names[4]));
            module.setVersion3(Integer.valueOf(names[5]));
            return module;
        }

        public static List<I18nFileDefinitionModule> ofFiles(File[] moduleFiles) {
            List<I18nFileDefinitionModule> modules = new ArrayList<>();
            if (null != moduleFiles && moduleFiles.length > 0) {
                for (int i = 0; i < moduleFiles.length; i++) {
                    File moduleFile = moduleFiles[i];
                    String name = moduleFile.getName();
                    I18nFileDefinitionModule module = I18nFileDefinitionModule.ofDirName(name);
                    modules.add(module);
                    if (moduleFile.isDirectory()) {
                        File[] itemFiles = moduleFile.listFiles();
                        List<I18nFileDefinitionFile> i18nFileDefinitionFiles = I18nFileDefinitionFile.ofFiles(itemFiles);
                        module.setFiles(i18nFileDefinitionFiles);
                    }
                }
            }
            return modules;
        }

    }

    @Data
    public static class I18nFileDefinitionFile implements Serializable {
        private String language;
        private String name;
        private String suffix;
        private String content;
        private String md5;
        private Integer type;
        private String path;

        public static List<I18nFileDefinitionFile> ofFiles(File[] itemFiles) {
            List<I18nFileDefinitionFile> i18nFileDefinitionFiles = new ArrayList<>();
            for (int j = 0; j < itemFiles.length; j++) {
                File itemFile = itemFiles[j];
                String itemFileName = itemFile.getName();
                if ("i18n".equals(itemFileName)) {
                    if (itemFile.isDirectory()) {
                        File[] endFiles = itemFile.listFiles();
                        for (int i = 0; i < endFiles.length; i++) {
                            File endFile = endFiles[i];
                            if ("frontend".equals(endFile.getName())) {
                                if (endFile.isDirectory()) {
                                    File[] languageFiles = endFile.listFiles();
                                    for (int k = 0; k < languageFiles.length; k++) {
                                        File languageFile = languageFiles[k];
                                        String language = languageFile.getName();
                                        if (languageFile.isDirectory()) {
                                            File[] files = languageFile.listFiles();
                                            for (int m = 0; m < files.length; m++) {
                                                I18nFileDefinitionFile i18nFileDefinitionFile = new I18nFileDefinitionFile();
                                                i18nFileDefinitionFiles.add(i18nFileDefinitionFile);
                                                i18nFileDefinitionFile.setLanguage(language);
                                                File file = files[m];
                                                String fileName = file.getName();
                                                int i1 = fileName.lastIndexOf(".");
                                                String suffix = fileName.substring(i1 + 1);
                                                i18nFileDefinitionFile.setSuffix(suffix);
                                                i18nFileDefinitionFile.setName(fileName);
                                                i18nFileDefinitionFile.setContent(getContent(file));
                                                i18nFileDefinitionFile.setType(2);
                                                i18nFileDefinitionFile.setMd5(MD5.getMD5Str(i18nFileDefinitionFile.getContent() + i18nFileDefinitionFile.getLanguage()));
                                                i18nFileDefinitionFile.setPath(file.getPath());
                                            }
                                        }
                                    }
                                }
                            }
                            if ("backend".equals(endFile.getName())) {
                                if (endFile.isDirectory()) {
                                    File[] files = endFile.listFiles();
                                    for (int m = 0; m < files.length; m++) {
                                        I18nFileDefinitionFile i18nFileDefinitionFile = new I18nFileDefinitionFile();
                                        i18nFileDefinitionFiles.add(i18nFileDefinitionFile);
                                        i18nFileDefinitionFile.setLanguage("zh_CN");
                                        File file = files[m];
                                        String fileName = file.getName();
                                        int i1 = fileName.lastIndexOf(".");
                                        String suffix = fileName.substring(i1 + 1);
                                        i18nFileDefinitionFile.setSuffix(suffix);
                                        i18nFileDefinitionFile.setName(fileName);
                                        i18nFileDefinitionFile.setContent(getContent(file));
                                        i18nFileDefinitionFile.setType(1);
                                        i18nFileDefinitionFile.setMd5(MD5.getMD5Str(i18nFileDefinitionFile.getContent() + i18nFileDefinitionFile.getLanguage()));
                                        i18nFileDefinitionFile.setPath(file.getPath());
                                    }
                                }
                            }
                        }

                    }
                }
            }
            return i18nFileDefinitionFiles;
        }

        public static String getContent(File file) {
            try {
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                return new String(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

}
