package netKnow.Code;

import netKnow.Class.IP;

import java.util.Arrays;


/**
 * Created by Kuba on 16.05.2017.
 */
public class NetworkAggregation {
    private IP[] IPArray;

    public NetworkAggregation(IP[] Array) {
        this.IPArray = Array;
    }

    public IP[] aggregateNetwork() {
        int [][] tmp = new int[IPArray.length][4];
        IP [] result = new IP[IPArray.length];
        for (int i = 0; i < IPArray.length; i++) {
            tmp[i] =  IPArray[i].getIPAsInt();
        }
        Arrays.sort(IPArray);
        int k = 0;
        for(int i = 0; i < IPArray.length; i++){
            int [] tmp2 = tmp[i];
            int [] tmp3 = IPArray[i].getIPAsInt();
            int j = IPArray[i].getMaskAsInt() - 1;
            int mask = j+1;
            String [] tmpString1 = {Integer.toString(tmp3[0]), Integer.toString(tmp3[1]), Integer.toString(tmp3[2]),
                    Integer.toString(tmp3[3]), Integer.toString(mask)};
            result[k] = new IP(tmpString1);
            while((tmp3[j / 8] & (128 >> j%8)) == 0) {
                tmp2[j / 8] |= 128 >> j % 8;
                String[] tmpString = {Integer.toString(tmp2[0]), Integer.toString(tmp2[1]), Integer.toString(tmp2[2]),
                        Integer.toString(tmp2[3]), Integer.toString(j)};
                IP newIP = new IP(tmpString);
                int key = Arrays.binarySearch(IPArray, newIP);
                if (key > 0 && key < IPArray.length && IPArray[key].compareTo(newIP) == 0) {
                    i = key;
                    mask = j;
                }
                result[k].setMask(mask);
                j--;
            }
            k++;
        }
        return result;
    }
}