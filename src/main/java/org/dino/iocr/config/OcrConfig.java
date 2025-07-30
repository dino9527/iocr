package org.dino.iocr.config;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import io.github.mymonstercat.ocr.config.ParamConfig;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.mymonstercat.Model.ONNX_PPOCR_V3;

/**
 * OcrConfig 参数调优
 *
 * @author Ethan Chu
 * @since 2025/07/24 15:52
 */
@Configuration
@ConfigurationProperties(prefix = "inference.engine")
@Setter
public class OcrConfig {
    private int numThreads = 4;
    private int gpu = -1;
    private Model model = ONNX_PPOCR_V3;

    /**
     * 初始化 ParamConfig 实例
     * @return ParamConfig 实例
     */
    @Bean
    public ParamConfig paramConfig() {
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        paramConfig.setPadding(20);
        return paramConfig;
    }

    /**
     * 初始化 InferenceEngine 实例
     * @return InferenceEngine 实例
     */
    @Bean
    public InferenceEngine inferenceEngine() {
        return InferenceEngine.getInstance(model, new HardwareConfig(numThreads, gpu));
    }
}