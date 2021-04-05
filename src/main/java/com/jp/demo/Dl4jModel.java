//package com.jp.evolution.engine.neural.networks;
//
//import org.deeplearning4j.nn.api.Layer;
//import org.deeplearning4j.nn.conf.CacheMode;
//import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
//import org.deeplearning4j.nn.conf.layers.DenseLayer;
//import org.deeplearning4j.nn.conf.layers.OutputLayer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.nd4j.linalg.activations.Activation;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.lossfunctions.LossFunctions;
//
//import java.util.List;
//
//public class Dl4jModel implements Model {
//
//    private final Activation hiddenActivationFunction = Activation.SWISH;
//    private final Activation outputActivationFunction = Activation.SOFTMAX;
//    //LossFunctions.LossFunction lossFunction = LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;
//    private final LossFunctions.LossFunction outputLossFunction = LossFunctions.LossFunction.MCXENT;
//    private final MultiLayerNetwork net;
//
//    public Dl4jModel(List<INDArray> synapseWeights) {
//
//        net = buildNetwork(synapseWeights);
//        net.init();
//
//        Layer[] layers = net.getLayers();
//        for (int i = 0; i < layers.length; i++) {
//            Layer layer = layers[i];
//            layer.setParam("W", synapseWeights.get(i));
//        }
//    }
//
//    private MultiLayerNetwork buildNetwork(List<INDArray> synapseWeights) {
//
//        NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();
//        int nrOfLayers = synapseWeights.size();
//        NeuralNetConfiguration.ListBuilder listBuilder = builder.list();
//
//        for (int layer = 0; layer < nrOfLayers - 1; layer++) {
//            INDArray layerWeights = synapseWeights.get(layer);
//            int nIn = layerWeights.rows();
//            int nOut = layerWeights.columns();
//            listBuilder.layer(layer, buildLayer(nIn, nOut));
//        }
//
//        INDArray outputLayerWeights = synapseWeights.get(synapseWeights.size() - 1);
//        int nIn = outputLayerWeights.rows();
//        int nOut = outputLayerWeights.columns();
//        listBuilder.layer(nrOfLayers - 1, buildOutputLayer(nIn, nOut));
//        //listBuilder.validateOutputLayerConfig(false);
//        listBuilder.cacheMode(CacheMode.DEVICE);
//
//        MultiLayerConfiguration conf = listBuilder.build();
//        return new MultiLayerNetwork(conf);
//    }
//
//    private DenseLayer buildLayer(int nIn, int nOut) {
//        DenseLayer.Builder layerBuilder = new DenseLayer.Builder();
//        layerBuilder.activation(hiddenActivationFunction);
//        layerBuilder.nIn(nIn);
//        layerBuilder.nOut(nOut);
//        layerBuilder.setHasBias(false);
//        return layerBuilder.build();
//    }
//
//    private OutputLayer buildOutputLayer(int nIn, int nOut) {
//        OutputLayer.Builder outputLayerBuilder = new OutputLayer.Builder(outputLossFunction);
//        outputLayerBuilder.activation(outputActivationFunction);
//        outputLayerBuilder.nIn(nIn);
//        outputLayerBuilder.nOut(nOut);
//        outputLayerBuilder.setHasBias(false);
//        return outputLayerBuilder.build();
//    }
//
//    @Override
//    public INDArray infer(INDArray input) {
//        return net.output(input);
//    }
//    
//    public MultiLayerNetwork getNetwork() {
//        return net;
//    }
//    
//    public void close() {
//        net.close();
//    }
//}
