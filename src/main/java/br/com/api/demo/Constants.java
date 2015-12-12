package br.com.api.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by root on 10/12/15.
 */
public class Constants {

    public static final String PACKAGE_BASE = "br.com.api.demo";

    public static Logger logger (){
    	return LoggerFactory.getLogger("api");
    }
}
