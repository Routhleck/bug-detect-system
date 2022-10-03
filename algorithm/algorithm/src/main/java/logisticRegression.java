/**
 * @program: algorithm
 * @description: Logistic Regression class
 * @author: 20301037_Routhleck
 * @create: 2022-10-03 22:41
 **/
public class logisticRegression {
    public double[] w;

    public logisticRegression() {
        this.w = new double[0];
    }

    // 计算sigmoid函数
    public double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    // 预测函数, 返回sigmoid函数的值
    public double[] predict(double[][] x) {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            double z = 0;
            for (int j = 0; j < x[i].length; j++) {
                z += x[i][j] * w[j];
            }
            result[i] = sigmoid(z);
        }
        return result;
    }

    // 训练函数
    public double[] train(double[][] x, double[] y, double lr, int max_iter) {
        int m = x.length;
        int n = x[0].length;
        double[] w = new double[n];
        for (int i = 0; i < max_iter; i++) {
            double[] h = predict(x);
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < m; k++) {
                    sum += (h[k] - y[k]) * x[k][j];
                }
                w[j] -= lr * sum / m;
            }
        }
        return w;
    }

    public double[] train(double[][] x, double[] y) {
        return train(x, y, 0.01, 1000);
    }

    // 计算准确率
    public double accuracy(double[] h, double[] y) {
        int correct = 0;
        for (int i = 0; i < h.length; i++) {
            if (h[i] >= 0.5 && y[i] == 1) {
                correct++;
            } else if (h[i] < 0.5 && y[i] == 0) {
                correct++;
            }
        }
        return (double) correct / h.length;
    }


}
