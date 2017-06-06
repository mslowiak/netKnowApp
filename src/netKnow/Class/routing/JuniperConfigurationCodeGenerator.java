    package netKnow.Class.routing;

import java.util.List;

/**
 * Created by MQ on 2017-06-06.
 */
public class JuniperConfigurationCodeGenerator {

    List<DraggableNode> routersList;
    List<DraggableNode> nodes;

    public JuniperConfigurationCodeGenerator(List<DraggableNode> routersList, List<DraggableNode> nodes){
        this.routersList = routersList;
        this.nodes = nodes;
    }

    public String getConfiguration(){
        String out = "";
        for(int i=0; i<routersList.size(); ++i){
            out += "\n\nKonfiguracja dla routera: " + routersList.get(i).titleBar.getText() + "\n";
            out += "============================================================================\n";
            out += goToConfiguration();
            out += setHostName(routersList.get(i).titleBar.getText());
            out += commit();
            out += setInterfaces(i);
            out += commit();
            out += setRoutingOptions(i);
            out += commit();
        }
        return out;
    }

    private String goToConfiguration(){
        String out = "";
        out += "cli\n";
        out += "configure\n";
        return out;
    }

    private String commit(){
        return "commit" + "\nexit\n";
    }

    private String setHostName(String hostName){
        String out = "";
        out += "edit system\n";
        out += "set host-name " + hostName + "\n";
        return out;
    }

    private String setInterfaces(int index){
        String out = "";
        int geNumber = 0;
        int seNumber = 0;
        DraggableNode interfaceNode = routersList.get(index);
        for(int i=0; i<interfaceNode.nodeLinks.size(); ++i){
            NodeLink nodeLink = interfaceNode.nodeLinks.get(i);
            out += "edit interfaces ";
            if(nodeLink.nodeLinkData.getTypeOfConnection().equals("Kabel lan")){
                out += "ge-0/0/" + geNumber + "\n";
                geNumber++;
            }else{
                out += "se-0/0/" + seNumber + "\n";
                out += "set encapsulation ppp" + "\n";
                seNumber++;
            }
            out += "set unit 0 family inet address " + nodeLink.nodeLinkData.getAddressToInterface() + interfaceNode.draggableNodeData.getHost() + "/" + nodeLink.nodeLinkData.getMask() + "\n";
            out += "activate unit 0 family inet address " + nodeLink.nodeLinkData.getAddressToInterface() + interfaceNode.draggableNodeData.getHost() + "/" + nodeLink.nodeLinkData.getMask() + "\n";
        }
        return out;
    }

    private String setRoutingOptions(int index){
        String out = "edit routing-options static\n";
        DraggableNode interfaceNode = routersList.get(index);
        for(int i=0; i<interfaceNode.nodeLinks.size(); ++i){
            DraggableNode connectedToInterfaceNode;
            if(interfaceNode.nodeLinks.get(i).startIDNode.equals(interfaceNode.getId())){
                connectedToInterfaceNode = getFriend(interfaceNode.nodeLinks.get(i).endIDNode);
            }else{
                connectedToInterfaceNode = getFriend(interfaceNode.nodeLinks.get(i).startIDNode);
            }
            if(connectedToInterfaceNode.getType().equals(DragIconType.switchIco)){
                for(int j=0; j<connectedToInterfaceNode.nodeLinks.size(); ++j){
                    DraggableNode fromSwitchNode;
                    if(connectedToInterfaceNode.nodeLinks.get(j).startIDNode.equals(connectedToInterfaceNode.getId())){
                        fromSwitchNode = getFriend(connectedToInterfaceNode.nodeLinks.get(j).endIDNode);
                    }else{
                        fromSwitchNode = getFriend(connectedToInterfaceNode.nodeLinks.get(j).startIDNode);
                    }
                    if(fromSwitchNode != interfaceNode){
                        NodeLinkData data = connectedToInterfaceNode.nodeLinks.get(j).nodeLinkData;
                        out += "set route ";
                        out += connectedToInterfaceNode.pcList.get(j).draggableNodeData.getIp();
                        out += " next-hop ";
                        out += data.getAddressToInterface() + connectedToInterfaceNode.draggableNodeData.getHost() + "\n";
                    }
                }
            }else if(connectedToInterfaceNode.getType().equals(DragIconType.routerIco)){
                if(connectedToInterfaceNode.nodePCLink.size() > 0){
                    NodeLinkData data = connectedToInterfaceNode.nodeLinks.get(i).nodeLinkData;
                    for(int j=0; j<connectedToInterfaceNode.nodePCLink.size(); ++j){
                        String pcId;
                        if(connectedToInterfaceNode.nodePCLink.get(j).startIDNode.equals(connectedToInterfaceNode.getId())){
                            pcId = connectedToInterfaceNode.nodePCLink.get(j).endIDNode;
                        }else{
                            pcId = connectedToInterfaceNode.nodePCLink.get(j).startIDNode;
                        }
                        for(int k=0; k<connectedToInterfaceNode.pcList.size(); ++k){
                            if(connectedToInterfaceNode.pcList.get(k).getId().equals(pcId)){
                                out += "set route ";
                                out += connectedToInterfaceNode.pcList.get(k).draggableNodeData.getIp();
                                out += " next-hop ";
                                out += data.getAddressToInterface() + connectedToInterfaceNode.draggableNodeData.getHost() + "\n";
                            }
                        }
                    }
                }
            }
        }
        return out;
    }

    private DraggableNode getFriend(String id){
        for(int j=0; j<nodes.size(); ++j){
            if(nodes.get(j).getId().equals(id)){
                return nodes.get(j);
            }
        }
        return null;
    }
}
