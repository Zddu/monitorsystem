package com.cidp.monitorsystem.util.capture;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;

public class CSV2ARFF {

    public static void main(String[] args) {
        try {
            String f1 = "C:\\Users\\Administrator\\Desktop\\1.pcap_Flow.csv";
            String f2 = "C:\\Users\\Administrator\\Desktop\\output.arff";

            // load the CSV file (input file)
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(f1));
            String [] options = new String[1];
            options[0]="-H";
            loader.setOptions(options);

            Instances data = loader.getDataSet();
            System.out.println(data);

            // save as an  ARFF (output file)
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File(f2));
            saver.writeBatch();
        } catch(Exception e) {
            System.out.println("发生错误");
        }
    }

    public void Csv2Arff() throws Exception {

    }
}
