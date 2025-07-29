package org.dino.iocr.config;

import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import io.github.mymonstercat.ocr.config.ParamConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static io.github.mymonstercat.Model.ONNX_PPOCR_V4;

/**
 * OcrConfig参数调优
 *
 * @author Ethan Chu
 * @since 2025/07/24 15:52
 */
@Configuration
public class OcrConfig {

    @Value("#{T(java.lang.Integer).parseInt('${inference.engine.hardware.cpu:4}')}")
    private Integer cpu;

    @Value("#{T(java.lang.Integer).parseInt('${inference.engine.hardware.gpu:-1}')}")
    private Integer gpu;

    public static InferenceEngine INFERENCE_ENGINE;

    public static ParamConfig PARAM_CONFIG = ParamConfig.getDefaultConfig();

    static {
        PARAM_CONFIG.setDoAngle(true);
        PARAM_CONFIG.setMostAngle(true);
        PARAM_CONFIG.setPadding(20);
    }

    @PostConstruct
    public InferenceEngine getInstance() {
        if (INFERENCE_ENGINE == null) {
            INFERENCE_ENGINE = InferenceEngine.getInstance(ONNX_PPOCR_V4, new HardwareConfig(cpu, gpu));
        }
        return INFERENCE_ENGINE;
    }

}
