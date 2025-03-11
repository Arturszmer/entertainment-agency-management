package com.agency.documentcontext.doccontext;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateContextType {

    CONTRACT_WORK("contractWork"),
    CONTRACTOR("contractor"),
    PROJECT("project"),
    ORGANIZER("organizer"),
    AGENCY("agency"),
    BILL("bill"),;

    private final String prefix;
}
