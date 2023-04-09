package com.power.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import org.beetl.core.annotation.ThreadSafety;
import org.bouncycastle.util.Strings;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.power.common.util.DateTimeUtil;
import com.power.common.util.FileUtil;
import com.power.common.util.StringUtil;
import com.power.doc.builder.*;
import com.power.doc.builder.openapi.OpenApiBuilder;
import com.power.doc.builder.rpc.RpcAdocBuilder;
import com.power.doc.builder.rpc.RpcHtmlBuilder;
import com.power.doc.builder.rpc.RpcMarkdownBuilder;
import com.power.doc.builder.rpc.RpcTornaBuilder;
import com.power.doc.model.*;

public class Main {
    private final static Set<String> PREFIX_LIST = new HashSet<>();

    /**
     * Gson Object
     */
    public final static Gson GSON = new GsonBuilder().addDeserializationExclusionStrategy(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }).create();

    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("Usage: java -jar [ApiDocType] [SettingJsonFile]");
        } else {
            System.out.println("Starting...");
            String docType = args[0];
            String jsonFile = args[1];
            if(test_1(docType, jsonFile)){
                System.out.println("finished!...");
            } else {
                System.out.println("ApiDocType error OR SettingJsonFile read error !!!");
            }
            // String artifactId = "ksharding-jdbc";
            // System.out.println(artifactId);
        }
    }

    public static boolean test_1(String docType, String jsonFile) {
        // ApiConfig config = new ApiConfig();        
        long start = System.currentTimeMillis();
        String data;
        try {
            data = FileUtil.getFileContent(new FileInputStream(jsonFile));
            // data = FileUtil.getFileContent(new
            // FileInputStream("src/main/resources/default.json.bak"));
            ApiConfig config = GSON.fromJson(data, ApiConfig.class);

            switch (docType) {
                case "markdown":
                    ApiDocBuilder.buildApiDoc(config);
                    break;

                case "html":
                    HtmlApiDocBuilder.buildApiDoc(config);
                    break;

                case "postman":
                    PostmanJsonBuilder.buildPostmanCollection(config);
                    break;

                case "adoc":
                    AdocDocBuilder.buildApiDoc(config);
                    break;

                case "openapi":
                    OpenApiBuilder.buildOpenApi(config);
                    break;

                case "torna-rest":
                    TornaBuilder.buildApiDoc(config);
                    break;

                case "rpc-html":
                    RpcHtmlBuilder.buildApiDoc(config);
                    break;

                case "rpc-markdown":
                    RpcMarkdownBuilder.buildApiDoc(config);
                    break;

                case "rpc-adoc":
                    RpcAdocBuilder.buildApiDoc(config);
                    break;

                case "torna-rpc":
                    RpcTornaBuilder.buildApiDoc(config);
                    break;

                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
        return true;
    }
}
