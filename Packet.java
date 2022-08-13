/**
 * <p>This class takes a line from the preformatted packet String and 
 * turns it into a Packet which also is able to
 * split the content of that line into different parts.
 * The parts that are taken out of the input packet String are:
 * time, source host, destination hose and size of the packet.
 * There are mutators in this class which allows changes to the 
 * time, source host, destination hose and size of the packet.
 * The class can also turn the packet into a formated String 
 * of the packet when the toString() method is called upon.</p>
 * 
 * @author Kevin A Gao
 */

class Packet {
    private String sourceHost;
    private String destHost;
    private double time;
    private int size;
    /**
     * This takes in an input String and splits the content up into 
     * four unique data: source ip, destination ip, time and size.
     * 
     * @param input     One line of Stirng of a packet.
     */
    public Packet(String input){
        String[] inputList = input.split("\\t{1}");
        this.time = Double.parseDouble(inputList[1]);
        this.sourceHost = inputList[2];
        this.destHost = inputList[4];
        if(sourceHost.length() != 0){
            this.size = Integer.parseInt(inputList[7]);
        }
    }
    /**
     * Returns the source ip address.
     * @return A Stirng of the source ip
     */
    public String getSourceHost(){
        return this.sourceHost;
    }
    /**
     * Returns the destination ip address.
     * @return A Stirng of the destination ip
     */
    public String getDestinationHost(){
        return this.destHost;
    }
    /**
     * Returns the time stamp as a decimal
     * @return A double of the time
     */
    public double getTimeStamp(){
        return this.time;
    }
    /**
     * Returns the size of the ip packet
     * @return An interger of the size of the packet
     */
    public int getIpPacketSize(){
        return this.size;
    }
    /**
     * Alters the source host to the source host Stirng of the parameter
     * @param sourceHost A String of the source ip address
     */
    public void setSourceHost(String sourceHost){
        this.sourceHost = sourceHost;
    }
    /**
     * Alters the destination host to the destination host Stirng of the parameter
     * @param destHost A String of the destination ip address
     */
    public void setDestinationHost(String destHost){
        this.destHost = destHost;
    }
    /**
     * Alters the time of the packet to that given in the parameter
     * @param time A decimal of the time of the packet
     */
    public void setTimeStamp(double time){
        this.time = time;
    }
    /**
     * Alters the packet size of the packet
     * @param size  An integer of the size of the packet
     */
    public void setIpPacketSize(int size){
        this.size = size;
    }
    public String toString(){
        return String.format("src=%s, dest=%s, time=%.2f, size=%d", this.sourceHost, this.destHost, this.time, this.size);
    }
}