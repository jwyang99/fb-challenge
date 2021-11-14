import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class FriendbuyAppAfter {

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		AtomicBoolean isTransaction = new AtomicBoolean(false);
		
		Stack<HashMap<String, Integer>> transactions = new Stack<HashMap<String, Integer>>();
		print("start transactions size: "+transactions.size());
		try {
			print("Friendbuy Challenge\n\n");
			
			while  (sc.hasNextLine()) {
				String cmd = sc.next().toUpperCase();
		
			    switch  (cmd)  {
			    	case "GET":			    		
			    		getCmd(sc.next(), table, isTransaction, transactions);			    			
			    		break;
			    	case "SET":
			    		setCmd(sc.next(), sc.nextInt(), table, isTransaction, transactions);
			    		break;
			    	case "UNSET":
			    		unsetCmd(sc.next(), table, transactions, isTransaction);
			    		break;
			    	case "NUMEQUALTO":
			    		numequaltoCmd(sc.nextInt(), table, transactions, isTransaction);
			    		break;			    		
			    	case "END":
			    		System.exit(0);
			    	case "BEGIN":
			    		beginCmd(isTransaction, transactions, table);
			    		break;
			    	case "ROLLBACK":
			    		rollbackCmd(isTransaction, transactions);
			    		break;
			    	case "COMMIT":
			    		commitCmd(table, transactions, isTransaction);
			    		break;
			    	default:
			    		print("\n");
		    	
			    }
		    }
				
		} finally  {
			if (sc != null)
			sc.close();
		}
	    	
	}
	
	private static void unsetCmd(String inKey, HashMap<String, Integer> table, Stack<HashMap<String, Integer>> transactions, AtomicBoolean isTransaction) {

		if (isTransaction.get()) {
			HashMap<String, Integer> currentCache = transactions.pop();
			currentCache.remove(inKey);
			transactions.push(currentCache);
//			print("set transactions size: "+transactions.size());
			
		}
		if (table.containsKey(inKey)) {
			table.remove(inKey);
		} 
		
	}
	
	private static void setCmd(String inKey, Integer inValue, HashMap<String, Integer> table,
			AtomicBoolean isTransaction, Stack<HashMap<String, Integer>> transactions) {

		if (isTransaction.get()) {
			//get current stack and remove old stack
			HashMap<String, Integer> currentCache = transactions.pop();
			currentCache.put(inKey, inValue);
			transactions.push(currentCache);
//			print("set transactions size: "+transactions.size());
		} else {
			table.put(inKey, inValue);
		}
	}
	
	private static void getCmd(String inKey, HashMap<String, Integer> table,
			AtomicBoolean isTransaction, Stack<HashMap<String, Integer>> transactions) {

		if (isTransaction.get()) {
			
			if (!transactions.isEmpty()  && (transactions.peek()).containsKey(inKey) ) {
				print(transactions.peek().get(inKey)+"\n");
//				print("get transactions size: "+transactions.size());
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
	
	private static void numequaltoCmd(Integer inValue, HashMap<String, Integer> table, Stack<HashMap<String, Integer>> transactions, AtomicBoolean isTransaction) {
	
		count = 0;
		
		if (isTransaction.get()) {
			//get current stack
			HashMap<String, Integer> currentCache = transactions.peek();
			if (currentCache.containsValue(inValue)) {
				currentCache.forEach((k,v) -> {if(v == inValue ) {count++;}});
				print(count+"\n");
			} else {
				print(count+"\n");
			}
//			print("set transactions size: "+transactions.size());
		} else {
			if (table.containsValue(inValue)) {
				table.forEach((k,v) -> {if(v == inValue ) {count++;}});
				print(count+"\n");
			} else {
				print(count+"\n");
			}
		}
	}
	
	private static void print(String msg)  {
		System.out.println(msg);
	}
	
	private static void beginCmd(AtomicBoolean isTransaction, Stack<HashMap<String, Integer>> transactions, HashMap<String, Integer> table ) {
		changeBool(isTransaction, true);
		HashMap<String, Integer> newCache = new HashMap<String, Integer>();
		if(!table.isEmpty())  {
			table.forEach((k,v) -> {newCache.put(k, v);});
		}
		
		transactions.push(newCache);
//		print("begin transactions size: "+transactions.size());
		
	}
	
	private static void commitCmd(HashMap<String, Integer> table, Stack<HashMap<String, Integer>> transactions, AtomicBoolean isTransaction) {
		//get the latest stack to commit
		HashMap<String, Integer> latestCache = transactions.peek();
	
		latestCache.forEach((k,v) -> {table.put(k, v);});
		transactions.clear();
		changeBool(isTransaction, false);
		
		
	}
	
	private static void rollbackCmd(AtomicBoolean isTransaction, Stack<HashMap<String, Integer>> transactions  ) {
		
		if (!transactions.isEmpty()) {
			transactions.pop();
//			print("transactions size: "+transactions.size());
		} else {
			print("NO TRANSACTION\n");
		}
		
	}
	
	private static void changeBool (AtomicBoolean isTransaction, boolean value) {
		isTransaction.set(value);
	}

}