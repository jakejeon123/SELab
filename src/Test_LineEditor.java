import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class Test_LineEditor extends TestCase {

	private File_Buffer FILE;
	private Init_Exit Start_Finish;
    private Cmd_Driver Command_Test;
    private UserCmd User_Cmd;
    private String[] File_Loc = {"test.txt" };
    private boolean done;
    private boolean UPDATE_FILE = true;

    @BeforeClass
    // 각 테스트가 실행되기전 매번 실행될 함수
	protected void Test_Ready() throws Exception {
		
		 FILE = new File_Buffer();
	     Start_Finish = new Init_Exit(File_Loc, FILE);
	     Command_Test = new Cmd_Driver();

	     done = false;
	     super.setUp();
	}

	
	@Test
	public void test_Q() {
		
		int Num_Line = FILE.NumLins();
		
		done = true;
        FILE.setUpdateFlag(UPDATE_FILE);
        
        try {
        	// Q 는 업데이트 후 저장을 해주는 것이다.
        	// Cmd_D 를 사용하여 문장을 삭제한 후 저장이 되었는지 확인한다. 
        	Command_Test.Cmd_D(FILE, 1);
        	
        	Start_Finish.Do_Update(FILE);
			assertEquals("micheal jackson - the way you make me feel", FILE.GetLine(1));
			assertEquals(Num_Line - 1, FILE.NumLins());
        	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_X() {
		
		int Num_Line = FILE.NumLins();
		
		done = true;
        FILE.setUpdateFlag(!UPDATE_FILE);
        
        try {
        	// X 는 업데이트 되지 않고 저장되어야 한다
        	// Cmd_D 를 사용하여 문장을 삭제하고 업데이트 됐는지 확인한다. 
			Command_Test.Cmd_D(FILE, 1);
			
        	Start_Finish.Do_Update(FILE);
        	assertEquals("micheal jackson - the way you make me feel", FILE.GetLine(1));
			assertEquals(Num_Line , FILE.NumLins());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_T() {
	
		Command_Test.Cmd_T(FILE);
		assertEquals(1, FILE.GetCLN());
	}
	
	@Test
	public void test_E() {
		
		int CLN = FILE.NumLins();
		Command_Test.Cmd_E(FILE);
		assertEquals(CLN, FILE.GetCLN());
	}
	
	@Test
	public void test_N() {
		
		int CLN = FILE.GetCLN();
		int next = 3;
		
		Command_Test.Cmd_N(FILE, next);
		
		if(CLN + next > FILE.NumLins())			
			CLN = FILE.NumLins();
		else
			CLN += next;
		
		assertEquals(CLN, FILE.GetCLN());
	}
	
	@Test
	public void test_B() {
		
		int CLN = FILE.GetCLN();
		int back = 3;
		
		Command_Test.Cmd_B(FILE, back);
		
		if(1 > CLN - back)			CLN = 1;
		else						CLN -= back;
		
		assertEquals(CLN, FILE.GetCLN());
	}
	
	@Test
	public void test_W() {
		
		assertEquals("At Edit File Line 1", JUnitUsingTest.Command_C(FILE));
	}	
	
	@Test
	public void test_C() {
		
		assertEquals("Total Edit File Lines: 17", JUnitUsingTest.Command_C(FILE));
	}	
	
	@Test
	public void test_L() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		// test 한 파일의 1~5 라인을 저장한 리스트와, Cmd_L 함수를 통해 출력되는 문장들을 저장한 리스트를 서로 비교하는 부분
		ArrayList<String> list = new ArrayList<String>();
		list.add("@playlist");
		list.add("micheal jackson - the way you make me feel");
		list.add("swings - special");
		
		ArrayList<String> test = JUnitUsingTest.Command_L(FILE, nLines);
		assertEquals(list, test);
		
		Command_Test.Cmd_L(FILE, nLines);
		
		// CLN 이 출력된 마지막 라인 수를 가지는지 테스트
		assertEquals(CLN + (nLines-1), FILE.GetCLN());
	}
	
	@Test
	public void test_S() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		// test 한 파일의 1~5 라인을 저장한 리스트와, Cmd_L 함수를 통해 출력되는 문장들을 저장한 리스트를 서로 비교하는 부분
		ArrayList<String> list = new ArrayList<String>();
		list.add("@playlist");
		list.add("micheal jackson - the way you make me feel");
		list.add("swings - special");
		
		ArrayList<String> test = JUnitUsingTest.Command_L(FILE, nLines);
		assertEquals(list, test);
		
		Command_Test.Cmd_S(FILE, nLines);
		
		// CLN 이 변화되지 않는지 테스트 : CLN 과 FILE.GetCLN() 이 같지 않아야 함
		assertSame(CLN, FILE.GetCLN());
	}
	
	@Test
	public void test_D() {
		
		int CLN = FILE.GetCLN(), nLines = 2;
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("@playlist");
		list.add("micheal jackson - the way you make me feel");
		list.add("swings - special");
	
		ArrayList<String> test = JUnitUsingTest.Command_D(FILE, nLines);  
		assertEquals(list, test); // 달라서 오류가 발생한다.
		
		Command_Test.Cmd_D(FILE, nLines);
		
		assertEquals(CLN, FILE.GetCLN());
	}
	
	@Test
	public void test_A() {
		
		int CLN = FILE.GetCLN(), Num_Line = FILE.NumLins();
		
		try {
			
			Command_Test.Cmd_A(FILE);
			
			assertEquals(CLN + 1, FILE.GetCLN()); // CLN 이 증가되는지 확인
			assertEquals(Num_Line, FILE.NumLins());; // Num_Line가 증가됐는지 확인
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_F() {
		
		int CLN = FILE.GetCLN(), nLines = 5;
		String findingObject = "loco";
		
		Command_Test.Cmd_F(FILE, nLines, findingObject);
		
		// Cmd_F가 실행된 뒤, CLN도 이동했는지 확인
		assertEquals(CLN + 4, FILE.GetCLN());
	}
	
	@Test
	public void test_R() {
		
		int CLN = FILE.GetCLN(), nLines = 5;
		String strF = "loco", strR = "AOMG_LOCO";
		
		Command_Test.Cmd_R(FILE, nLines, strF, strR);
		
		// Cmd_R이 실행된 뒤, CLN도 이동했는지 확인
		assertEquals(CLN + 4, FILE.GetCLN());
	}
	
	
	@Test
	public void test_Z() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		Command_Test.Cmd_Z(FILE, nLines);
		// Cmd_Z이 실행된 뒤, CLN도 이동했는지 확인
		assertEquals(CLN + 2 , FILE.GetCLN());
	}
	

	@Test
	public void test_I() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		// 파일의 1행 1열에 '@' 가 없는 경우 CLN 은 영향을 받지 않음
		Command_Test.Cmd_I(FILE);
		assertEquals(CLN , FILE.GetCLN());
	}
	

	
	@Test
	public void test_O() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		Command_Test.Cmd_O(FILE, nLines);
		
		// Cmd_O가 실행된 뒤, CLN도 이동했는지 확인
		assertEquals(CLN + 2, FILE.GetCLN());
	}
	
	

}