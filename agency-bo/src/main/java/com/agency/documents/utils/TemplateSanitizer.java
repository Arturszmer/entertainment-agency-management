package com.agency.documents.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

public class TemplateSanitizer {

    public static String sanitize(String htmlContent) {
        if (htmlContent == null) {
            return null;
        }
        Document.OutputSettings settings = new Document.OutputSettings();
        settings.prettyPrint(false);
        settings.escapeMode(org.jsoup.nodes.Entities.EscapeMode.xhtml);
        settings.charset(java.nio.charset.StandardCharsets.UTF_8);
        String cleaned = Jsoup.clean(htmlContent, "", Safelist.basic(), settings);
        
        // Convert multiple spaces to non-breaking spaces so they are preserved in HTML preview.
        // Standard browsers collapse multiple regular spaces.
        while (cleaned.contains("  ")) {
            cleaned = cleaned.replace("  ", "\u00A0 ");
        }

        // Replace non-breaking space entities back to literal characters.
        // We also handle case-insensitive entities and both hex/decimal just in case.
        return cleaned.replace("&#xa0;", "\u00A0")
                .replace("&#XA0;", "\u00A0")
                .replace("&nbsp;", "\u00A0")
                .replace("&NBSP;", "\u00A0")
                .replace("&#160;", "\u00A0");
    }
}
