import java.util.ArrayList;

public class JUnitUsingTest {
	
	private static DLL KeyWordList = new DLL();
	
	private class Krec {
       
		String KeyWord;
		DLL Lines = new DLL();

		public Krec(String kw)     {KeyWord = kw;      }  // 생성자

		public void   storeKeyWordline(Integer lineNum) 
                                  {Lines.insertDLL(Lines.numberDLL(),lineNum); }
		public String getKeyWord()      {return KeyWord;    }
		public DLL    getKeyWordlines() {return Lines; }
	}
	
	public static String Command_W(File_Buffer FILE) {
		return "At Edit File Line " + FILE.GetCLN();
	}
	
	public static String Command_C(File_Buffer FILE) {
	    return "Total Edit File Lines: " + FILE.NumLins();
	}
	
	public static ArrayList<String> Command_L(File_Buffer FILE, int nLines) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		int CLN = FILE.GetCLN();
        int end = Math.min(CLN+nLines-1,FILE.NumLins());
        
        for(int i=CLN; i<=end; i++) 
        	list.add(FILE.GetLine(i));
     
        return list;
	}
	
	public static ArrayList<String> Command_S(File_Buffer FILE, int nLines) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		int CLN = FILE.GetCLN();
        int end = Math.min(CLN+nLines-1,FILE.NumLins());
        
        for(int i=CLN; i<=end; i++) 
        	list.add(FILE.GetLine(i));
        
        return list;
	}
	
	public static ArrayList<String> Command_D(File_Buffer FILE, int nLines) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		int CLN = FILE.GetCLN();
        int end = Math.min(CLN+nLines-1,FILE.NumLins());
        
        for(int i=CLN; i<end; i++) 
        	list.add(FILE.GetLine(i));
        
        // 오류가 존재하는 부분 : i<=end 까지 for 문을 실행해야 함
        for(int i=CLN; i<end; i++)
        	FILE.DelLine(CLN);
        
        return list;
	}
	
	
	
	public static String Command_K(String keyword) {
		
		int i, j, num_KeyWord, nlines; 
		boolean found;
		DLL lineNums;
		Krec KeyWordRec;
		
       // Check for null keyword length string & return if so

		if(keyword.length() == 0) {
          return "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken.";
       }

       // Check for no keyword list & return if so

       if(KeyWordList == null) 
    	   return "THIS KEYWORD DOES NOT EXIST:  No action taken.";

       // Now scan the keywords and find a match: if so print line numbers

       found  = false;
       num_KeyWord = KeyWordList.numberDLL();

       for(i=1; i<=num_KeyWord; i++) 
       {
           KeyWordRec = (Krec) KeyWordList.getDLL(i);
           if((KeyWordRec.getKeyWord()).compareTo(keyword) == 0) 
           {
              lineNums = (DLL) KeyWordRec.getKeyWordlines();
              nlines   = lineNums.numberDLL();
              for(j=1; j<=nlines; j++) 
                return "" + ((Integer) lineNums.getDLL(j)).intValue() + "  ";
              found=true;
              break;
           }
       }

       // If no keyword match then write this to the user
       return "THIS KEYWORD DOES NOT EXIST:  No action taken.";

	}
	
	 public static String Command_M(int M_left, int M_right) {
		 
		 String str = "";
		 
		 if((M_left <= 0) && (M_right <= 0))
			 str += "COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken.\n";
		 else if(M_left > M_right)
			 str += "REVERSED OR BACKWARDS COLUMN RANGES ARE ILLEGAL:  No action taken.\n";
		 else {
			 //Margin_Left  = M_left ;
			 //Margin_Right = M_right;
		 }
		 
		 return (str += "COMMAND NOT IMPLEMENTED (for F, R, O) YET\n");
	 }
}
