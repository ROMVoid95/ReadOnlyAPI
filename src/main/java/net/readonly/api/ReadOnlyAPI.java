package net.readonly.api;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.path;
import static spark.Spark.port;

import java.io.IOException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.readonly.api.utils.Config;
import net.readonly.api.utils.Utils;
import spark.Spark;

public class ReadOnlyAPI {
    private final Logger logger = LoggerFactory.getLogger(ReadOnlyAPI.class);

    private Config config;
    private int servedRequests;

    public static void main(String[] args) {
        try {
            new ReadOnlyAPI();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private ReadOnlyAPI() throws Exception {
        logger.info("\n" +
                "||   ██████╗ ███████╗ █████╗ ██████╗  ██████╗ ███╗   ██╗██╗  ██╗   ██╗     █████╗ ██████╗ ██╗   ||\n" +
                "||   ██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔═══██╗████╗  ██║██║  ╚██╗ ██╔╝    ██╔══██╗██╔══██╗██║   ||\n" +
                "||   ██████╔╝█████╗  ███████║██║  ██║██║   ██║██╔██╗ ██║██║   ╚████╔╝     ███████║██████╔╝██║   ||\n" +
                "||   ██╔══██╗██╔══╝  ██╔══██║██║  ██║██║   ██║██║╚██╗██║██║    ╚██╔╝      ██╔══██║██╔═══╝ ██║   ||\n" +
                "||   ██║  ██║███████╗██║  ██║██████╔╝╚██████╔╝██║ ╚████║███████╗██║       ██║  ██║██║     ██║   ||\n" +
                "||   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚═╝       ╚═╝  ╚═╝╚═╝     ╚═╝   ||\n" +
                "\n" +
                ":: ReadOnly API {} ({}):: By ROM ::\n", APIInfo.VERSION, APIInfo.GIT_REVISION);
        try {
            config = Utils.loadConfig();
        } catch (IOException e) {
            logger.error("An error occurred while loading the configuration file!", e);
            System.exit(100);
        }

        //Spark initialization.
        port(config.getPort());
        Spark.init();

        get("/mantaroapi/ping", (req, res) ->
                new JSONObject().put("status", "ok")
                        .put("version", APIInfo.VERSION)
                        .put("rev", APIInfo.GIT_REVISION)
                        .put("requests_served", servedRequests)
                        .toString()
        );

        path("/mantaroapi/bot", () -> {
            //Spark why does this work like this but not without an argument, I'M LITERALLY GIVING YOU AN EMPTY STRING
            before("", (request, response) -> {
                handleAuthentication(request.headers("Authorization"));
                servedRequests++;
            });
            before("/*", (request, response) -> {
                handleAuthentication(request.headers("Authorization"));
                servedRequests++;
            });
        });
    }

    //bootleg af honestly
    private void handleAuthentication(String auth) {
        if(!config.getAuth().equals(auth))
            halt(403);
    }
}
