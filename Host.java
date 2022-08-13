/**
 * <p>This class allows an IPv4 String to be converted into the Host class
 * The class allows the IPv4 address to be sorted in order and turned back
 * into String format</p>
 * 
 * @author Kevin A Gao
 */

class Host implements Comparable<Host>{
    private String ip;
    /**
     * This method takes in a String of IPV4 and store it in the class
     * @param ip    An IPV4 address
     */
    public Host(String ip){
        this.ip = ip;
    }

    public String toString(){
        return ip;
    }

    public int compareTo(Host other){
        String strOther = other.toString();
        String[] thisIp = this.ip.split("\\.");
        String[] otherIp = strOther.split("\\.");
        for (int i = 0; i < 4; i++){
            int current = Integer.parseInt(thisIp[i]);
            int otherInt = Integer.parseInt(otherIp[i]);
            int difference = current - otherInt;
            if (difference != 0){
                return difference;
            }
        }
        return 0;
    }
}