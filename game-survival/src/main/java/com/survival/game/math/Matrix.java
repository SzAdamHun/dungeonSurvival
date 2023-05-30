package com.survival.game.math;

public class Matrix {

    public static float[][] multiply(float[][] aMatrix, float[][] bMatrix) {
        if(aMatrix[0].length != bMatrix.length) {
            System.out.println("ERROR: A matrix columns does not equal B matrix rows");
            return identity(aMatrix);
        }

        float[][] outMatrix = new float[aMatrix.length][bMatrix[0].length];

		float temp;
		for(int i = 0; i < aMatrix.length; i++) {
			for(int j = 0; j < bMatrix[0].length; j++) {
				temp = 0;
				for(int k = 0; k < aMatrix[0].length; k++) {
					temp += aMatrix[i][k] * bMatrix[k][j]; 
				}
				outMatrix[i][j] = temp;
			}
		}

		return outMatrix;
    }

    private static float[][] identity(float[][] matrix) {
        float[][] outMatrix = new float[matrix.length][matrix.length];

        for(int i = 0; i < matrix.length; i++) {
            outMatrix[i][i] = 1;
        }

        return outMatrix;
    }

}