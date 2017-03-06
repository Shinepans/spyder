package javatopython.cwb.dhu.edu.cn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
@SuppressWarnings("resource")
public final class JythonUtil {
     
    private JythonUtil(){}
     
    /**
     * 执行某个.py文件
     * @param filePath
     * @throws IOException
     */
    public static void pythonExecute(String filePath) throws IOException{
        
		PythonInterpreter pin = new PythonInterpreter();
        InputStream is = new FileInputStream(filePath);
        pin.execfile(is);
        is.close();
    }
     
    /**
     * 获取python程序的变量值
     * @param filePath
     * @param ponames
     * @return
     * @throws IOException
     */
    public static List<PyObject> transP2JData(String filePath, String...ponames) throws IOException{
        PythonInterpreter pin = new PythonInterpreter();
        InputStream is = new FileInputStream(filePath);
        pin.execfile(is);
        is.close();
        List<PyObject> pos = new ArrayList<>();
        for (String poname : ponames) {
            PyObject po = pin.get(poname);
            pos.add(po);
        }
        return pos;
    }
     
    /**
     * 将参数赋给python程序执行
     * @param filePath
     * @param pomaps
     * @throws IOException
     */
    public static void transJ2PData(String filePath, Map<String, Object> pomaps) throws IOException {
        PythonInterpreter pin = new PythonInterpreter();
        InputStream is = new FileInputStream(filePath);
        for (String pomapkey : pomaps.keySet()) {
            pin.set(pomapkey, pomaps.get(pomapkey));
        }
        pin.execfile(is);
        is.close();
    }
     
    /**
     * 将参数赋给python程序执行,并获取python中的变量值
     * @param filePath
     * @param pomaps
     * @param ponames
     * @return
     * @throws IOException
     */
    public static List<PyObject> transJ2PData(String filePath, Map<String, Object> pomaps, String...ponames) throws IOException {
        PythonInterpreter pin = new PythonInterpreter();
        InputStream is = new FileInputStream(filePath);
        for (String pomapkey : pomaps.keySet()) {
            pin.set(pomapkey, pomaps.get(pomapkey));
        }
        pin.execfile(is);
        is.close();
        List<PyObject> pos = new ArrayList<>();
        for (String poname : ponames) {
            PyObject po = pin.get(poname);
            pos.add(po);
        }
        return pos;
    }
 
}
