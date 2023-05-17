package org.zj.takeout.common;

/**
 * 工具类
 * 通过线程的共享副本存储用户ID，来让MyMetaObjecthander获取用户id
 * 因为一个http请求的多个处理过程都是由一个线程处理
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setThreadLocal(Long id){
        threadLocal.set(id);
    }
    public static Long getThreadLocal(){
        return threadLocal.get();
    }
}
