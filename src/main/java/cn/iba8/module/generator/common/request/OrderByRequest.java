package cn.iba8.module.generator.common.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderByRequest implements Serializable {

    private String orderBy;

}
