package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-18.
 */
public class DraggableNodeData {

    private String name;
    private String host;
    private String ip;

    public DraggableNodeData(String name, String x){
        this.name = name;
        if(x.length() > 3){
            this.ip = x;
        }else{
            this.host = x;
        }
    }

    public String getName(){
        return name;
    }

    public String getHost(){
        return host;
    }

    public String getIp() {
        return ip;
    }
}
