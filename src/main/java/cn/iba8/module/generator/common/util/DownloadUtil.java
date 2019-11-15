package cn.iba8.module.generator.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Slf4j
public abstract class DownloadUtil {

    public static boolean download(String filename, String content, HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(filename, "UTF-8"));
            IOUtils.write(content.getBytes(), response.getOutputStream());
            return true;
        } catch (Exception e) {
            log.error("文件下载失败 e {}", e);
        }
        return false;
    }

}
