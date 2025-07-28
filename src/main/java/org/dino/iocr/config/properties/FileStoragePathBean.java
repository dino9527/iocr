package org.dino.iocr.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储路径配置类
 *
 * @author Ethan Chu
 * @since 2025/07/28 13:43
 */
@Configuration
@Data
public class FileStoragePathBean {

    @Value("${file.storage.windows-path}")
    private String windowsPath;
    @Value("${file.storage.linux-path}")
    private String linuxPath;

    private static final String WIN = "Windows";
    private static final String LINUX = "Linux";

    public String getFilePathForSystem(String system) {
        if (system != null && system.startsWith(WIN)) {
            return windowsPath;
        } else if (system != null && system.startsWith(LINUX)) {
            return linuxPath;
        } else {
            return "";
        }
    }

}
