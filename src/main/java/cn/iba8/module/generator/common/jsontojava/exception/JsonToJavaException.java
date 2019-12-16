package cn.iba8.module.generator.common.jsontojava.exception;

public class JsonToJavaException extends RuntimeException{

    public JsonToJavaException(String msg){
        super(msg);
    }
    public JsonToJavaException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
