package com.agency.documentcontext.doccontext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerationResult {

    private String filename;
    private String content;
    private boolean success = true;
    private List<String> errorLogs = new ArrayList<>();
}
