package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-18.
 */
public class DraggableNodeData {

    private String name;
    private String host;

    public DraggableNodeData(String name, String host){
        this.name = name;
        this.host = host;
    }

    public String getName(){
        return name;
    }

    public String getHost(){
        return host;
    }
}
