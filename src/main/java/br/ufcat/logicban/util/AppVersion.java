package br.ufcat.logicban.util;

import java.io.InputStream;
import java.util.Properties;

public class AppVersion {
    public static final String VERSION;

    static {
        String version = "0.0.0"; // Versão padrão
        try (InputStream input = AppVersion.class.getClassLoader().getResourceAsStream("version.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                version = prop.getProperty("version", "0.0.0");
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler a versão: " + e.getMessage());
        }
        VERSION = version;
    }
}