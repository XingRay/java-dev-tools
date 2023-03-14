package com.xingray.maven.dependency.downloader.test;

import com.xingray.maven.dependency.downloader.ErrorParser;
import com.xingray.maven.dependency.downloader.WgetMavenDownloader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class DownloaderTest {

    private WgetMavenDownloader downloader;
    private ErrorParser errorParser;

    @BeforeEach
    public void beforeEach(){
        downloader = new WgetMavenDownloader("https://nexus.gluonhq.com/nexus/content/repositories/releases", new File("D:\\tmp\\maven"));
        errorParser = new ErrorParser();
    }
    @Test
    public void test() {
        String error = """
                com.gluonhq:charm-cloudlink-client:jar:6.0.7 was not found in https://maven.aliyun.com/repository/public during a previous attempt. This failure was cached in the local repository and resolution is not reattempted until the update interval of aliyunmaven has elapsed or updates are forced
                                
                Try to run Maven import with -U flag (force update snapshots)
                                
                """;
        downloader.download(errorParser.parse(error));
    }

}
