package mod.leer.capabilities;

import java.util.concurrent.Callable;

public class VoidFactory implements Callable<ILeer> {
	
	@Override
	public ILeer call() throws Exception {
		return new Void();
	}

}
