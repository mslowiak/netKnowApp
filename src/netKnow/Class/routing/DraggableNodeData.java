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
            System.out.println("IP: " + this.ip);
        }else{
            this.host = x;
            System.out.println("host: " + this.host);
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

    public String getIpWithoutMask(){
        String [] pp = ip.split("/");
        return pp[0];
    }

    public String getMask(){
        System.out.println("MY IP IS: " + ip);
        String [] pp = ip.split("/");
        int cidrMask = Integer.parseInt(pp[1]);
        long bits = 0;
        bits = 0xffffffff ^ (1 << 32 - cidrMask) - 1;
        String mask = String.format("%d.%d.%d.%d", (bits & 0x0000000000ff000000L) >> 24, (bits & 0x0000000000ff0000) >> 16, (bits & 0x0000000000ff00) >> 8, bits & 0xff);
        return mask;
    }
}
