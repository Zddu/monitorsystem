package com.cidp.monitorsystem.util.ML;

import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.Random;

public class test {
    public static void main(String[] args) throws Exception {
            new test().Knn();
    }

    /**
     * 决策树分类
     * @throws Exception
     */
    public void treeJ48() throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\main.arff";
        Instances trainingSet = DataSource.read(path);
        trainingSet.randomize(new Random(0));
        int trainSize = (int) Math.round(trainingSet.numInstances()*0.66);
        int testSize = trainingSet.numInstances() - trainSize;
        Instances train = new Instances(trainingSet,0,trainSize);
        Instances test = new Instances(trainingSet,trainSize,testSize);
        train.setClassIndex(train.numAttributes()-1);//训练集
        test.setClassIndex(test.numAttributes()-1);//测试集
        if (!train.equalHeaders(test)){
            throw new Exception("训练集和测试集不兼容"+train.equalHeadersMsg(test));
        }
        //训练分类器
        J48 classifier = new J48();
        classifier.buildClassifier(train);
        //输出数据
        System.out.println("编号\t-\t实际\t-\t预测\t-\t错误\t-\t分布");
        double score=0;
        for (int i = 0; i < test.numInstances(); i++) {
            //得到预测值
            double pred = classifier.classifyInstance(test.instance(i));
            double[] dist = classifier.distributionForInstance(test.instance(i));
            System.out.print(i+1);
            System.out.print("\t-\t");
            System.out.print(test.instance(i).toString(test.classIndex()));
            System.out.print("\t-\t");
            System.out.print(test.classAttribute().value((int) pred));
            System.out.print("\t-\t");
            if (pred!=test.instance(i).classValue()){
                System.out.print("是");
            }
            else{
                score++;
                System.out.print("否");
            }
            System.out.print("\t-\t");
            System.out.print(Utils.arrayToString(dist));
            System.out.println();
        }
        System.out.println(score/test.numInstances());
    }

    /**
     * K 最近邻算法
     * @throws Exception
     */
    public void Knn() throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\main.arff";
        Instances trainingSet = DataSource.read(path);
        trainingSet.randomize(new Random(0));
        int trainSize = (int) Math.round(trainingSet.numInstances()*0.66);
        int testSize = trainingSet.numInstances() - trainSize;
        Instances train = new Instances(trainingSet,0,trainSize);
        Instances test = new Instances(trainingSet,trainSize,testSize);
        train.setClassIndex(train.numAttributes()-1);//训练集
        test.setClassIndex(test.numAttributes()-1);//测试集
        //KNN
        IBk classifierKnn = new IBk();
        classifierKnn.buildClassifier(train);
        double score=0;
        for (int i = 0; i < test.numInstances(); i++){// 测试分类结果
            int preIndex = i + 1;
            System.out.println("第"+ preIndex +"个样本的判断结果是：" + test.classAttribute().value((int) classifierKnn.classifyInstance(test.instance(i))));
            System.out.println("第"+ preIndex +"个样本的类别属性是：" + test.instance(i).toString(test.classIndex()));
            boolean flag = false;

            if (classifierKnn.classifyInstance(test.instance(i)) == test.instance(i).classValue()){// 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
                score++;// 正确值加1
                flag = true;
            }
            System.out.println("第"+ preIndex +"个样本的判断结果是否正确：" + flag);
        }
        System.out.println("KNN classification precision:" + (score / test.numInstances()));

    }

}
