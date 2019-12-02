package cn.iba8.module.generator.common.properties2json.utils.iteration;

/**
 * Consumer which can throw some Exception.
 */
@FunctionalInterface
public interface ExceptionableConsumer {
    void consume(int iterationIndex) throws Exception;
}
