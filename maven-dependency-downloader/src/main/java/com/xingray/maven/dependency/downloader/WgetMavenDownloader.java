package com.xingray.maven.dependency.downloader;

import com.xingray.java.command.JavaRuntimeCommandExecutor;
import com.xingray.java.command.SimpleExecuteListener;
import com.xingray.java.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WgetMavenDownloader {

    private static final Logger logger = LoggerFactory.getLogger(WgetMavenDownloader.class);
    private final JavaRuntimeCommandExecutor executor;
    private final String remoteRepositoryUrl;

    private final File outputDir;

    private final String wgetCmd;

    public WgetMavenDownloader(String remoteRepositoryUrl, File outputDir) {
        this(null, remoteRepositoryUrl, outputDir);
    }

    public WgetMavenDownloader(String wgetCmd, String remoteRepositoryUrl, File outputDir) {
        this.remoteRepositoryUrl = remoteRepositoryUrl;
        this.executor = new JavaRuntimeCommandExecutor();
        this.outputDir = outputDir;
        this.wgetCmd = StringUtil.isEmpty(wgetCmd) ? "wget" : wgetCmd;
    }

    public void download(Dependency dependency) {
        if (dependency == null) {
            logger.error("download dependency is null");
            return;
        }

        String groupId = dependency.getGroupId();
        String artifactId = dependency.getArtifactId();
        String version = dependency.getVersion();

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String path = groupId.replaceAll("\\.", "/") + "/" + artifactId + "/" + version + "/";
        String cmd = "cmd /c "+ wgetCmd + " -r -np -R *.htm* " + remoteRepositoryUrl + "/" + path;
        logger.info(cmd);
        executor.execute(cmd, outputDir, new SimpleExecuteListener() {
            @Override
            public void out(String line) {
                logger.info(line);
            }
        });
    }
}
