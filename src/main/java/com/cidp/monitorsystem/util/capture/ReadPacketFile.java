package com.cidp.monitorsystem.util.capture;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import java.io.EOFException;
import java.util.concurrent.TimeoutException;

public class ReadPacketFile {
    private static final int COUNT = 5;

    private static final String PCAP_FILE_KEY = ReadPacketFile.class.getName() + ".pcapFile";
    private static final String PCAP_FILE =
            System.getProperty(PCAP_FILE_KEY, "1.pcap");

    private ReadPacketFile() {}

    public static void main(String[] args) throws PcapNativeException, NotOpenException {
        String path = "C:\\Users\\Administrator\\Desktop\\1.pcap";
        PcapHandle handle;
        try {
            handle = Pcaps.openOffline(path, PcapHandle.TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            handle = Pcaps.openOffline(PCAP_FILE);
        }

        for (int i = 0; i < COUNT; i++) {
            try {
                Packet packet = handle.getNextPacketEx();
                System.out.println(handle.getTimestamp());
                System.out.println(packet);
            } catch (TimeoutException e) {
            } catch (EOFException e) {
                System.out.println("EOF");
                break;
            }
        }

        handle.close();
    }
}
