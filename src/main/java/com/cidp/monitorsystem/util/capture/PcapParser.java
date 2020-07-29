package com.cidp.monitorsystem.util.capture;

import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.protocol.Protocol;

import java.io.IOException;

public class PcapParser {
    public static void main(String[] args) throws IOException
    {
        final Pcap pcap = Pcap.openStream("C:\\Users\\Administrator\\Desktop\\1.pcap");
        System.out.println(pcap);
        pcap.loop(new PacketHandler(){
            @Override
            public boolean nextPacket(final Packet packet) throws IOException
            {

                if (packet.hasProtocol(Protocol.TCP))
                {
                    Buffer payload = packet.getPacket(Protocol.TCP).getPayload();
                    if (payload != null)
                    {
                        System.out.println(payload);
                    }

                }

                return true;
            }
        });
    }
}
