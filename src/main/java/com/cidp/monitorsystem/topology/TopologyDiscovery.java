package com.cidp.monitorsystem.topology;

import com.cidp.monitorsystem.mapper.*;
import com.cidp.monitorsystem.model.*;
import com.cidp.monitorsystem.service.*;
import com.cidp.monitorsystem.service.dispservice.ConnectivelyService;
import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.snmp4j.PDU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description: 用于拓扑发现的主要类
 * @author: Zdde丶
 * @create: 2020/4/1012:31
 **/
@Component
public class TopologyDiscovery {
    @Autowired
    IpRouteTableMapper ipRouteTableMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    IpAddrTableMapper ipAddrTableMapper;
    @Autowired
    InterfaceService interfaceService;
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    SystemService systemService;
    @Autowired
    InterfaceOfMacService macService;
    @Autowired
    MacForwardService forwardService;
    @Autowired
    IndexPortService indexPortService;
    @Autowired
    NeighborMacService neighborMacService;
    @Autowired
    ConnectivelyService connectivelyService;

    public Node deviceSearch(String ip) throws Exception {
        SNMPSessionUtil issnmp = new SNMPSessionUtil(ip, "161", "public", "2");
        ArrayList<String> isSnmpGet = issnmp.getIsSnmpGet(PDU.GET, ".1.3.6.1.2.1.1.3");
        ipRouteTableMapper.deleteAll();
        nodeMapper.deleteAll();
        ipAddrTableMapper.deleteAll();
        if ("-1".equals(isSnmpGet.get(0))) {
            System.out.println(ip + "该设备未开启snmp服务，或者snmp服务配置错误");
            return null;
        } else {
            List<String> hasSearchList = new ArrayList<String>();
            String[] oids1 = {".1.3.6.1.2.1.4.20.1.1"};//ipAdEntAddr
            String[] oids2 = {".1.3.6.1.2.1.4.20.1.2"};//ipAdEntIfIndex
            String[] oids3 = {".1.3.6.1.2.1.4.20.1.3"};//ipAdEntNetMask
            String[] router1 = {".1.3.6.1.2.1.4.21.1.1"};//ipRouteDest
            String[] router2 = {".1.3.6.1.2.1.4.21.1.2"};//ipRouteIfIndex
            String[] router3 = {".1.3.6.1.2.1.4.21.1.7"};//ipRouteNextHop
            String[] router4 = {".1.3.6.1.2.1.4.21.1.8"};//ipRouteType
            String[] router5 = {".1.3.6.1.2.1.4.21.1.11"};//ipRouteMask
            Node node = new Node();
            node.setIp(ip);
            node.setParentId(0);
            LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();
            queue.offer(node);
            List<Edge> edgeList = new ArrayList<>();
            Edge edge;
            while (!queue.isEmpty()) {
                Node node1 = queue.poll();
                SNMPSessionUtil issnmp2 = new SNMPSessionUtil(node1.getIp(), "161", "public", "2");
                ArrayList<String> isSnmpGet2 = issnmp2.getIsSnmpGet(PDU.GET, ".1.3.6.1.2.1.1.3");
                if ("-1".equals(isSnmpGet2.get(0))) continue;
                if (hasSearchList.contains(node1.getIp())) continue;


                System.out.println("弹出队列:" + node1.getIp());


                if (!node1.getIp().isEmpty()) interfaceService.GetInterInfo(node1.getIp());
                SNMPSessionUtil aPublic = new SNMPSessionUtil(node1.getIp(), "161", "public", "2");
                ArrayList<String> ipAdEntAddr = aPublic.snmpWalk2(oids1);
                ArrayList<String> ipAdEntIfIndex = aPublic.snmpWalk2(oids2);
                ArrayList<String> ipAdEntNetMask = aPublic.snmpWalk2(oids3);
                IpAddrTable ipAddrTable;
                List<IpAddrTable> ipAddrTables = new ArrayList<>();
                for (int i = 0; i < ipAdEntAddr.size(); i++) {
                    ipAddrTable = new IpAddrTable();
                    ipAddrTable.setIp(node1.getIp());
                    ipAddrTable.setIpAdEntAddr(ipAdEntAddr.get(i).substring(ipAdEntAddr.get(i).lastIndexOf("=")).replace("=", "").trim());
                    ipAddrTable.setIpAdEntIfIndex(ipAdEntIfIndex.get(i).substring(ipAdEntIfIndex.get(i).lastIndexOf("=")).replace("=", "").trim());
                    ipAddrTable.setIpAdEntNetMask(ipAdEntNetMask.get(i).substring(ipAdEntNetMask.get(i).lastIndexOf("=")).replace("=", "").trim());
                    ipAddrTables.add(ipAddrTable);
                }
                if (!ipAddrTables.isEmpty()) {
                    ipAddrTableMapper.insert(ipAddrTables);
                } else {
                    ipAddrTableMapper.insertError(node1.getIp());
                }

                String ip1 = node1.getIp();
                IpRouteTable ipRouteTable;
                SNMPSessionUtil nextip = new SNMPSessionUtil(ip1, "161", "public", "2");
                ArrayList<String> rlist1 = nextip.snmpWalk2(router1);
                ArrayList<String> rlist2 = nextip.snmpWalk2(router2);
                ArrayList<String> rlist3 = nextip.snmpWalk2(router3);
                ArrayList<String> rlist4 = nextip.snmpWalk2(router4);
                ArrayList<String> rlist5 = nextip.snmpWalk2(router5);
                List<IpRouteTable> list = new ArrayList<>();
                for (int j = 0; j < rlist1.size(); j++) {
                    ipRouteTable = new IpRouteTable();
                    ipRouteTable.setIp(ip1);
                    ipRouteTable.setIpRouteDest(rlist1.get(j).substring(rlist1.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteIfIndex(rlist2.get(j).substring(rlist2.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteNextHop(rlist3.get(j).substring(rlist3.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteType(rlist4.get(j).substring(rlist4.get(j).lastIndexOf("=")).replace("=", "").trim());
                    ipRouteTable.setIpRouteMask(rlist5.get(j).substring(rlist5.get(j).lastIndexOf("=")).replace("=", "").trim());
                    list.add(ipRouteTable);
                }
                if (list.isEmpty()) {
                    ipRouteTableMapper.insertError(ip1);
                } else {
                    ipRouteTableMapper.insert(list);
                }

                hasSearchList.add(ip1);
                List<String> nextRoute = ipRouteTableMapper.selectNextRoute(ip1);
                List<String> addr = ipAddrTableMapper.selectAddr(ip1);
                if (!addr.isEmpty()) {
                    hasSearchList.addAll(addr);
                }
                Node n;
                for (String s : nextRoute) {
                    if (hasSearchList.contains(s)) continue;
                    edge = new Edge();
                    edge.setSource(ip1);
                    edge.setTarget(s);
                    edgeList.add(edge);
                    n = new Node();
                    n.setParentId(nodeMapper.getParentIp(node1.getIp()));
                    n.setIp(s);
                    queue.offer(n);
                    System.out.println("压入队列:" + n.getIp());
                }
            }
            edgeMapper.insert(edgeList);
            return node;
        }

    }
    public void getDeviceInterInfo() throws Exception {
        List<String> activeDevices =  systemService.getAllActDevice();
        for (String ip : activeDevices) {
            interfaceService.GetInterInfo(ip);
        }
    }
    public void deviceInterfaceMac() throws Exception {
        String [] macs= {"1.3.6.1.2.1.2.2.1.6"};
        String [] indexs= {"1.3.6.1.2.1.2.2.1.1"};
        //1.获取所有活跃设备
        List<String> activeDevices =  systemService.getAllActDevice();
        for (int j =0;j<activeDevices.size();j++) {
            List<InterfaceOfMac> ofMacs = new ArrayList<>();
            SNMPSessionUtil issnmp = new SNMPSessionUtil(activeDevices.get(j), "161", "public", "2");
            if (issnmp.snmpWalk2(macs) == null||issnmp.snmpWalk2(indexs) == null){
                continue;
            }
            ArrayList<String> listMac = issnmp.snmpWalk2(macs);
            ArrayList<String> indexlist = issnmp.snmpWalk2(indexs);
            List<String> hasmac = new ArrayList<>();
            for (int i = 0; i < listMac.size(); i++) {
                String mac = listMac.get(i).substring(listMac.get(i).lastIndexOf("=")).replace("=", "").trim();
                if (hasmac.contains(mac)) continue;
                hasmac.add(mac);
                InterfaceOfMac item = new InterfaceOfMac();
                item.setIp(activeDevices.get(j));
                item.setIfindex(indexlist.get(i).substring(indexlist.get(i).lastIndexOf("=")).replace("=","").trim());
                item.setIfmac(mac);
                ofMacs.add(item);
            }
            macService.addMac(ofMacs);
        }
    }

    public void deviceMacForward(){
        String [] macs= {"1.3.6.1.2.1.17.4.3.1.1"};
        String [] port= {"1.3.6.1.2.1.17.4.3.1.2"};
        List<String> activeDevices =  systemService.getAllActDevice();
        for (String ip : activeDevices) {
            List<MacForward> forwards = new ArrayList<>();
            SNMPSessionUtil issnmp = new SNMPSessionUtil(ip, "161", "public", "2");
            if (issnmp.snmpWalk2(macs) == null||issnmp.snmpWalk2(port) == null){
                continue;
            }
            if (issnmp.snmpWalk2(macs).size() == 0||issnmp.snmpWalk2(port).size() == 0){
                continue;
            }
            ArrayList<String> listMac = issnmp.snmpWalk2(macs);
            ArrayList<String> indexlist = issnmp.snmpWalk2(port);
            for (int i = 0; i < listMac.size(); i++) {
                MacForward item = new MacForward();
                item.setIp(ip);
                item.setPort(indexlist.get(i).substring(indexlist.get(i).lastIndexOf("=")).replace("=","").trim());
                item.setMac(listMac.get(i).substring(listMac.get(i).lastIndexOf("=")).replace("=","").trim());
                forwards.add(item);
            }
            forwardService.addMac(forwards);
        }

    }

    public void indexRelatePort(){
        String [] ifindex= {"1.3.6.1.2.1.17.1.4.1.2"};
        String [] port= {"1.3.6.1.2.1.17.1.4.1.1"};

        List<String> actDevice =  systemService.getAllActDevice();
        for (String ip : actDevice) {
            List<IndexPortRelate> relates = new ArrayList<>();
            SNMPSessionUtil issnmp = new SNMPSessionUtil(ip, "161", "public", "2");
            if (issnmp.snmpWalk2(ifindex) == null||issnmp.snmpWalk2(port) == null ||issnmp.snmpWalk2(ifindex).size() == 0||issnmp.snmpWalk2(port).size() == 0){
                continue;
            }
            ArrayList<String> listMac = issnmp.snmpWalk2(ifindex);
            ArrayList<String> portlist = issnmp.snmpWalk2(port);
            for (int i = 0; i < listMac.size(); i++) {
                IndexPortRelate relate = new IndexPortRelate();
                relate.setIp(ip);
                relate.setPort(portlist.get(i).substring(portlist.get(i).lastIndexOf("=")).replace("=","").trim());
                relate.setIfindex(listMac.get(i).substring(listMac.get(i).lastIndexOf("=")).replace("=","").trim());
                relates.add(relate);
            }
            indexPortService.addRelate(relates);
        }
    }

    public void neighborMac(){
        String[] lldpmac = {"1.0.8802.1.1.2.1.4.1.1.5"};//邻居物理地址
        List<String> actDevice =  systemService.getAllActDevice();
        for (String ip : actDevice) {
            List<NeighborMac> neighborMacs = new ArrayList<>();
            SNMPSessionUtil issnmp = new SNMPSessionUtil(ip, "161", "public", "2");
            if (issnmp.snmpWalk2(lldpmac) == null||issnmp.snmpWalk2(lldpmac).size() == 0){
                continue;
            }
            List<String> lldpmacs = issnmp.snmpWalk2(lldpmac);
            List<String> exist = new ArrayList<>();
            for (String item : lldpmacs) {
                String mac = item.substring(item.lastIndexOf("=")).replace("=", "").trim();
                if (exist.contains(mac)) continue;
                exist.add(mac);
                NeighborMac neighborMac = new NeighborMac();
                neighborMac.setIp(ip);
                neighborMac.setRemmac(mac);
                neighborMacs.add(neighborMac);
            }
            neighborMacService.addRemMac(neighborMacs);
        }

    }

    public void deviceSearch(String ...ips){
        //

    }

    public void deviceSearch(String startIp,String endIp){

    }

    public void connectivelyOfL2ToL2(){
        //1.得到所有L2设备集合
        List<SystemInfo>  listOfL2 = systemService.getAllL2Device();
        for (SystemInfo info : listOfL2) {
            System.out.println(info.getIp());

        }
    }

    public void connectOfAll(){
        List<Connectively> connectivelies = connectivelyService.getConnect();
        for (Connectively con : connectivelies) {
            Connectively connectively = new Connectively();
            connectively.setSip(con.getSip());
            connectively.setDip(con.getDip());
            connectively.setDifindex(con.getDifindex());
            if (connectivelyService.hasSip2Dip(con.getSip(),con.getDip()) || connectivelyService.hasDip2Sip(con.getDip(),con.getSip())) continue;
            connectivelyService.addConnect(connectively);
        }
    }


}
