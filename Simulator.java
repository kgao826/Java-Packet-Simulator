import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * <p> This class allows the user to turn a text file in a preformatted text file for packets
 * into an ArrayList of Packets. The methods allow the data extracted to show valid packets,
 * unique source hosts and unique destination hosts.
 * The class also contains a method which turns the data into an Array for use in a JTable</p>
 * 
 * <p> This class requires the following classes to run: Host, Packet, classes. </p>
 * 
 * @author Kevin A Gao
 */

class Simulator {
    private ArrayList<Packet> packetList = new ArrayList<Packet>();
    /**
     * <p>This method opens a text file in a preformatted text file for packets and reads the lines
     * to create every valid line into a Packet.
     * This method uses the Packet class.</p>
     * 
     * @param file  File path of the text file
     * @throws      FileNotFoundException if the file path does not exist.
     */
    public Simulator(File file){
        Scanner input = null;
        try {
            input = new Scanner(file);
            while (input.hasNextLine()){
                String line = input.nextLine();
                Packet packet = new Packet(line);
                if (packet.getSourceHost().length() != 0){
                    packetList.add(packet);
                }
            }
            input.close();
        }
        catch (FileNotFoundException fnfExc){
            System.out.printf("java.io.FileNotFoundException: %s (No such file or directory)\n", file.getPath());
        }

    }
    /**
     * Returns an ArrayList of Packets that were valid from the file
     * 
     * @return      An ArrayList of Packets
     */
    public ArrayList<Packet> getValidPackets(){
        return packetList;
    }
    /**
     * Returns unique and sorted source hosts in an Array.
     * 
     * @return      An Array of Strings of sorted and unique source hosts
     */
	public Host[] getUniqueSortedSourceHosts(){
		ArrayList<String> sourceHostsArray = new ArrayList<String>();
		for(Packet packet : this.packetList){
			String srcHostString = packet.getSourceHost();
			if (srcHostString.length() != 0){
				if (!sourceHostsArray.contains(srcHostString)){
					sourceHostsArray.add(srcHostString);
				}
			}
		}
		Host[] output = new Host[sourceHostsArray.size()];
		for(int i = 0; i < sourceHostsArray.size(); i++){
		    Host value = new Host(sourceHostsArray.get(i));
		    output[i] = value;
		}
		Arrays.sort(output);
        return output;
    }
    /**
     * Returns unique and sorted destination hosts in an Array.
     * 
     * @return      An Array of Strings of sorted and unique destination hosts
     */
	public Host[] getUniqueSortedDestHosts(){
		ArrayList<String> destHostsArray = new ArrayList<String>();
		for(Packet packet : this.packetList){
			String destHostString = packet.getDestinationHost();
			if (destHostString.length() != 0){
				if (!destHostsArray.contains(destHostString)){
					destHostsArray.add(destHostString);
				}
			}
		}
		Host[] output = new Host[destHostsArray.size()];
		for(int i = 0; i < destHostsArray.size(); i++){
		    Host value = new Host(destHostsArray.get(i));
		    output[i] = value;
		}
		Arrays.sort(output);
        return output;
    }
    /**
     * <p>Return an Array of Packets that can be used in a future JTable.
     * Takes in the String that is going to be filtered in the data
     * and a boolean, True if it is a Source and false if it is a Destination host
     * that will pick the type of data for that table. </p>
     * 
     * @param ip    The host String that is required
     * @param type  True if it is a Source and false if it is a Destination host
     * @return      An Array of Packets for use to create a JTable
     */
	public Packet[] getTableData(String ip, Boolean type){
        ArrayList<Packet> selectedPackets = new ArrayList<Packet>();
        if(type){
            for(Packet packet : packetList){
                if(packet.getSourceHost().equals(ip)) selectedPackets.add(packet);
            }
        }
        else {
            for(Packet packet : packetList){
                if(packet.getDestinationHost().equals(ip)) selectedPackets.add(packet);
            }
        }
        Packet[] result = selectedPackets.toArray(new Packet[selectedPackets.size()]);
        return result;
    }
}
