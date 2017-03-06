package javatopython.cwb.dhu.edu.cn;

/*import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;*/
import org.python.util.PythonInterpreter;

public class Test3 {

	public static void main(String args[]) {

		// PythonInterpreter interpreter = new PythonInterpreter();
		// interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun');
		// ");
		// interpreter.exec("print days[1];");

//		PythonInterpreter interp = new PythonInterpreter();
//		System.out.println("Hello, brave new world");
//		interp.exec("import sys");
//		interp.exec("print sys");
//		interp.set("a", new PyInteger(42));
//		interp.exec("print a");
//		interp.exec("x = 2+2");
//		PyObject x = interp.get("x");
//		System.out.println("x: " + x);
//		System.out.println("Goodbye, cruel world");

		
		//调用具体的方法，并传参数
//		PythonInterpreter interpreter = new PythonInterpreter();
//		interpreter.execfile("E:\\ckcode\\my_utils.py");
//		PyFunction func = (PyFunction) interpreter.get("adder", PyFunction.class);
//		int a = 1010, b = 2;
//		PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
//		System.out.println("anwser = " + pyobj.toString());
		
		@SuppressWarnings("resource")
		  PythonInterpreter interpreter = new PythonInterpreter();   
		  interpreter.execfile("E:\\ckcode\\Python Codes\\CK_all.py");   

	}// main

}
