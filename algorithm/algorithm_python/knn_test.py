import numpy as np
import pandas as pd

# 读取csv文件, 路径为当前目录下的PDE.csv
df = pd.read_csv('PDE.csv')

# 将df['class']中的字符串转换为数字
df['class'] = df['class'].map({'clean': 0, 'buggy': 1})
# x为df中除了'class'列的所有列
x = df.drop('class', axis=1)
y = df['class']

# knn 算法训练
from sklearn.neighbors import KNeighborsClassifier
knn = KNeighborsClassifier(n_neighbors=5)
knn.fit(x, df['class'])

# 读取csv文件, 路径为当前目录下的JDT.csv
df = pd.read_csv('JDT.csv')
# 将df['class']中的字符串转换为数字
df['class'] = df['class'].map({'clean': 0, 'buggy': 1})
# x为df中除了'class'列的所有列
x = df.drop('class', axis=1)
y = df['class']
# knn 算法预测
y_pred = knn.predict(x)
# 计算准确率
from sklearn.metrics import accuracy_score
print('准确率为:', accuracy_score(y, y_pred))

