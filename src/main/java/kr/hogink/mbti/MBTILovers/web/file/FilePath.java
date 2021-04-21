package kr.hogink.mbti.MBTILovers.web.file;

import java.io.File;
import java.util.regex.Matcher;

public class FilePath {
    private static final String projectPath = System.getProperty("user.dir");
    private static final String additionalPath = "/src/main/resources/static/images";
    static final String imagesPath = projectPath + additionalPath.replaceAll("/", Matcher.quoteReplacement(File.separator));
}
