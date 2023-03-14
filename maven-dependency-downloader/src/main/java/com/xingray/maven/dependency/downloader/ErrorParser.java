package com.xingray.maven.dependency.downloader;

import com.xingray.java.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorParser {
    private static final Logger logger = LoggerFactory.getLogger(ErrorParser.class);

    /**
     * com.gluonhq:glisten-afterburner:jar:2.1.0 was not found in https://maven.aliyun.com/repository/public during a previous attempt. This failure was cached in the local repository and resolution is not reattempted until the update interval of aliyunmaven has elapsed or updates are forced
     * <p>
     * Try to run Maven import with -U flag (force update snapshots)
     *
     * @param error
     * @return
     */
    public Dependency parse(String error) {
        logger.info("parse error:{}", error);
        if (StringUtil.isEmpty(error)) {
            return null;
        }

        int index = error.indexOf("was not found in");
        if (index < 0) {
            return null;
        }

        String substring = error.substring(0, index).trim();
        logger.info("gav:{}", substring);

        String[] split = substring.split(":");
        Dependency dependency = new Dependency();
        dependency.setGroupId(split[0].trim());
        dependency.setArtifactId(split[1].trim());
        dependency.setPackaging(split[2].trim());
        dependency.setVersion(split[3].trim());

        logger.info("dependency:{}", dependency);
        return dependency;

    }
}
