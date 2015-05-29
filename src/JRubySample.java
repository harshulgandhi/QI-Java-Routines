import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JRubySample {
	public static void main(String[] args) throws ScriptException, NoSuchMethodException {
		
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		List<ScriptEngineFactory> factories = scriptEngineManager.getEngineFactories();
		for (ScriptEngineFactory factory : factories) {
		   System.out.println("Factory name: " + factory.getEngineName());
		}
		
		JRubySample jObj = new JRubySample();
		jObj.runRuby();
	}
	
	public void runRuby() throws ScriptException, NoSuchMethodException{
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("rb");
		InputStream is = ClassLoader.getSystemResourceAsStream("helloworld.rb");
		Reader reader = new InputStreamReader(is);
		scriptEngine.eval(reader);
		Invocable invocableEngine = (Invocable)scriptEngine;
		if (invocableEngine != null) {
		   System.out.println(invocableEngine.invokeFunction("sayHello", "Ruby Guru"));
		}
	}
}
