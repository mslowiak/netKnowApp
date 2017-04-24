package netKnow.Class;

public class IP {
    private int [] ipArray;
    private int [] maskArray;
    private int maskAsInteger;
    private String fullIPAdress [];

    public IP(String [] fullIPAdress){
        ipArray = new int[4];
        maskArray = new int[4];
        maskAsInteger = 0;
        this.fullIPAdress = fullIPAdress;
        convertStringToIPAdress(fullIPAdress);
        convertStringToMask(fullIPAdress[4]);
        System.out.println(computeNetwork());
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
            maskAsInteger++;
        }
    }

    private String computeNetwork(){
        int [] tmp = new int[4];
        String result = "";
        for(int i = 0; i < 4; i++) {
            result += (ipArray[i] & maskArray[i]) + ".";
        }
        return result;
    }
    private String computeBrodcast(){
        String result;
        for(int i = 0; i < 32 - maskAsInteger; i++){

        }
        return "";
    }
}
