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

    public void setOctetFirst(String octetFirst) {
        this.octetFirst = octetFirst;
    }

    public void setOctetSecond(String octetSecond) {
        this.octetSecond = octetSecond;
    }

    public void setOctetThird(String octetThird) {
        this.octetThird = octetThird;
    }

    public void setOctetFourth(String octetFourth) {
        this.octetFourth = octetFourth;
    }

    public String getAddress(){
        return octetFirst + "." + octetSecond + "." + octetThird + "." + octetFourth + "/" + mask;
    }
}
