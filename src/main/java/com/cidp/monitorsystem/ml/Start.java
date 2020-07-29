package com.cidp.monitorsystem.ml;


import com.cidp.monitorsystem.ml.convert.PcapReader;

public class Start {
    public static void main(String[] args) {
        PcapReader.readFile("C:\\Users\\Administrator\\Desktop\\1.pcap","C:\\Users\\Administrator\\Desktop",120000000L,5000000L);
    }
}
