package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.ftl.FileModuleDefinition;
import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import cn.iba8.module.generator.common.ftl.PointsDefinition;
import cn.iba8.module.generator.common.util.JacksonUtil;
import cn.iba8.module.generator.config.JsonToJsonStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @Author sc.wan
 * @Description 加载模板配置文件
 * @Date 2019/12/4 10:09
 */
@Component
@AllArgsConstructor
@Slf4j
public class LoadInitBizService implements CommandLineRunner {

    private final ModuleBizService moduleBizService;

    private final LoadTemplateBizService loadTemplateBizService;

    @Override
    public void run(String... args) throws Exception {
        loadModule();
        loadPoints();
    }

    /*
     * @Author sc.wan
     * @Description 加载模块
     * @Date 16:41 2019/12/9
     * @Param
     * @return
     **/
    private void loadModule() {
        InputStream is = null;
        try {
            File file = ResourceUtils.getFile("classpath:templates/modules.json");
            is = new FileInputStream(file);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            String json = new String(bytes);
            FileModuleDefinition fileModuleDefinition = FileModuleDefinition.ofJson(json);
            if (null == fileModuleDefinition) {
                return;
            }
            List<FileModuleDefinition.FileModuleModuleDefinition> modules = fileModuleDefinition.getModules();
            List<FileModuleDefinition.FileModuleTemplateDefinition> templateRoots = fileModuleDefinition.getTemplates();
            if (!CollectionUtils.isEmpty(modules)) {
                for (FileModuleDefinition.FileModuleModuleDefinition module : modules) {
                    moduleBizService.load(module);
                }
            }
            if (!CollectionUtils.isEmpty(templateRoots)) {
                for (FileModuleDefinition.FileModuleTemplateDefinition templateRoot : templateRoots) {
                    loadTemplateBizService.loadTemplates(templateRoot);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loadPoints() {
        InputStream is = null;
        InputStream pis = null;
        try {
            File file = ResourceUtils.getFile("classpath:points/points.json");
            is = new FileInputStream(file);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            String json = new String(bytes);
            List<PointsDefinition> pointsDefinitions = PointsDefinition.ofList(json);
            if (CollectionUtils.isEmpty(pointsDefinitions)) {
                return;
            }
            for (PointsDefinition pointsDefinition : pointsDefinitions) {
                File propertiesFile = ResourceUtils.getFile(pointsDefinition.getFilepath());
                pis = new FileInputStream(propertiesFile);
                byte[] propertiesBytes = new byte[pis.available()];
                pis.read(propertiesBytes);
                String properties = new String(propertiesBytes);
                JsonToJsonStorage.load(pointsDefinition.getDataType(), properties);
            }
            System.out.println("==========================");
            System.out.println(JacksonUtil.toString(JsonToJsonStorage.json2jsonStorage));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != pis) {
                try {
                    pis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
