package com.agency.documents.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class TemplateSanitizer {
    public static String sanitize(String htmlContent) {
        return Jsoup.clean(htmlContent, Safelist.basic());
    }
}
