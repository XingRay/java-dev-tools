package com.xingray.maven.dependency.downloader.test;

import com.xingray.maven.dependency.downloader.Dependency;
import com.xingray.maven.dependency.downloader.ErrorParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErrorParserTest {

    private ErrorParser errorParser;

    @BeforeEach
    public void beforeEach(){
        errorParser = new ErrorParser();
    }
    @Test
    public void test() {
        String error = """
                com.gluonhq:glisten-afterburner:jar:2.1.0 was not found in https://maven.aliyun.com/repository/public during a previous attempt. This failure was cached in the local repository and resolution is not reattempted until the update interval of aliyunmaven has elapsed or updates are forced
                                
                Try to run Maven import with -U flag (force update snapshots)
                                
                """;
        Dependency dependency = errorParser.parse(error);
        assert dependency.getGroupId().equals("com.gluonhq");
        assert dependency.getArtifactId().equals("glisten-afterburner");
        assert dependency.getPackaging().equals("jar");
        assert dependency.getVersion().equals("2.1.0");
    }

}


