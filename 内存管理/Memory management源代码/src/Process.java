public class Process {
    int number; 
    int startAddress;
    int length; 
    int flag;
    public Process(int number, int startAddress, int length, int flag) {
        super();
        this.number = number;
        this.startAddress = startAddress;
        this.length = length;
        this.flag = flag;
    }
    public Process() {
        super();
    }
    @Override
    public String toString() {
    	if(flag==0)
    		return String.format("%03d ",startAddress)
                    + "～" + (startAddress+length) + " 空 闲";
    	else return String.format("%03d ",startAddress)
                + "～" + (startAddress+length) + " 任务"+number;
        
    }
}