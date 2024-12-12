package com.agency.service;

import org.springframework.core.io.Resource;

public interface DocumentService {

    Resource downloadDocument(String filename);
    void removeDocument(String referenceId);

}
