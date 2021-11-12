import java.util.HashMap;
import java.util.Scanner;

public class FriendbuyApp {

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		HashMap<String, Integer> tempTable = new HashMap<String, Integer>();
		boolean isTransaction = false;
		
		try {
			print("Friendbuy Challenge\n\n");
			
			while  (sc.hasNextLine()) {
				String cmd = sc.next().toUpperCase();
		
			    switch  (cmd)  {
			    	case "GET":			    		
			    		getCmd(sc.next(), table, tempTable, isTransaction);			    			
			    		break;
			    	case "SET":
			    		setCmd(sc.next(), sc.nextInt(), table, tempTable, isTransaction);
			    		break;
			    	case "UNSET":
			    		unsetCmd(sc.next(), table);
			    		break;
			    	case "NUMEQUALTO":
			    		numequaltoCmd(sc.nextInt(), table);
			    		break;			    		
			    	case "END":
			    		System.exit(0);
			    	case "BEGIN":
			    		beginCmd(isTransaction);
			    	case "ROLLBACK":
			    		rollbackCmd(isTransaction, tempTable);
			    	case "COMMIT":
			    		commitCmd(table, tempTable);
			    	default:
			    		print("\n");
		    	
			    }
		    }
				
		} finally  {
			if (sc != null)
			sc.close();
		}
	    	
	}
	
	private static void unsetCmd(String inKey, HashMap<String, Integer> table) {
		if (table.containsKey(inKey)) {
			table.remove(inKey);
		} 
		
	}
	
	private static void setCmd(String inKey, Integer inValue, HashMap<String, Integer> table, HashMap<String, Integer> tempTable, boolean isTransaction) {

		if (isTransaction) {
			tempTable.put(inKey, inValue);
		} else {
			table.put(inKey, inValue);
		}
	}
	
	private static void getCmd(String inKey, HashMap<String, Integer> table, HashMap<String, Integer> tempTable, boolean isTransaction) {

		if (isTransaction) {
			if (tempTable.containsKey(inKey)) {
				print(table.get(inKey)+"\n");
			} else {
				print("NULL"+"\n");
			}
		} else {
			if (table.containsKey(inKey)) {
				print(table.get(inKey)+"\n");
			} else {
				print("NULL"+"\n");
			}
		}
	}
	
	private static int count;
	
	private static void numequaltoCmd(Integer inValue, HashMap<String, Integer> table) {
	
		count = 0;
		if (table.containsValue(inValue)) {
			table.forEach((k,v) -> {if(v == inValue ) {count++;}});
			print(count+"\n");
		} else {
			print(count+"\n");
		}
	}
	
	private static void print(String msg)  {
		System.out.println(msg);
	}
	
	private static void beginCmd(boolean isTransaction) {
		
		isTransaction = true;
		
	}
	
	private static void commitCmd(HashMap<String, Integer> table, HashMap<String, Integer> tempTable) {
		
		table.putAll(tempTable);
		
	}
	
	private static void rollbackCmd(boolean isTransaction, HashMap<String, Integer> tempTable ) {
		
		isTransaction = false;
		tempTable = new HashMap<String, Integer>();
		
	}
	

	
	

}