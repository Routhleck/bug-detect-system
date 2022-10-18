import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @program: algorithm
 * @description: Logistic Regression main function
 * @author: 20301037_Routhleck
 * @create: 2022-10-03 22:49
 **/
public class logisticRegression_train {
    public static double[][] readTrainSet(String path) throws IOException {
        // 读取训练集
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        String[] firstLine = line.split(",");
        int colNum = firstLine.length;
        int rowNum = 0;
        while ((line = br.readLine()) != null) {
            rowNum++;
        }
        br = new BufferedReader(new FileReader(path));
        double[][] train_set = new double[rowNum][colNum];
        line = br.readLine();
        // 最后一列为标签, 令buggy为1，clean为0
        for (int i = 0; i < rowNum; i++) {
            line = br.readLine();
            String[] temp = line.split(",");
            for (int j = 0; j < colNum - 1; j++) {
                train_set[i][j] = Double.parseDouble(temp[j]);
            }
            if(temp[colNum - 1].equals("buggy")){
                train_set[i][colNum - 1] = 1;
            }else{
                train_set[i][colNum - 1] = 0;
            }
        }
        return train_set;
    }

    public static void main(String[] args) {
        // 读取训练集
        double[][] train_set = new double[0][];
        // System.out.println(System.getProperty("user.dir") + "/algorithm/Datasets/AEEEM/csv/PDE.csv");
        try {
            train_set = readTrainSet(System.getProperty("user.dir") + "/Datasets/AEEEM/csv/Lucene.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("训练集读取完成，训练集大小：" + train_set.length);
        // 读取测试集
        double[][] test_set = new double[0][];
        try {
            test_set = readTrainSet(System.getProperty("user.dir") + "/Datasets/AEEEM/csv/JDT.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("测试集读取完成，测试集大小：" + test_set.length);
        // 训练模型
        logisticRegression lr = new logisticRegression();
        // 训练集分为x和y
        double[][] x = new double[train_set.length][train_set[0].length - 1];
        double[] y = new double[train_set.length];
        for (int i = 0; i < train_set.length; i++) {
            for (int j = 0; j < train_set[0].length - 1; j++) {
                x[i][j] = train_set[i][j];
            }
            y[i] = train_set[i][train_set[0].length - 1];
        }
        // 测试集分为x和y
        double[][] x_test = new double[test_set.length][test_set[0].length - 1];
        double[] y_test = new double[test_set.length];
        for (int i = 0; i < test_set.length; i++) {
            for (int j = 0; j < test_set[0].length - 1; j++) {
                x_test[i][j] = test_set[i][j];
            }
            y_test[i] = test_set[i][test_set[0].length - 1];
        }

        System.out.println("debug");

        lr.train(x, y, 0.01, 1000);
        // 测试模型
        double[] predict = lr.predict(x_test);

        // 计算准确率
        double accuracy = lr.accuracy(predict, y_test);
        System.out.println("准确率为：" + accuracy);
    }
}
