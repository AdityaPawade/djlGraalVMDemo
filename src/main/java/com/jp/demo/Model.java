package com.jp.demo;

import com.jp.demo.JNDArray;

public interface Model {

    JNDArray infer(JNDArray input);
}
