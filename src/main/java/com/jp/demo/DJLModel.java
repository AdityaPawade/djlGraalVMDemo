//package com.jp.demo;
//
//import ai.djl.Device;
//import ai.djl.ndarray.NDArray;
//import ai.djl.ndarray.NDList;
//import ai.djl.ndarray.NDManager;
//import ai.djl.ndarray.types.DataType;
//import ai.djl.ndarray.types.Shape;
//import ai.djl.nn.Activation;
//import ai.djl.nn.Block;
//import ai.djl.nn.Blocks;
//import ai.djl.nn.SequentialBlock;
//import ai.djl.nn.core.Linear;
//import ai.djl.training.DefaultTrainingConfig;
//import ai.djl.training.Trainer;
//import ai.djl.training.evaluator.Accuracy;
//import ai.djl.training.listener.TrainingListener;
//import ai.djl.training.loss.Loss;
//import ai.djl.training.optimizer.Optimizer;
//import ai.djl.training.tracker.Tracker;
//
//import java.util.List;
//import java.util.function.Function;
//
//public class DJLModel implements Model {
//
//    //LossFunctions.LossFunction lossFunction = LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;
//    //private final LossFunctions.LossFunction outputLossFunction = LossFunctions.LossFunction.MCXENT;
//    private final Trainer trainer;
//
//    public DJLModel(List<JNDArray> synapseWeights) {
//
//        trainer = buildNetwork(synapseWeights);
//    }
//
//    private Trainer buildNetwork(List<JNDArray> synapseWeights) {
//
//        int nrOfLayers = synapseWeights.size();
//
//        SequentialBlock block = new SequentialBlock();
//        Block inputBlock = Blocks.batchFlattenBlock(synapseWeights.get(0).rows());
//        block.add(inputBlock);
//
//        for (int layer = 0; layer < nrOfLayers - 1; layer++) {
//            final JNDArray layerWeights = synapseWeights.get(layer);
//            int units = layerWeights.columns();
//            Linear linearBlock = Linear.builder().setUnits(units).optBias(false).build();
//            linearBlock.setInitializer((NDManager manager, Shape shape, DataType dataType) -> {
//                return getNdArray(layerWeights, manager).transpose();
//            });
//            block.add(linearBlock);
//            Function<NDList, NDList> lambdaBlock = (NDList ndArrays) -> {
//                return Activation.swish(ndArrays, 1);
//            };
//            block.add(lambdaBlock);
//        }
//
//        final JNDArray outputLayerWeights = synapseWeights.get(synapseWeights.size() - 1);
//        int nOut = outputLayerWeights.columns();
//        Linear linearOutputBlock = Linear.builder().setUnits(nOut).optBias(false).build();
//        linearOutputBlock.setInitializer((NDManager manager, Shape shape, DataType dataType) -> {
//            return getNdArray(outputLayerWeights, manager).transpose();
//        });
//        block.add(linearOutputBlock);
//
//        ai.djl.Model model = ai.djl.Model.newInstance("nn");
//        model.setBlock(block);
//
//        Tracker lrt = Tracker.fixed(0.1f);
//        Optimizer sgd = Optimizer.sgd().setLearningRateTracker(lrt).build();
//        DefaultTrainingConfig trainingConfig = new DefaultTrainingConfig(Loss.l1Loss())
//            .optOptimizer(sgd)
//            .optDevices(Device.getDevices(1)) // single GPU
//            .addEvaluator(new Accuracy()) // Model Accuracy
//            .addTrainingListeners(TrainingListener.Defaults.logging());
//
//        return model.newTrainer(trainingConfig);
//    }
//
//    @Override
//    public JNDArray infer(JNDArray input) {
//
//        NDManager ndManager = trainer.getManager();
//        NDArray modelInput = getNdArray(input, ndManager);
//        NDList output = trainer.forward(new NDList(modelInput));
//        NDArray outputArray = output.get(0).softmax(1);
//        return getJNDArray(outputArray);
//    }
//
////    public MultiLayerNetwork getNetwork() {
////        return null;
////    }
//
//    private NDArray getNdArray(JNDArray array, NDManager manager) {
//        double[][] doubleArray = new double[array.rows()][array.columns()];
//        for (int i = 0; i < array.rows(); i++) {
//            for (int j = 0; j < array.columns(); j++) {
//                doubleArray[i][j] = array.getDouble(i, j);
//            }
//        }
//        return manager.create(doubleArray);
//    }
//
//    private JNDArray getJNDArray(NDArray array) {
//        double[] doubleArray = new double[(int) array.size()];
//        for (int j = 0; j < array.size(); j++) {
//            doubleArray[j] = array.getDouble(0, j);
//        }
//        return JNDArray.create(doubleArray, new int[]{1, (int) array.size()});
//    }
//    
//    public void close() {
//        trainer.close();
//    }
//}
