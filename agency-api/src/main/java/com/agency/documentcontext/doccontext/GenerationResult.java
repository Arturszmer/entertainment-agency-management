package com.agency.documentcontext.doccontext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerationResult {

    private String filename;
    private boolean success = true;
    private List<String> errorLogs = new ArrayList<>();
}
