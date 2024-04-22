package com.agency.project.service;

import com.agency.project.repository.ProjectRepository;
import com.agency.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

class ProjectServiceImplTest {

    private ProjectService service;
    private ProjectRepository repository = mock(ProjectRepository.class);

    @BeforeEach
    void setup(){
        service = new ProjectServiceImpl(repository);
    }


}
