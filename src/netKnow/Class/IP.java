package netKnow.Class;

public class IP {
    private char [] ipArray;
    private char [] maskArray;
    private String fullIPAdress [];

    public IP(String [] fullIPAdress){
        ipArray = new char[4];
        maskArray = new char[4];
        this.fullIPAdress = fullIPAdress;
        convertStringToByte(fullIPAdress);
    }

    private void convertStringToByte(String [] fullIPAdress){
        System.out.println("ELDO");

        for (int i=0; i<4; i++){
            System.out.println(fullIPAdress[i]);
        }

        ipArray[0] = (char) Integer.parseInt(fullIPAdress[0]);
        ipArray[1] = (char) Integer.parseInt(fullIPAdress[1]);
        ipArray[2] = (char) Integer.parseInt(fullIPAdress[2]);
        ipArray[3] = (char) Integer.parseInt(fullIPAdress[3]);

        for (int i=0; i<4; i++){
            System.out.println(ipArray[i]);
        }
    }
}
