package cn.howxu;


import java.util.logging.Level;
import java.util.logging.Logger;

public class ELog {
    //private Logger logger;
    public static void log_info(String loggerName,String text){
        Logger.getLogger(loggerName).log(Level.INFO,text);
    }

}
