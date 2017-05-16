package netKnow.Class;

public class IP {
    private int [] ipArray;
    private int [] maskArray;
    private String fullIPAdress [];

    private String network;
    private String broadcast;
    private String maxHost;
    private String minHost;
    private String numberOfHosts;

    public IP(String [] fullIPAdress){
        ipArray = new int[4];
        maskArray = new int[4];
        this.fullIPAdress = fullIPAdress;
    }

    private void convertStringToIPAdress(String [] fullIPAdress){
        for(int i = 0; i < 4; i++) {
            ipArray[i] = Integer.parseInt(fullIPAdress[i]);
        }
    }

    private void convertStringToMask(String mask){
        int n = Integer.parseInt(fullIPAdress[4]);
        for(int i = 0; i < n; i++){
            maskArray[i/8] |=  (128 >> (i%8));
        }
    }

    public void computeData(){
        convertStringToIPAdress(fullIPAdress);
        convertStringToMask(fullIPAdress[4]);
        network = computeNetwork();
        broadcast = computeBroadcast();
        numberOfHosts = Integer.toString(numberOfHosts());
        minHost = minHost();
        maxHost = maxHost();
    }

    private String computeNetwork(){
        String result = "";
        for(int i = 0; i < 4; i++) {
            result += (ipArray[i] & maskArray[i]) + ".";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }
    private String computeBroadcast(){
        String result = "";
        for(int i = 0; i < 4; i++){
            result +=  (((~maskArray[i] ^ (1 << 31)) - 2147483392) | ipArray[i]) + ".";
        }
        result = result.substring(0, result.length()-1);
        return result;
    }

    private int numberOfHosts(){
        int n = Integer.parseInt(fullIPAdress[4]);
        return (int) (Math.pow(2,(double) (32 - n)) - 2);
    }

    private String minHost(){
        String [] tmp1 = computeNetwork().split("\\.");
        int tmp2 = Integer.parseInt(tmp1[tmp1.length-1]);
        tmp2++;
        return (tmp1[0] + "." + tmp1[1] + "." + tmp1[2] + "." + tmp2);
    }

    private String maxHost()
    {
        String [] tmp1 = computeBroadcast().split("\\.");
        int tmp2 = Integer.parseInt(tmp1[tmp1.length-1]);
        tmp2--;
        return (tmp1[0] + "." + tmp1[1] + "." + tmp1[2] + "." + tmp2);
    }

    public String getNetwork() {
        return network;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public String getMaxHost() {
        return maxHost;
    }

    public String getMinHost() {
        return minHost;
    }

    public String getNumberOfHosts() {
        return numberOfHosts;
    }
}
