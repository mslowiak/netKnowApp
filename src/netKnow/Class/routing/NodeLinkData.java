package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-17.
 */
public class NodeLinkData {
    private String octetFirst;
    private String octetSecond;
    private String octetThird;
    private String octetFourth;
    private String mask;
    private String typeOfConnection;

    public NodeLinkData(String octetFirst, String octetSecond, String octetThird, String octetFourth, String mask, String typeOfConnection) {
        this.octetFirst = octetFirst;
        this.octetSecond = octetSecond;
        this.octetThird = octetThird;
        this.octetFourth = octetFourth;
        this.mask = mask;
        this.typeOfConnection = typeOfConnection;
    }

    public String getAddress(){
        return octetFirst + "." + octetSecond + "." + octetThird + "." + octetFourth + "/" + mask;
    }

    public String getAddressToInterface(){
        return octetFirst + "." + octetSecond + "." + octetThird + ".";
    }

    public String getMask() {
        return mask;
    }

    public String getTypeOfConnection(){
        return typeOfConnection;
    }

    public String getMaskCisco(){
        int cidrMask = Integer.parseInt(mask);
        long bits = 0;
        bits = 0xffffffff ^ (1 << 32 - cidrMask) - 1;
        String mask = String.format("%d.%d.%d.%d", (bits & 0x0000000000ff000000L) >> 24, (bits & 0x0000000000ff0000) >> 16, (bits & 0x0000000000ff00) >> 8, bits & 0xff);
        return mask;
    }
}
