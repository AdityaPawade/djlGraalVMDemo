package com.jp.demo;

public class JNDArray {

    private double[][] data;

    public JNDArray(double[][] data) {
        this.data = data;
    }

    public static JNDArray create(int columns) {

        return JNDArray.create(1, columns);
    }
    
    public static JNDArray create(int rows, int columns) {

        double[][] arrayData = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                arrayData[i][j] = 0;
            }
        }
        return new JNDArray(arrayData);
    }

    public static JNDArray create(double[] data) {
        return JNDArray.create(data, new int[]{1, data.length});
    }

    public static JNDArray create(double[][] data) {
        return JNDArray.create(data, new int[]{data.length, data[0].length});
    }

    public static JNDArray create(double[] data, int[] shape) {

        double[][] arrayData = new double[shape[0]][shape[1]];
        arrayData[0] = data;
        return JNDArray.create(arrayData, shape);
    }
    
    public static JNDArray create(double[][] data, int[] shape) {

        double[][] arrayData = new double[shape[0]][shape[1]];
        for (int i = 0; i < shape[0]; i++) {
            System.arraycopy(data[i], 0, arrayData[i], 0, data[0].length);
        }
        return new JNDArray(arrayData);
    }

    public static JNDArray concat(int dimension, JNDArray... arraysToConcat) {

        int newDataRows = 0;
        int newDataColumns = 0;

        for (JNDArray array : arraysToConcat) {
            if (dimension == 1) {
                newDataRows = array.rows();
                newDataColumns = newDataColumns + array.columns();
            } else if (dimension == 2) {
                newDataRows = newDataRows + array.rows();
                newDataColumns = array.columns();
            }
        }

        double[][] arrayData = new double[newDataRows][newDataColumns];

        int rowsCovered = 0;
        int columnsCovered = 0;
        for (JNDArray jndArray : arraysToConcat) {
            for (int row = 0; row < jndArray.rows(); row++) {
                for (int column = 0; column < jndArray.columns(); column++) {
                    arrayData[rowsCovered + row][columnsCovered + column] = jndArray.getDouble(row, column);
                }
            }
            if (dimension == 1) {
                columnsCovered = columnsCovered + jndArray.columns();
            } else if (dimension == 2) {
                rowsCovered = rowsCovered + jndArray.rows();
            }
        }
        
        return new JNDArray(arrayData);
    } 
    
    public double getDouble(int row, int column) {
        return data[row][column];
    }

    public double getDouble(int column) {
        return data[0][column];
    }

    public int rows() {
        return data.length;
    }

    public int columns() {
        return data[0].length;
    }
    
    public JNDArray putSlice(int slice, JNDArray put) {
        if(rows() > 1) {
            System.arraycopy(put.data[0], 0, data[slice], 0, put.data[0].length);
        }
        return this;
    }

    public void putScalar(int i, double value) {
        data[0][i] = value;
    }

    public void put(int row, int column, double value) {
        data[row][column] = value;
    }
    
    public JNDArray getRow(int row) {
        double[] rowData = data[row];
        return JNDArray.create(rowData, new int[]{1, rowData.length});
    }
}
