package org.dino.iocr.config;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import io.github.mymonstercat.ocr.config.ParamConfig;

/**
 * OcrConfig参数调优
 *
 * @author Ethan Chu
 * @since 2025/07/24 15:52
 */
public final class OcrConfig {

    public static ParamConfig PARAM_CONFIG = ParamConfig.getDefaultConfig();

    public static InferenceEngine ENGINE = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4, new HardwareConfig(Runtime.getRuntime().availableProcessors() / -1, -1));

    static {
        PARAM_CONFIG.setDoAngle(true);
        PARAM_CONFIG.setMostAngle(true);
        PARAM_CONFIG.setPadding(20);
    }
}
