package de.welikecoding.misc.customskulls.utils;

import de.welikecoding.misc.customskulls.CustomSkulls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    private final CustomSkulls main = CustomSkulls.getInstance();

    public void checkForUpdate() {

        String latest = "0.0";

        URL versionSource;
        try {

            versionSource = new URL("https://raw.githubusercontent.com/welikecoding/WeLikeCustomSkulls/master/pom.xml");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(versionSource.openStream()));

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if(inputLine.contains("<version>")) {
                    latest = inputLine.replace("<version>", "").replace("</version>", "").trim();
                    break;
                }
            }
            reader.close();

            if(Double.parseDouble(latest) > Double.parseDouble(main.getDescription().getVersion())) {
                CC.log(LogType.WARNING, "You don't have the most up to date version installed.");
                CC.log(LogType.WARNING, "Visit GitHub to get the newest release.");
                CC.log(LogType.WARNING, "https://github.com/welikecoding/WeLikeCustomSkulls");
            } else {
                CC.log(LogType.SUCCESS, "You have the most up to date version installed.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
