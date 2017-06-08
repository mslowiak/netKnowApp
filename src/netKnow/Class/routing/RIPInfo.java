package netKnow.Class.routing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-06-05.
 */
public class RIPInfo {

    private DraggableNode routerFrom;
    public List<RIPWay> ripWayList;
    private DraggableNode[] prev;
    private List<DraggableNode> orderedRouters;

    public RIPInfo(DraggableNode[] prev, List<DraggableNode> orderedRouters){
        routerFrom = orderedRouters.get(0);
        this.prev = prev;
        this.orderedRouters = orderedRouters;
        ripWayList = new ArrayList<>();
        fillWayList();
        showWayList();
    }

    public void showWayList(){
        System.out.println("Way for: " + routerFrom.titleBar.getText());
        for(int i=0; i<ripWayList.size(); ++i){
            RIPWay ripWay = ripWayList.get(i);
            System.out.println("\t" + ripWay.getDestination().titleBar.getText());
            System.out.print("\t\t");
            for(int j=0; j<ripWay.getWay().size(); ++j){
                System.out.print(ripWay.getWay().get(j).titleBar.getText()+", ");
            }
            System.out.println();
        }
    }

    public void fillWayList(){
        for(int i=1; i<prev.length; ++i){
            createNewWay(orderedRouters.get(i));
            addToLatestWayFront(orderedRouters.get(i));
            DraggableNode tmp = prev[i];
            while(tmp != routerFrom){
                addToLatestWayFront(tmp);
                int index = findIndexRouter(tmp);
                tmp = prev[index];
            }
            addToLatestWayFront(routerFrom);
        }
    }

    public int findIndexRouter(DraggableNode toFind){
        for(int j=0; j<orderedRouters.size(); ++j){
            if(toFind == orderedRouters.get(j)){
                return j;
            }
        }
        return -1;
    }

    public void createNewWay(DraggableNode x){
        ripWayList.add(new RIPWay(x));
    }

    public void addToLatestWayFront(DraggableNode x){
        ripWayList.get(ripWayList.size()-1).addToStart(x);
    }

    public void addToLatestWayRear(DraggableNode x){
        ripWayList.get(ripWayList.size()-1).addToEnd(x);
    }
}
