package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import com.sun.tools.javac.Main;
import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class GeoLocator {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            logger.debug("Variable ipAddOrHost is not null");
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            logger.debug("Method UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost) finished successfully");
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
        } else {
            logger.warn("Variable ipAddrOrHost is null");
            url = new URL(GEOLOCATOR_SERVICE_URI);
        }
        logger.info("Message was successfully received from the following URL: {}",url);
        String s = IOUtils.toString(url, "UTF-8");
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {

        try {
            String arg = args.length > 0 ? args[0] : null;
            logger.info("\nThe JSON file received is: {}",new GeoLocator().getGeoLocation(arg));

        } catch (IOException e) {
            logger.error("\nThe following error occoured: {}", e.getMessage());
        }
    }

}
