import java.util.*;
import javax.swing.table.*;

/**
 * This class allows the creation of a table model for use in the 
 * JTable that will allow the editing of data in the table.
 * All the method in this class override the extended class's methods.
 * 
 * @author Kevin A Gao
 */
class PacketTableModel extends AbstractTableModel{
    private ArrayList<Packet> packetsList = new ArrayList<Packet>();
    private boolean type;
    private double average;
    private int sum;
    public PacketTableModel(Packet[] packets, boolean type){
        for(Packet packet : packets){
            this.packetsList.add(packet);
            this.sum += packet.getIpPacketSize();
        }
        this.type = type;
        this.average = this.sum / this.packetsList.size();
    }
    public int getColumnCount(){
        return 3;
    }
    public int getRowCount(){
        return this.packetsList.size() + 2;
    }
    public Object getValueAt(int column, int row){
        Object value = new Object();
        if(column == (getRowCount() - 2)){
            return value = sum;
        }
        if(column == (getRowCount() - 1)){
            return value = average;
        }
        Packet current = packetsList.get(column);
        if(type){
            switch(row) {
                case 0:
                    return value = current.getTimeStamp();
                case 1:
                    return value = current.getDestinationHost();
                case 2:
                    return value = current.getIpPacketSize();
                default:
                    return value = "";
            }
        }
        else{
            switch(row) {
                case 0:
                    return value = current.getTimeStamp();
                case 1:
                    return value = current.getSourceHost();
                case 2:
                    return value = current.getIpPacketSize();
                default:
                    return value = "";
            }
        }
    }
}