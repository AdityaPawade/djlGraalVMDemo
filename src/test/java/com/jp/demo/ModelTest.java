package com.jp.demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ModelTest {

    @Test
    public void testModel() {

        List<JNDArray> synapseWeights = Arrays.asList(
            JNDArray.create(
                new double[][]{
                    {-2.2529,   0,          -1.7792,    -1.7971,    3.4690,     0},
                    {0.4144,    -3.5393,    0,          0,          0,          -4.4635},
                    {3.1434,    0,          0,          0,          -3.2891,    4.1034},
                    {0,         0,          0,          0,          3.6294,     -0.0649},
                    {0,         0,          0,          0,          1.1850,     0},
                    {-4.0107,   -2.6671,    -4.4709,    0.2428,     0,          3.4294},
                    {0,         2.6987,     0,          0,          0,          2.2124},
                    {0,         0,          0,          0,          -2.4545,    0}
                }
            ),
            JNDArray.create(
                new double[][]{
                    {-2.2529,   0,          -1.7792,    -1.7971,    3.4690,     0,          0,          -1.7792},
                    {0.4144,    -3.5393,    0,          0,          0,          -4.4635,    -3.5393,    0},
                    {3.1434,    0,          0,          0,          -3.2891,    4.1034,     0,          0},
                    {0,         0,          0,          0,          3.6294,     -0.0649,    0,          0},
                    {0,         0,          0,          0,          1.1850,     0,          0,          0},
                    {-4.0107,   -2.6671,    -4.4709,    0.2428,     0,          3.4294,     -2.6671,    -4.4709}
                }
            )
        );
        JNDArray input = JNDArray.create(
            new double[]{
                0.0003,     0.0323,     0,          0.1803,     0.7540,     -1.0000,    -1.0000,    1.0000},
            new int[]{1, 8}
        );

        Dl4jModel model = new Dl4jModel(synapseWeights);
        JNDArray output = model.infer(input);
        model.close();
//        
//        DJLModel djlModel = new DJLModel(synapseWeights);
//        JNDArray djlOutput = djlModel.infer(input);
//        djlModel.close();

//        System.out.println(output);
    }
}
