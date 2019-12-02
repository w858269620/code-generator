package cn.iba8.module.generator.common.properties2json.utils.iteration;

/**
 * This runnable can rise some Throwable instance.
 */
@FunctionalInterface
public interface ExceptionableRunnable {
    void invoke() throws Exception;
}
