
/**
 * @program: algorithm
 * @description: knn algorithm
 * @author: 20301037_Routhleck
 * @create: 2022-09-14 18:46
 **/
public class knn {
    // k个最近邻
    public int k;
    /* 训练集列表,列表元素为v：v[0]为训练集数据向量，v[1]为训练集标签
        * double[i]为第i个训练数据
        * double[i][0]为第i个训练数据中的数据向量
        * double[i][1][0]为第i个训练数据中的标签
        * double[i][0][n]为第i个训练数据中的数据向量中的第n个元素
    */
    public double[][][] train_set;

    // 构造函数
    public knn(int k) {
        this.k = k;
        this.train_set = new double[0][][];
    }

    // 计算两个向量的欧式距离
    // 函数假定向量是长度相等的数组
    public double dist(double[] v1, double[] v2){
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(sum);

    }

    // 更新最大值
    public double updateMax(double val, double[] arr){
        double max = val;
        for (double v : arr) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    // 返回分类结果, 1为buggy, 0为clean
    public int mode(int arr[]){
        int[] frequency = new int[arr.length];
        int max = 0;
        int result = -1;
        // 看arr[]中出现最多的数字是多少，返回result
        for (int j : arr) {
            frequency[j]++;
            if (frequency[j] > max) {
                max = frequency[j];
                result = j;
            }
        }
        return result;
    }

    // 训练函数
    public void fit(double[] vector, int label){
        // 将训练数据添加到训练集中
        double[][][] new_train_set = new double[this.train_set.length + 1][2][];
        System.arraycopy(this.train_set, 0, new_train_set, 0, this.train_set.length);
        new_train_set[this.train_set.length][0] = vector;
        new_train_set[this.train_set.length][1] = new double[]{label};
        this.train_set = new_train_set;
    }

    // 预测函数
    public int predict(double[] vector){
        // 计算测试数据与训练集中每个数据的距离
        double[] dists = new double[this.train_set.length];
        for (int i = 0; i < this.train_set.length; i++) {
            dists[i] = this.dist(vector, this.train_set[i][0]);
        }
        // 找出距离最小的k个数据
        double[] k_dists = new double[this.k];
        for (int i = 0; i < this.k; i++) {
            k_dists[i] = this.updateMax(Double.MAX_VALUE, dists);
        }
        // 找出k个数据中的标签
        int[] k_labels = new int[this.k];
        for (int i = 0; i < this.k; i++) {
            for (int j = 0; j < dists.length; j++) {
                if (dists[j] == k_dists[i]){
                    k_labels[i] = (int) this.train_set[j][1][0];
                }
            }
        }
        // 返回k个数据中出现最多的标签
        return this.mode(k_labels);
    }

}
