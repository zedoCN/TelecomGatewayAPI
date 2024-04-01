package top.zedo.telecomcgi.info;

public class PMRule {
   public String client;
   public String desp;
   public int enable;
   public int exPort;
   public int inPort;
   public String protocol;

   @Override
   public String toString() {
      return "PMRule{" +
              "client='" + client + '\'' +
              ", desp='" + desp + '\'' +
              ", enable=" + enable +
              ", exPort=" + exPort +
              ", inPort=" + inPort +
              ", protocol='" + protocol + '\'' +
              '}';
   }
}
