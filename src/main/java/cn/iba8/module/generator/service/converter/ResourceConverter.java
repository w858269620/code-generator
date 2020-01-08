package cn.iba8.module.generator.service.converter;

import lombok.Data;

import java.io.Serializable;

public abstract class ResourceConverter {

    @Data
    public static class ResourceTheSame implements Serializable {

    }

}
