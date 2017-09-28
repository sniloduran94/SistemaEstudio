package conexion;

import java.applet.Applet;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class UserHomeApplet
  extends Applet
{
  private static final long serialVersionUID = 1L;

  public String getUserHome()
  {
     return AccessController.doPrivileged(new PrivilegedAction()
      {
        public Object run()
        {
          return System.getProperty("user.home");
        }
      }).toString();
  }


}