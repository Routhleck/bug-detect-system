import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @program: algorithm
 * @description: test knn algorithm
 * @author: 20301037_Routhleck
 * @create: 2022-09-17 22:19
 **/
public class knn_train {
    
    public static double[][][] readTrainSet(String path) throws IOException {
        // 读取csv文件
        BufferedReader br = new BufferedReader(new FileReader(path));
        // 读取第一行，获取列数
        String line = br.readLine();
        String[] firstLine = line.split(",");
        int colNum = firstLine.length;
        // 读取第一列，获取行数
        int rowNum = 0;
        while ((line = br.readLine()) != null) {
            rowNum++;
        }
        // 读取数据
        br = new BufferedReader(new FileReader(path));
        double[][][] train_set = new double[rowNum][2][colNum];
        line = br.readLine();
        for (int i = 1; i < rowNum; i++) {
            line = br.readLine();
            String[] temp = line.split(",");
            for (int j = 0; j < colNum - 1; j++) {
                train_set[i][0][j] = Double.parseDouble(temp[j]);
            }
            if(temp[colNum - 1].equals("buggy")){
                train_set[i][1][0] = 1;
            }else{
                train_set[i][1][0] = 0;
            }
        }
        return train_set;
    }
    public static void main(String[] args) {

        // String ProjectPath=knn_train.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        // System.out.println(ProjectPath);
        // knn参数设置
        int k = 5;

        // 读取训练集csv，为Datasets/AEEEM/csv/JDT.csv
        double[][][] train_set = new double[0][][];
        try {
            train_set = readTrainSet(System.getProperty("user.dir") + "/algorithm/Datasets/AEEEM/csv/JDT.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 读取测试集csv，为Datasets/AEEEM/csv/Lucene.csv
        double[][][] test_set = new double[0][][];
        try {
            test_set = readTrainSet(System.getProperty("user.dir") + "/algorithm/Datasets/AEEEM/csv/Lucene.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 创建knn对象
        knn knn = new knn(k);
        // 训练
        for (double[][] v : train_set) {
            knn.fit(v[0], (int)v[1][0]);
        }
        // 测试
        int[] result = new int[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            result[i] = knn.predict(test_set[i][0]);
        }
        // 原本的标签
        int[] label = new int[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            label[i] = (int) test_set[i][1][0];
        }
        // 计算准确率
        double accuracy = 0;
        for (int i = 0; i < test_set.length; i++) {
            if (result[i] == label[i]) {
                accuracy++;
            }
        }
        accuracy /= test_set.length;
        System.out.println("准确率为：" + accuracy);


    }
}
