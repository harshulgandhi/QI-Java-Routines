import java.util.Arrays;
import java.util.List;


public class StringToList {
	public static void main(String[] args) {
		StringToList obj = new StringToList();
		String str = "[HE12 , HLM  , RHM  , BM29 , Q81  , C2  ]";
		
		List<String> list = obj.getList(str);
		System.out.println(list);
		System.out.println(list.get(5));
		
	}
	public  static List<String> getList(String str){
		str=str.replaceAll("\\[|\\]","");
		str=str.replaceAll(" ","");
		List<String> list;
		list = Arrays.asList(str.split("\\s*,\\s*"));
		return list;
	}
	
	
	public static int listLength(List<String> list){
		return list.size();
	}
}