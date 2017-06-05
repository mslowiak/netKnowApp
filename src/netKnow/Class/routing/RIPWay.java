package netKnow.Class.routing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-06-06.
 */
public class RIPWay {
    private DraggableNode destination;
    private List<DraggableNode> way;

    public RIPWay(DraggableNode dest){
        destination = dest;
        way = new ArrayList<>();
    }

    public void addToEnd(DraggableNode x){
        way.add(x);
    }

    public void addToStart(DraggableNode x){
        way.add(0, x);
    }

    public DraggableNode getDestination() {
        return destination;
    }

    public void setDestination(DraggableNode destination) {
        this.destination = destination;
    }

    public List<DraggableNode> getWay() {
        return way;
    }
}
