/**
 * 
 */
package vista;

import java.awt.Component;
import java.awt.Container;

/**
 * @author adalberto
 *
 */
public class Utils {

	/**
	 * 
	 */
	public Utils() {
		// TODO Auto-generated constructor stub
	}
	
	public static void removeComponentRecursiveBySuffix(Container padre, String sufijo) {
		Component[] components = padre.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (sufijo == null || components[i].getName().endsWith(sufijo)) {
				if (components[i] instanceof Container) {
					removeComponentRecursiveBySuffix((Container) components[i],
							null);
				}
				padre.remove(components[i]);
			}
		}
	}

}
