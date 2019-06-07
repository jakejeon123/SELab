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
    // �� �׽�Ʈ�� ����Ǳ��� �Ź� ����� �Լ�
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
        	// Q �� ������Ʈ �� ������ ���ִ� ���̴�.
        	// Cmd_D �� ����Ͽ� ������ ������ �� ������ �Ǿ����� Ȯ���Ѵ�. 
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
        	// X �� ������Ʈ ���� �ʰ� ����Ǿ�� �Ѵ�
        	// Cmd_D �� ����Ͽ� ������ �����ϰ� ������Ʈ �ƴ��� Ȯ���Ѵ�. 
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
		
		// test �� ������ 1~5 ������ ������ ����Ʈ��, Cmd_L �Լ��� ���� ��µǴ� ������� ������ ����Ʈ�� ���� ���ϴ� �κ�
		ArrayList<String> list = new ArrayList<String>();
		list.add("@playlist");
		list.add("micheal jackson - the way you make me feel");
		list.add("swings - special");
		
		ArrayList<String> test = JUnitUsingTest.Command_L(FILE, nLines);
		assertEquals(list, test);
		
		Command_Test.Cmd_L(FILE, nLines);
		
		// CLN �� ��µ� ������ ���� ���� �������� �׽�Ʈ
		assertEquals(CLN + (nLines-1), FILE.GetCLN());
	}
	
	@Test
	public void test_S() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		// test �� ������ 1~5 ������ ������ ����Ʈ��, Cmd_L �Լ��� ���� ��µǴ� ������� ������ ����Ʈ�� ���� ���ϴ� �κ�
		ArrayList<String> list = new ArrayList<String>();
		list.add("@playlist");
		list.add("micheal jackson - the way you make me feel");
		list.add("swings - special");
		
		ArrayList<String> test = JUnitUsingTest.Command_L(FILE, nLines);
		assertEquals(list, test);
		
		Command_Test.Cmd_S(FILE, nLines);
		
		// CLN �� ��ȭ���� �ʴ��� �׽�Ʈ : CLN �� FILE.GetCLN() �� ���� �ʾƾ� ��
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
		assertEquals(list, test); // �޶� ������ �߻��Ѵ�.
		
		Command_Test.Cmd_D(FILE, nLines);
		
		assertEquals(CLN, FILE.GetCLN());
	}
	
	@Test
	public void test_A() {
		
		int CLN = FILE.GetCLN(), Num_Line = FILE.NumLins();
		
		try {
			
			Command_Test.Cmd_A(FILE);
			
			assertEquals(CLN + 1, FILE.GetCLN()); // CLN �� �����Ǵ��� Ȯ��
			assertEquals(Num_Line, FILE.NumLins());; // Num_Line�� �����ƴ��� Ȯ��
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_F() {
		
		int CLN = FILE.GetCLN(), nLines = 5;
		String findingObject = "loco";
		
		Command_Test.Cmd_F(FILE, nLines, findingObject);
		
		// Cmd_F�� ����� ��, CLN�� �̵��ߴ��� Ȯ��
		assertEquals(CLN + 4, FILE.GetCLN());
	}
	
	@Test
	public void test_R() {
		
		int CLN = FILE.GetCLN(), nLines = 5;
		String strF = "loco", strR = "AOMG_LOCO";
		
		Command_Test.Cmd_R(FILE, nLines, strF, strR);
		
		// Cmd_R�� ����� ��, CLN�� �̵��ߴ��� Ȯ��
		assertEquals(CLN + 4, FILE.GetCLN());
	}
	
	
	@Test
	public void test_Z() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		Command_Test.Cmd_Z(FILE, nLines);
		// Cmd_Z�� ����� ��, CLN�� �̵��ߴ��� Ȯ��
		assertEquals(CLN + 2 , FILE.GetCLN());
	}
	

	@Test
	public void test_I() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		// ������ 1�� 1���� '@' �� ���� ��� CLN �� ������ ���� ����
		Command_Test.Cmd_I(FILE);
		assertEquals(CLN , FILE.GetCLN());
	}
	

	
	@Test
	public void test_O() {
		
		int CLN = FILE.GetCLN(), nLines = 3;
		
		Command_Test.Cmd_O(FILE, nLines);
		
		// Cmd_O�� ����� ��, CLN�� �̵��ߴ��� Ȯ��
		assertEquals(CLN + 2, FILE.GetCLN());
	}
	
	

}