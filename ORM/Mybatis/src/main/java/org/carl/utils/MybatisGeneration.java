package org.carl.utils;

import org.mybatis.generator.api.ShellRunner;

import java.util.Objects;

public class MybatisGeneration {
    public static void main(String[] args) {

        String config = Objects.requireNonNull(MybatisGeneration.class.getClassLoader()
                .getResource("generatorConfig.xml")).getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);
    }
}
