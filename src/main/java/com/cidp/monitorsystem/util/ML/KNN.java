package com.cidp.monitorsystem.util.ML;

import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.neighboursearch.LinearNNSearch;

public class KNN {
    /**
     *
     * @param sourceIns 表示你在哪个数据集中找邻居
     * @param target 找邻居的样本
     * @param kNN 多少邻居
     * @return k个邻居
     */
    public static Instances getKNeighbour(Instances sourceIns, Instance target, int kNN)
    {
        Instances neighbours = null;
        try{
            EuclideanDistance dfunc = new EuclideanDistance();
            LinearNNSearch lnn = new LinearNNSearch();
            lnn.setDistanceFunction(dfunc);
            lnn.setInstances( sourceIns );
            lnn.addInstanceInfo(target);
            neighbours = lnn.kNearestNeighbours(target, kNN);
        }catch(Exception e){
            System.out.println("Util.buildKdTree(LinearNNSearch m_NNSearch,Instance mean, int m_kNN) is wrong!");
        }
        return neighbours;
    }
    public static Instances getFileInstances(String fileName) throws Exception {
        ConverterUtils.DataSource frData = new ConverterUtils.DataSource( fileName );
        return frData.getDataSet();
    }

}
