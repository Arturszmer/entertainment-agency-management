package com.agency.generator;

import com.agency.documentcontext.doccontext.GenerationResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DocParagraphsReplacer {

    private final GenerationResult generationResult;

    public DocParagraphsReplacer(GenerationResult generationResult) {
        this.generationResult = generationResult;
    }

    public String replaceText(String part, Map<String, Object> placeholderContext) {
        String regexPattern = "\\{\\{.+?}}";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(part);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String placeholder = matcher.group();
            String replacement = replace(placeholderContext, placeholder);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private String replace(Map<String, Object> placeholderContext, String word) {
        try {
            String getKeyRegexPattern = "\\{\\{(.+?)}}";
            String key = word.replaceAll(getKeyRegexPattern, "$1");
            Object object = placeholderContext.get(key);
            if(object != null) {
                return object.toString();
            } else {
                log.warn("Placeholder for: {} does not exist in context", word);
                generationResult.getErrorLogs().add("Placeholder for: %s does not exist in context".formatted(word));
                generationResult.setSuccess(Boolean.FALSE);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            log.error(e.getMessage());
        }
        return word;
    }
}
